package org.PiEngine.Editor;

import imgui.ImGui;
import imgui.type.ImString;
import org.PiEngine.Core.LayerManager;

public class LayerWindow extends EditorWindow
{
    private static final int MAX_LAYERS = LayerManager.noOfLayers();

    private final ImString[] editableNames = new ImString[MAX_LAYERS];
    private final String[] originalNames = new String[MAX_LAYERS];
    private final boolean[] showErrorPopup = new boolean[MAX_LAYERS]; // control per-layer popup logic

    public LayerWindow()
    {
        super("LayerWindow");

        String[] currentNames = LayerManager.GetLayerNameArray();
        for (int i = 0; i < MAX_LAYERS; i++) {
            editableNames[i] = new ImString(currentNames[i], 32);
            originalNames[i] = currentNames[i];
            showErrorPopup[i] = false;
        }
    }

    @Override
    public void onRender()
    {
        ImGui.begin("Layers");

        for (int i = 0; i < MAX_LAYERS; i++) {
            ImGui.text("Layer" + i);
            ImGui.sameLine();

            ImGui.inputText("##layerName" + i, editableNames[i]);

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

        ImGui.end();
    }
}
