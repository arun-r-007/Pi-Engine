package org.PiEngine.Editor;

import imgui.ImGui;
import imgui.flag.ImGuiInputTextFlags;
import imgui.flag.ImGuiTreeNodeFlags;
import imgui.type.ImBoolean;
import imgui.type.ImString;
import org.PiEngine.Render.Renderer;
import org.PiEngine.Core.LayerManager;
import org.PiEngine.Engine.Console;
import org.PiEngine.Render.RenderPass;
import org.PiEngine.Render.RenderPassFactory;

import java.util.*;

public class RendererInspector extends EditorWindow
{
    private final Renderer renderer;
    private final ImString searchBuffer = new ImString(64);
    String[] RenamePass = new String[2];
    RenderPass passToRename;
    boolean rename;
    public static int count = 0;
    

    public RendererInspector(Renderer renderer)
    {
        super("Renderer Inspector");
        this.renderer = renderer;
        id = count++;
    }

    @Override
    public void onRender()
    {
        ImBoolean isOpen = new ImBoolean(true);
        if (!ImGui.begin(name + "##" + id, isOpen))
        {
            ImGui.end();
            return;
        }

        if (!isOpen.get())
        {
            Editor.get().queueRemoveWindow(this);
        }

        ImGui.text("Render Passes:");
        ImGui.separator();

        // List current passes
        List<String> removeQueue = new ArrayList<>();
        List<RenderPass> addQueue = new ArrayList<>();
        for (Map.Entry<String, RenderPass> entry : renderer.getPasses().entrySet())
        {
            String passName = entry.getKey();
            RenderPass pass = entry.getValue();

            ImGui.pushID(passName);

            // Use the class name as the heading
            String className = pass.getClass().getSimpleName();
            if (ImGui.collapsingHeader(className, ImGuiTreeNodeFlags.DefaultOpen))
            {
                // Right-click context menu for removing pass
                if (ImGui.beginPopupContextItem("PassContext"))
                {
                    if (ImGui.menuItem("Remove Pass"))
                    {
                        removeQueue.add(passName);
                    }
                    ImGui.endPopup();
                }

                // Editable Pass Name
                // ImGui.text("Name   ");
                // ImGui.sameLine();
                String oldName = pass.getName();
                ImString buffer = new ImString(oldName, 32);  // Create a buffer with the current name

                if (ImGui.inputText("Name", buffer, ImGuiInputTextFlags.EnterReturnsTrue))
                {
                    String newName = buffer.get();  // Get the new name from the buffer

                    // Store the old and new names in an array of size 2
                    passToRename = pass;
                    RenamePass[0] = oldName;  // Old name
                    RenamePass[1] = newName;  // New name
                    rename = true;
                }

                // Display the output texture and input count
                // ImGui.text("Output Texture: " + pass.getOutputTexture());
                // ImGui.text("Input Count: " + pass.getInputCount());

                // Dropdown for Layer Mask Selection (bitwise selection)
                int currentLayerMask = pass.getLayerMask();
                String[] layers = LayerManager.GetLayerNameArray();
                String selected;
                int count = Integer.bitCount(currentLayerMask);
                if ( count > 1)
                {
                    selected = "Multiple Layers"; 
                }
                else if(count == 1)
                {
                    selected = LayerManager.getLayerNameFromBitmask(currentLayerMask);
                }
                else
                {
                    selected = "None";
                }

                // ImGui.text("Layers ");
                // ImGui.sameLine();

                // Begin Combo Box
                if (ImGui.beginCombo("Layers", selected))
                {
                    // Iterate over the layers and create selectable items
                    for (int i = 0; i < layers.length; i++)
                    {
                        if (ImGui.checkbox(layers[i], (currentLayerMask & (1 << i)) != 0))
                        {
                            // Update layer mask with the selected layer
                            if ((currentLayerMask & (1 << i)) != 0)
                            {
                                currentLayerMask &= ~(1 << i); // Remove layer from mask
                            }
                            else
                            {
                                currentLayerMask |= (1 << i); // Add layer to mask
                            }
                            pass.setLayerMask(currentLayerMask);
                        }
                    }

                    // End the combo box properly after rendering the selections
                    ImGui.endCombo();
                }


            }

            ImGui.popID();
        }

        // Remove passes after processing
        for (String passNames : removeQueue)
        {
            renderer.removePass(passNames);
        }

        // Add passes after processing
        ImGui.separator();

        if (ImGui.button("Add Pass"))
        {
            ImGui.openPopup("AddPassPopup");
        }

        if (ImGui.beginPopup("AddPassPopup"))
        {
            ImGui.text("Add New Pass:");
            ImGui.inputText("##PassSearch", searchBuffer, 64);

            String searchQuery = searchBuffer.get().toLowerCase().trim();
            for (String availablePass : RenderPassFactory.getRegisteredRenderPassNames()) {
                if (!searchQuery.isEmpty() && !availablePass.toLowerCase().contains(searchQuery))
                {
                    continue;
                }

                if (ImGui.menuItem(availablePass))
                {
                    RenderPass newPass = RenderPassFactory.create(availablePass);
                    if (newPass != null)
                    {
                        // Queue the pass to be added
                        addQueue.add(newPass);
                    }
                }
            }

            ImGui.endPopup();
        }

        for (RenderPass passToAdd : addQueue)
        {
            renderer.addPass(passToAdd);
        }

        if (rename && !RenamePass[0].equals(RenamePass[1]))  // Only update if the name has changed
        {
            renderer.updatePassName(RenamePass[0], RenamePass[1]);  // Update using the old and new names from the array
            passToRename.setName(RenamePass[1]);  // Set the new name to the pass
            Console.warning("Updated pass name: " + RenamePass[1]);
            rename = false;
        }

        ImGui.end();
    }
}
