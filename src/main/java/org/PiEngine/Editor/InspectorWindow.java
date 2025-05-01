package org.PiEngine.Editor;

import imgui.ImGui;
import imgui.flag.ImGuiInputTextFlags;
import imgui.flag.ImGuiTreeNodeFlags;

import org.PiEngine.GameObjects.*;
import org.PiEngine.Math.Vector;
import org.PiEngine.Utils.ComponentFactory;
import org.reflections.Reflections;
import org.PiEngine.Component.*;
import org.PiEngine.Core.LayerManager;
import org.PiEngine.Editor.Serialization.VectorField;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Consumer;
import java.util.function.Supplier;

import imgui.type.ImBoolean;
import imgui.type.ImInt;
import imgui.type.ImString;



public class InspectorWindow extends EditorWindow {
    public static GameObject inspectObject = null;
    public static GameObject root = null;
    public GameObject propertyObject = null;
    private final ImString searchBuffer = new ImString(64); // or larger size as needed
    public static int count = 0;


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

    public InspectorWindow()
    {
        super("Inspector");
        actAsProperty = false;
        id = count++;
    }

    /**
     * Constructor to initialize the InspectorWindow.
     * Sets whether this window acts as a property editor.
     */
    public InspectorWindow(boolean isproperty) {

        super((!isproperty ? "Inspector" : "Property"));
        actAsProperty = isproperty;
        id = count++;
    }

    /**
     * Renders the GUI contents of the Inspector or Property window.
     * Displays Transform and Component information for the selected GameObject.
     */
    @Override
    public void onRender() {
        String sname = !actAsProperty ? name : "Property (" + propertyObject.Name +")";
        GameObject current = actAsProperty ? propertyObject : inspectObject;
    
        // Define a boolean to control the window's visibility
        ImBoolean isOpen = new ImBoolean(true);
    
        // Begin the window with a close button if actAsProperty is true
        if (!ImGui.begin(sname + "##" + id, isOpen)) {
            ImGui.end();
            return;
        }
    
        // Check if the window was closed
        if (!isOpen.get()) {
            Editor.get().queueRemoveWindow(this);
        }
    
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
        ImGui.sameLine();
        if (ImGui.combo("##LayerCombo", selected, layers, layers.length)) {
            current.setLayerByName(LayerManager.getLayerName(selected.get()), false);
        }
        ImGui.separator();
    
        if (ImGui.collapsingHeader("Transform", ImGuiTreeNodeFlags.DefaultOpen)) {
            renderTransformEditor(current);
        }
    
        if (ImGui.collapsingHeader("Components", ImGuiTreeNodeFlags.DefaultOpen)) {
            renderComponentEditor(current);
        }
    
        if (ImGui.button("Add Component")) {
            ImGui.openPopup("AddComponentMenu");
        }
    
        if (ImGui.beginPopup("AddComponentMenu")) {
            ImGui.text("Add Component:");
            ImGui.separator();
        
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
    private final Map<String, VectorField> transformBlocks = new HashMap<>();
    private void renderTransformEditor(GameObject obj)
    {
        ImGui.text("GLOBAL");
        ImGui.separator();

        String id = obj.Name;

        transformBlocks.computeIfAbsent(id + "_worldPos", k -> new VectorField("Position  ", "worldPos"))
            .syncWith(obj.transform::getWorldPosition, obj.transform::setWorldPosition);
        transformBlocks.get(id + "_worldPos").handle();

        transformBlocks.computeIfAbsent(id + "_worldRot", k -> new VectorField("Rotation  ", "worldRot"))
            .syncWith(obj.transform::getWorldRotation, obj.transform::setWorldRotation);
        transformBlocks.get(id + "_worldRot").handle();

        transformBlocks.computeIfAbsent(id + "_worldScale", k -> new VectorField("Size      ", "worldScale"))
            .syncWith(obj.transform::getWorldScale, obj.transform::setWorldScale);
        transformBlocks.get(id + "_worldScale").handle();

        ImGui.separator();
        ImGui.text("LOCAL");
        ImGui.separator();

        transformBlocks.computeIfAbsent(id + "_localPos", k -> new VectorField("Position  ", "localPos"))
            .syncWith(obj.transform::getLocalPosition, obj.transform::setLocalPosition);
        transformBlocks.get(id + "_localPos").handle();

        transformBlocks.computeIfAbsent(id + "_localRot", k -> new VectorField("Rotation  ", "localRot"))
            .syncWith(obj.transform::getLocalRotation, obj.transform::setLocalRotation);
        transformBlocks.get(id + "_localRot").handle();

        transformBlocks.computeIfAbsent(id + "_localScale", k -> new VectorField("Size      ", "localScale"))
            .syncWith(obj.transform::getLocalScale, obj.transform::setLocalScale);
        transformBlocks.get(id + "_localScale").handle();

        ImGui.separator();
    }

    private void handleVectorField(String key, String name, String label, Vector current, Consumer<Vector> setter)
    {
        VectorField field = transformBlocks.computeIfAbsent(key, k -> new VectorField(name, label));

        if (!ImGui.isAnyItemActive())
        {
            field.set(current);
        }

        field.draw();

        if (ImGui.isAnyItemActive())
        {
            setter.accept(field.get());
        }
    }

    /**
     * Renders editable component data for all components attached to a GameObject.
     */
    private void renderComponentEditor(GameObject obj) 
    {
        ImGui.text("All Component Properties of " + obj.Name);
        ImGui.separator();

        List<Component> components = new ArrayList<>(obj.getAllComponents()); // Copy to avoid modification issues
        for (Component c : components) {
            String compName = c.getClass().getSimpleName();
            int compId = System.identityHashCode(c);
            ImGui.pushID(compId);

            if (ImGui.collapsingHeader(compName, ImGuiTreeNodeFlags.DefaultOpen)) {
                // Right-click context menu
                if (ImGui.beginPopupContextItem("ComponentContext")) {
                    if (ImGui.menuItem("Remove Component")) {
                        obj.removeComponent(c);
                        ImGui.endPopup();
                        ImGui.popID();
                        break;
                    }
                    ImGui.endPopup();
                }

                ComponentPropertyBlock comp = new ComponentPropertyBlock(compName);
                comp.drawComponentFields(c);
            }

            ImGui.popID();
        }
    }

}
