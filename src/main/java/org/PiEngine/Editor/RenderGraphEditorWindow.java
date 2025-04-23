package org.PiEngine.Editor;

import imgui.ImGui;
import imgui.ImVec2;
import imgui.ImVec4;
import imgui.flag.ImGuiKey;
import imgui.type.ImInt;
import imgui.extension.imnodes.ImNodes;

import org.PiEngine.Render.Renderer;
import org.PiEngine.Render.RenderPass;

import java.util.*;

public class RenderGraphEditorWindow extends EditorWindow
{
    private final Renderer renderer;

    // Stable ID maps
    private final Map<String, Integer> nodeIds      = new HashMap<>();
    private final Map<String, List<Integer>> inputPinIds = new HashMap<>();
    private final Map<String, Integer> outputPinIds = new HashMap<>();

    // Holds link → (from, to) so we can delete later
    private final Map<Integer, LinkInfo> linkMap    = new HashMap<>();

    private int nextId = 1000;

    private static class LinkInfo
    {
        final String from, to;
        final int inputIndex;

        LinkInfo(String from, String to, int inputIndex)
        {
            this.from = from;
            this.to = to;
            this.inputIndex = inputIndex;
        }
    }

    public RenderGraphEditorWindow(Renderer renderer)
    {
        super("Render Graph Editor");
        this.renderer = renderer;

        // Register any passes that already exist
        renderer.getPasses().keySet().forEach(this::registerPass);
        onCreate();
    }

    @Override
    public void onCreate()
    {
        // Create the ImNodes context once when this window is created
        ImNodes.createContext();
    }

    @Override
    public void onDestroy()
    {
        // Destroy it when the window is torn down
        ImNodes.destroyContext();
    }

    private void registerPass(String name)
    {
        RenderPass pass = renderer.getPasses().get(name);
        int inputCount = pass.getInputCount();

        nodeIds.put(name, nextId++);

        List<Integer> pinIds = new ArrayList<>();
        for (int i = 0; i < inputCount; i++)
        {
            pinIds.add(nextId++);
        }
        inputPinIds.put(name, pinIds);

        outputPinIds.put(name, nextId++);
    }

    private String findPassByInputId(int id)
    {
        return inputPinIds.entrySet()
                          .stream()
                          .filter(e -> e.getValue().contains(id))
                          .map(Map.Entry::getKey)
                          .findFirst()
                          .orElse(null);
    }

    private String findPassByOutputId(int id)
    {
        return outputPinIds.entrySet()
                           .stream()
                           .filter(e -> e.getValue() == id)
                           .map(Map.Entry::getKey)
                           .findFirst()
                           .orElse(null);
    }

    private int getInputIndex(String passName, int pinId)
    {
        List<Integer> inputs = inputPinIds.get(passName);
        if (inputs != null)
        {
            for (int i = 0; i < inputs.size(); i++)
            {
                if (inputs.get(i) == pinId)
                    return i;
            }
        }
        return -1;
    }

    @Override
    public void onRender()
    {
        ImGui.begin(getName());
        ImNodes.beginNodeEditor();

        for (String passName : renderer.getPasses().keySet())
        {
            int nodeId = nodeIds.get(passName);
            List<Integer> inputs = inputPinIds.get(passName);
            int outputId = outputPinIds.get(passName);

            long previewTex = renderer.getPasses().get(passName).getOutputTexture();

            ImNodes.beginNode(nodeId);

            // Title bar
            ImNodes.beginNodeTitleBar();
                ImGui.text(passName);
            ImNodes.endNodeTitleBar();

            // Input pins on the left
            for (int i = 0; i < inputs.size(); i++)
            {
                ImNodes.beginInputAttribute(inputs.get(i));
                    ImGui.text("Input " + i);
                ImNodes.endInputAttribute();
            }

            
            float fullWidth = 190;
            if(!(inputs.size()<= 0))
            {
                ImGui.sameLine();
                fullWidth = 130;
            }
           
            float labelWidth = ImGui.calcTextSize("Output").x;
            ImGui.setCursorPosX(ImGui.getCursorPosX() + fullWidth - labelWidth);

            ImNodes.beginOutputAttribute(outputId);
                ImGui.text("Output");
            ImNodes.endOutputAttribute();

            ImGui.image(
                previewTex,
                new ImVec2(192, 108),
                new ImVec2(0, 1),
                new ImVec2(1, 0),
                new ImVec4(1, 1, 1, 1),
                new ImVec4(0, 0, 0, 0)
            );

            ImNodes.endNode();
        }

        // Handle link creation and rendering
        linkMap.clear();
        renderer.getConnections().forEach((toPass, inputMap) -> {
            inputMap.forEach((inputIndex, fromPass) -> {
                int fromOutput = outputPinIds.get(fromPass);
                int toInput = inputPinIds.get(toPass).get(inputIndex);
                int linkId = Objects.hash(fromOutput, toInput);

                ImNodes.link(linkId, fromOutput, toInput);
                linkMap.put(linkId, new LinkInfo(fromPass, toPass, inputIndex));
            });
        });

        ImNodes.endNodeEditor();
        ImGui.end();
        // Check for link creation
        ImInt startAttr = new ImInt();
        ImInt endAttr   = new ImInt();
        if (ImNodes.isLinkCreated(startAttr, endAttr))
        {
            String from = findPassByOutputId(startAttr.get());
            String to = null;
            int index = -1;

            // Find corresponding input pin
            for (Map.Entry<String, List<Integer>> entry : inputPinIds.entrySet())
            {
                index = getInputIndex(entry.getKey(), endAttr.get());
                if (index != -1)
                {
                    to = entry.getKey();
                    break;
                }
            }

            if (from != null && to != null)
            {
                renderer.connect(from, to, index);
            }
        }

        // Handle link hover and deletion
        ImInt selectedLinkId = new ImInt();
        if (ImNodes.isLinkHovered(selectedLinkId))
        {
            if (ImGui.isKeyPressed(ImGuiKey.Delete))
            {
                LinkInfo info = linkMap.remove(selectedLinkId.get());
                if (info != null)
                {
                    renderer.disconnect(info.to, info.inputIndex);
                }
            }
        }


    }
}
