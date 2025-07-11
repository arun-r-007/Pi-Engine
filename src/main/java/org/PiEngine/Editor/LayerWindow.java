package org.PiEngine.Editor;

import imgui.ImGui;
import imgui.type.ImBoolean;
import imgui.type.ImString;
import org.PiEngine.Core.LayerManager;

public class LayerWindow extends EditorWindow {
    private static final int MAX_LAYERS = LayerManager.noOfLayers();

    private final ImString[] editableNames = new ImString[MAX_LAYERS];
    private final String[] originalNames = new String[MAX_LAYERS];
    private final boolean[] showErrorPopup = new boolean[MAX_LAYERS]; // control per-layer popup logic
    public static int count = 0;
    

    /**
     * Constructs a new LayerWindow and initializes layer names.
     */
    public LayerWindow() {
        super("Layer");
        id = count++;
        
        String[] currentNames = LayerManager.GetLayerNameArray();
        for (int i = 0; i < MAX_LAYERS; i++) {
            editableNames[i] = new ImString(currentNames[i], 32);
            originalNames[i] = currentNames[i];
            showErrorPopup[i] = false;
        }
    }

    /**
     * Renders the layer window and allows editing layer names.
     */
    @Override
    public void onRender() {
        

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

        // Set up the scrollable region with a fixed height
        //final float windowHeight = 400.0f; // Set your desired height here
        //ImGui.beginChild("LayerList", -1, windowHeight, true);  // -1 for auto width, fixed height, scrolling enabled

        for (int i = 0; i < MAX_LAYERS; i++) {
            // ImGui.text("Layer " + i);
            // ImGui.sameLine();

            ImGui.inputText("Layer " + i, editableNames[i]);

            if (ImGui.isItemDeactivatedAfterEdit()) {
                String newName = editableNames[i].get();
                boolean nameExists = false;

                for (int j = 0; j < MAX_LAYERS; j++) {
                    if (i != j && newName.equals(editableNames[j].get())) {
                        nameExists = true;
                        break;
                    }
                }

                if (!nameExists) {
                    LayerManager.renameLayer(i, newName);
                    originalNames[i] = newName; // Update reference name on success
                } else {
                    showErrorPopup[i] = true;
                    ImGui.openPopup("Name Error##" + i);
                }
            }

            if (showErrorPopup[i] && ImGui.beginPopup("Name Error##" + i)) {
                ImGui.text("Error: Layer name already exists!");
                if (ImGui.button("Close##" + i)) {
                    editableNames[i].set(originalNames[i]); // Revert to old name
                    showErrorPopup[i] = false;
                    ImGui.closeCurrentPopup();
                }
                ImGui.endPopup();
            }
        }

        // End the scrollable child window
        //ImGui.endChild();

        ImGui.end();
    }
}
