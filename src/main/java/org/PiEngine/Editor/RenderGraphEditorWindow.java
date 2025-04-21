package org.PiEngine.Editor;

import imgui.ImGui;
import imgui.ImVec2;
import imgui.ImVec4;
import imgui.flag.ImGuiKey;
import imgui.type.ImInt;
import imgui.extension.imnodes.ImNodes;

import org.PiEngine.Render.Renderer;

import java.util.*;

public class RenderGraphEditorWindow extends EditorWindow
{
    private final Renderer renderer;

    // Stable ID maps
    private final Map<String, Integer> nodeIds      = new HashMap<>();
    private final Map<String, Integer> inputPinIds  = new HashMap<>();
    private final Map<String, Integer> outputPinIds = new HashMap<>();

    // Holds link → (from, to) so we can delete later
    private final Map<Integer, LinkInfo> linkMap    = new HashMap<>();

    private int nextId = 1000;

    private static class LinkInfo
    {
        final String from, to;
        LinkInfo(String from, String to) { this.from = from; this.to = to; }
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
        // Allocate 3 consecutive IDs: nodeId, inputPinId, outputPinId
        nodeIds.put(name,      nextId);
        inputPinIds.put(name,  nextId + 1);
        outputPinIds.put(name, nextId + 2);
        nextId += 3;
    }

    private String findPassByInputId(int id)
    {
        return inputPinIds.entrySet()
                          .stream()
                          .filter(e -> e.getValue() == id)
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

    @Override
    public void onRender()
    {
        ImGui.begin(getName());
        ImNodes.beginNodeEditor();

        
        for (String passName : renderer.getPasses().keySet())
        {
            int nodeId   = nodeIds.get(passName);
            int inputId  = inputPinIds.get(passName);
            int outputId = outputPinIds.get(passName);
        
            long previewTex = renderer.getPasses().get(passName).getOutputTexture();
        
            ImNodes.beginNode(nodeId);
        
            // Title bar
            ImNodes.beginNodeTitleBar();
                ImGui.text(passName);
            ImNodes.endNodeTitleBar();
        
            // Input on left
            ImNodes.beginInputAttribute(inputId);
                ImGui.text("Input");
            ImNodes.endInputAttribute();
        
    
            ImGui.sameLine();
        
            // Move cursor to far right (based on region width minus text width)
            float labelWidth = ImGui.calcTextSize("Output").x;
            float fullWidth = 150;
            ImGui.setCursorPosX(ImGui.getCursorPosX() + fullWidth - labelWidth);
        
            ImNodes.beginOutputAttribute(outputId);
                ImGui.text("Output");
            ImNodes.endOutputAttribute();
        
            ImGui.image(
                previewTex,
                new ImVec2(192, 108),
                new ImVec2(0, 1),
                new ImVec2(1, 0),
                new ImVec4(1, 1, 1, 1), // White tint
                new ImVec4(0, 0, 0, 0)  // Transparent border
            );

        
            ImNodes.endNode();
        }
        


        linkMap.clear();
        renderer.getConnections().forEach((toPass, fromList) -> {
            int toInput = inputPinIds.get(toPass);
            for (String fromPass : fromList)
            {
                int fromOutput = outputPinIds.get(fromPass);
                int linkId = Objects.hash(fromOutput, toInput);
                

                ImNodes.link(linkId, fromOutput, toInput);
                linkMap.put(linkId, new LinkInfo(fromPass, toPass));

                

            }
        });
        
        // Check if a link is hovered or clicked


        
        ImNodes.endNodeEditor();
        ImGui.end();
        
        ImInt startAttr = new ImInt();
        ImInt endAttr   = new ImInt();
        if (ImNodes.isLinkCreated(startAttr, endAttr))
        {
            String from = findPassByOutputId(startAttr.get());
            String to   = findPassByInputId(endAttr.get());
            if (from != null && to != null)
            {
                renderer.connect(from, to);
            }
        }


        ImInt selectedLinkId = new ImInt();
        if (ImNodes.isLinkHovered(selectedLinkId)) {
            // If the link is hovered, it can be marked as selected.
            int linkId = selectedLinkId.get();
            
            // Check if the Delete key is pressed
            if (ImGui.isKeyPressed(ImGuiKey.Delete)) {
                // Remove the link from your link map
                LinkInfo info = linkMap.remove(linkId);
                
                // If the link was found, disconnect it
                if (info != null) {
                    renderer.disconnect(info.from, info.to);
                }
            }
        }

    }
}
