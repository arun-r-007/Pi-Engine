package org.PiEngine.Editor;

import imgui.ImGui;
import org.PiEngine.GameObjects.Transform;

public class TransformRenderer
{
    public static void render(Transform transform)
    {
        // GLOBAL
        ImGui.text("GLOBAL");
        ImGui.separator();

        var worldPos = new VectorPropertyBlock("worldPos");
        worldPos.set(transform.getWorldPosition());
        worldPos.draw("Position  ");
        if (!ImGui.isAnyItemActive()) transform.setWorldPosition(worldPos.get());

        var worldRot = new VectorPropertyBlock("worldRot");
        worldRot.set(transform.getWorldRotation());
        worldRot.draw("Rotation  ");
        if (!ImGui.isAnyItemActive()) transform.setWorldRotation(worldRot.get());

        var worldScale = new VectorPropertyBlock("worldScale");
        worldScale.set(transform.getWorldScale());
        worldScale.draw("Size      ");
        if (!ImGui.isAnyItemActive()) transform.setWorldScale(worldScale.get());

        ImGui.separator();

        // LOCAL
        ImGui.text("LOCAL");
        ImGui.separator();

        var localPos = new VectorPropertyBlock("localPos");
        localPos.set(transform.getLocalPosition());
        localPos.draw("Position  ");
        if (!ImGui.isAnyItemActive()) transform.setLocalPosition(localPos.get());

        var localRot = new VectorPropertyBlock("localRot");
        localRot.set(transform.getLocalRotation());
        localRot.draw("Rotation  ");
        if (!ImGui.isAnyItemActive()) transform.setLocalRotation(localRot.get());

        var localScale = new VectorPropertyBlock("localScale");
        localScale.set(transform.getLocalScale());
        localScale.draw("Size      ");
        if (!ImGui.isAnyItemActive()) transform.setLocalScale(localScale.get());

        ImGui.separator();
    }
}
