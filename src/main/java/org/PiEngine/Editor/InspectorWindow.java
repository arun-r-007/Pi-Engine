package org.PiEngine.Editor;

import imgui.ImGui;
import imgui.flag.ImGuiInputTextFlags;

import org.PiEngine.GameObjects.*;
import org.reflections.Reflections;
import org.PiEngine.Component.*;
import org.PiEngine.Core.LayerManager;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Supplier;

import imgui.type.ImInt;
import imgui.type.ImString;



public class InspectorWindow extends EditorWindow {
    public static GameObject inspectObject = null;
    public static GameObject root = null;
    public GameObject propertyObject = null;
    private final ImString searchBuffer = new ImString(64); // or larger size as needed


    public boolean actAsProperty = false;

    private static final Map<String, Supplier<Component>> componentFactory = new HashMap<>();

    static {
        Reflections reflections = new Reflections("org.PiEngine.Component");

        Set<Class<? extends Component>> componentClasses = reflections.getSubTypesOf(Component.class);

        for (Class<? extends Component> compClass : componentClasses)
        {
            try {
                // Ensure it has a public no-arg constructor
                compClass.getConstructor();

                componentFactory.put(compClass.getSimpleName(), () -> {
                    try {
                        return compClass.getConstructor().newInstance();
                    } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
                        e.printStackTrace();
                        return null;
                    }
                });

            } catch (NoSuchMethodException e) {
                System.out.println("Skipping " + compClass.getSimpleName() + ": No public no-arg constructor.");
            }
        }
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
        name = !actAsProperty ? "Inspector" : "Property" + propertyObject;
        GameObject current = actAsProperty ? propertyObject : inspectObject;
        ImGui.begin(name);

        if (current == null) {
            ImGui.text("No GameObject selected.");
            ImGui.end();
            return;
        }

        ImGui.text("Inspecting: " + current.Name);
        ImGui.separator();


        ImGui.text("Layer");

        String[] layers = LayerManager.GetLayerNameArray();

        int currentLayer = LayerManager.getIndexFromBitmask(current.getLayerBit()); 
        ImInt selected = new ImInt(currentLayer);

        if (ImGui.combo("##LayerCombo", selected, layers, layers.length)) {
            current.setLayerByName(LayerManager.getLayerName(selected.get()), false);
        }
        

        if (ImGui.collapsingHeader("Transform")) {
            renderTransformEditor(current);
        }

        if (ImGui.collapsingHeader("Components")) {
            renderComponentEditor(current);
        }

        if (ImGui.button("Add Component")) // This is your button
        {
            ImGui.openPopup("AddComponentMenu"); // Open the same popup manually
        }
        
        // Now render the popup as usual
        if (ImGui.beginPopup("AddComponentMenu")) {
            ImGui.text("Add Component:");
            ImGui.separator();
        
            // Static buffer for search input
            ImGui.text("Search:");
            ImGui.sameLine();
            ImGui.inputText("##search", searchBuffer, ImGuiInputTextFlags.None);
            String searchQuery = searchBuffer.get().toLowerCase().trim();

            String[] availableComponents = ComponentFactory.getRegisteredComponentNames().toArray(new String[0]);
        
            for (String compName : availableComponents) {
                if (!searchQuery.isEmpty() && !compName.toLowerCase().contains(searchQuery)) {
                    continue;
                }
            
                if (ImGui.menuItem(compName)) {
                    Component newComponent = ComponentFactory.create(compName);
                    if (newComponent != null) {
                        if (actAsProperty) {
                            propertyObject.addComponent(newComponent);
                        } else {
                            inspectObject.addComponent(newComponent);
                        }
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

    List<Component> components = new ArrayList<>(obj.getAllComponents()); // Copy to avoid modification issues
    for (Component c : components) {
        String compName = c.getClass().getSimpleName();
        int compId = System.identityHashCode(c);
        ImGui.pushID(compId);

        if (ImGui.collapsingHeader(compName)) {
            // Right-click context menu
            if (ImGui.beginPopupContextItem("ComponentContext")) {
                if (ImGui.menuItem("Remove Component")) {
                    obj.removeComponent(c);
                    ImGui.endPopup();
                    ImGui.popID();
                    break; // Prevents list modification error
                }
                ImGui.endPopup();
            }

            ComponentPropertyBlock comp = new ComponentPropertyBlock(compName);
            comp.drawComponentFields(c, root);
        }

        ImGui.popID();
    }
}

}
