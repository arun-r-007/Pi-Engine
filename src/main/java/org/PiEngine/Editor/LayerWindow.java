package org.PiEngine.Editor;

import imgui.ImGui;
import imgui.type.ImString;
import org.PiEngine.Core.LayerManager;

public class LayerWindow extends EditorWindow
{
    private static final int MAX_LAYERS = LayerManager.noOfLayers(); // Assuming you have this constant

    // Store editable names
    private final ImString[] editableNames = new ImString[MAX_LAYERS];

    public LayerWindow()
    {
        super("LayerWindow");

        // Initialize with current names
        String[] currentNames = LayerManager.GetLayerNameArray();
        for (int i = 0; i < MAX_LAYERS; i++) {
            editableNames[i] = new ImString(currentNames[i], 32); // limit 32 chars
        }
    }

    @Override
    public void onRender()
    {
        ImGui.begin("Layers");

        for (int i = 0; i < MAX_LAYERS; i++) {
            ImGui.text("Layer " + i);
            ImGui.sameLine();
            if (ImGui.inputText("##layerName" + i, editableNames[i])) {
                LayerManager.renameLayer(i, editableNames[i].get());
            }
        }

        ImGui.end();
    }
}
