package org.PiEngine.Editor;

import imgui.ImGui;
import org.PiEngine.GameObjects.*;
import org.PiEngine.Component.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;

public class InspectorWindow extends EditorWindow {
    public static GameObject inspectObject = null;
    public static GameObject root = null;
    public GameObject propertyObject = null;

    public boolean actAsProperty = false;

    private static final Map<String, Supplier<Component>> componentFactory = new HashMap<>();

    static {
        componentFactory.put("Follower", Follower::new);
        componentFactory.put("Movement", Movemet::new);
        componentFactory.put("SpinComponent", SpinComponent::new);
    }

    /**
     * Constructor to initialize the InspectorWindow.
     * Sets whether this window acts as a property editor.
     */
    public InspectorWindow(boolean isproperty) {
        super(!isproperty ? "Inspector" : "Property");
        actAsProperty = isproperty;
    }

    /**
     * Renders the GUI contents of the Inspector or Property window.
     * Displays Transform and Component information for the selected GameObject.
     */
    @Override
    public void onRender() {
        GameObject current = actAsProperty ? propertyObject : inspectObject;
        ImGui.begin(name);

        if (current == null) {
            ImGui.text("No GameObject selected.");
            ImGui.end();
            return;
        }

        ImGui.text("Inspecting: " + current.Name);
        ImGui.separator();

        if (ImGui.collapsingHeader("Transform")) {
            renderTransformEditor(current);
        }

        if (ImGui.collapsingHeader("Components")) {
            renderComponentEditor(current);
        }

        // Right-click context menu to add components
        if (ImGui.beginPopupContextItem("AddComponentMenu")) {
            ImGui.text("Add Component:");
            ImGui.separator();

            String[] availableComponents = {
                "Follower",
                "Movement",
                "SpinComponent"
            };

            for (String compName : availableComponents) {
                if (ImGui.menuItem(compName)) {
                    Supplier<Component> constructor = componentFactory.get(compName);
                    if (constructor != null) {
                        Component newComponent = constructor.get();
                        inspectObject.addComponent(newComponent);
                    }
                }
            }
            ImGui.endPopup();
        }

        ImGui.end();
    }

    /**
     * Renders the editable Transform data for the given GameObject.
     * Allows editing of both local and global transform values.
     */
    private void renderTransformEditor(GameObject obj) {
        ImGui.text("All Transform Properties of " + obj.Name);
        ImGui.separator();

        ImGui.text("GLOBAL");
        ImGui.separator();

        VectorPropertyBlock worldPosBlock = new VectorPropertyBlock("worldPos");
        worldPosBlock.set(obj.transform.getWorldPosition());
        worldPosBlock.draw("Position  ");
        if (!ImGui.isAnyItemActive()) {
            obj.transform.setWorldPosition(worldPosBlock.get());
        }

        VectorPropertyBlock worldRotBlock = new VectorPropertyBlock("worldRot");
        worldRotBlock.set(obj.transform.getWorldRotation());
        worldRotBlock.draw("Rotation  ");
        if (!ImGui.isAnyItemActive()) {
            obj.transform.setWorldRotation(worldRotBlock.get());
        }

        VectorPropertyBlock worldScaleBlock = new VectorPropertyBlock("worldScale");
        worldScaleBlock.set(obj.transform.getWorldScale());
        worldScaleBlock.draw("Size      ");
        if (!ImGui.isAnyItemActive()) {
            obj.transform.setWorldScale(worldScaleBlock.get());
        }

        ImGui.separator();

        ImGui.text("LOCAL");
        ImGui.separator();

        VectorPropertyBlock localPosBlock = new VectorPropertyBlock("localPos");
        localPosBlock.set(obj.transform.getLocalPosition());
        localPosBlock.draw("Position  ");
        if (!ImGui.isAnyItemActive()) {
            obj.transform.setLocalPosition(localPosBlock.get());
        }

        VectorPropertyBlock localRotBlock = new VectorPropertyBlock("localRot");
        localRotBlock.set(obj.transform.getLocalRotation());
        localRotBlock.draw("Rotation  ");
        if (!ImGui.isAnyItemActive()) {
            obj.transform.setLocalRotation(localRotBlock.get());
        }

        VectorPropertyBlock localScaleBlock = new VectorPropertyBlock("localScale");
        localScaleBlock.set(obj.transform.getLocalScale());
        localScaleBlock.draw("Size      ");
        if (!ImGui.isAnyItemActive()) {
            obj.transform.setLocalScale(localScaleBlock.get());
        }

        ImGui.separator();
    }

    /**
     * Renders editable component data for all components attached to a GameObject.
     */
    private void renderComponentEditor(GameObject obj) {
        ImGui.text("All Component Properties of " + obj.Name);
        ImGui.separator();

        List<Component> components = obj.getAllComponents();
        for (Component c : components) {
            String compName = c.getClass().getSimpleName();
            if (ImGui.collapsingHeader(compName)) {
                ComponentPropertyBlock comp = new ComponentPropertyBlock(compName);
                comp.drawComponentFields(c, root);
            }
        }
    }
}
