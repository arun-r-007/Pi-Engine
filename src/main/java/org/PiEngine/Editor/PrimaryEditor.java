package org.PiEngine.Editor;

import imgui.ImGui;
import imgui.flag.ImGuiTreeNodeFlags;
import imgui.type.ImFloat;
import org.PiEngine.GameObjects.GameObject;
import org.PiEngine.GameObjects.Transform;
import org.PiEngine.Math.Vector;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PrimaryEditor {
    private final Map<GameObject, Boolean> propertyWindowState = new HashMap<>();

    public void imgui(GameObject world) {
        ImGui.begin("Game Object Hierarchy");
        renderGameObjectHierarchy(world);
        ImGui.end();

        // Render open property windows
        for (Map.Entry<GameObject, Boolean> entry : propertyWindowState.entrySet()) {
            GameObject obj = entry.getKey();
            boolean isOpen = entry.getValue();
            if (isOpen) {
                ImGui.begin(obj.Name + " Properties");
                renderTransformEditor(obj);
                ImGui.end();
            }
        }
    }

    private void renderGameObjectHierarchy(GameObject obj) {
        int flags = ImGuiTreeNodeFlags.OpenOnArrow | ImGuiTreeNodeFlags.DefaultOpen;
        if (obj.transform.getChildren().isEmpty()) {
            flags |= ImGuiTreeNodeFlags.Leaf;
        }

        boolean nodeOpen = ImGui.treeNodeEx(obj.Name, flags);

        if (ImGui.isItemClicked()) {
            propertyWindowState.put(obj, !propertyWindowState.getOrDefault(obj, false));
        }

        if (nodeOpen) {
            List<Transform> children = obj.transform.getChildren();
            for (Transform childTransform : children) {
                GameObject childObj = childTransform.getGameObject();
                if (childObj != null) {
                    renderGameObjectHierarchy(childObj);
                }
            }
            ImGui.treePop();
        }
    }

    private void renderTransformEditor(GameObject obj) {
        ImGui.text("All Transform Properties of " + obj.Name);
    
        // World Position
        VectorPropertyBlock worldPosBlock = new VectorPropertyBlock("worldPos");
        worldPosBlock.set(obj.transform.getWorldPosition());
        worldPosBlock.draw("World Position");
        if (!ImGui.isAnyItemActive()) {
            obj.transform.setWorldPosition(worldPosBlock.get());
        }
        ImGui.separator();
    
        // Local Position
        VectorPropertyBlock localPosBlock = new VectorPropertyBlock("localPos");
        localPosBlock.set(obj.transform.getLocalPosition());
        localPosBlock.draw("Local Position");
        if (!ImGui.isAnyItemActive()) {
            obj.transform.setLocalPosition(localPosBlock.get());
        }
        ImGui.separator();
    
        // Local Rotation
        VectorPropertyBlock localRotBlock = new VectorPropertyBlock("localRot");
        localRotBlock.set(obj.transform.getLocalRotation());
        localRotBlock.draw("Local Rotation");
        if (!ImGui.isAnyItemActive()) {
            obj.transform.setLocalRotation(localRotBlock.get());
        }
        ImGui.separator();
    
        // Local Scale
        VectorPropertyBlock localScaleBlock = new VectorPropertyBlock("localScale");
        localScaleBlock.set(obj.transform.getLocalScale());
        localScaleBlock.draw("Local Scale");
        if (!ImGui.isAnyItemActive()) {
            obj.transform.setLocalScale(localScaleBlock.get());
        }
    }
    
    
}
