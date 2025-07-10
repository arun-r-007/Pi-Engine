package org.PiEngine.Editor;

import imgui.ImGui;
import imgui.ImGuiStyle;
import imgui.ImVec4;
import imgui.flag.ImGuiCol;
import imgui.flag.ImGuiInputTextFlags;
import imgui.flag.ImGuiKey;
import imgui.flag.ImGuiMouseButton;
import imgui.flag.ImGuiTreeNodeFlags;
import imgui.type.ImBoolean;
import imgui.type.ImString;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Iterator;

import org.PiEngine.Engine.Scene;
import org.PiEngine.GameObjects.GameObject;
import org.PiEngine.GameObjects.Transform;

/**
 * Renders a hierarchical view of all GameObjects starting from the root.
 * Supports selection, renaming, adding, and removing GameObjects.
 */
public class HierarchyWindow extends EditorWindow {
    private GameObject root;
    private GameObject renamingObject = null;
    private ImString renameBuffer = new ImString(64);
    private boolean renameFieldFocused = false;

    private final List<GameObject> toRemove = new ArrayList<>();
    private final List<InspectorWindow> windowsToAdd = new ArrayList<>();
    private final List<List<GameObject>> toReparent = new ArrayList<>();

    public static int count = 0;


    public HierarchyWindow() {
        super("Hierarchy");
        id = count++;
        
    }

    public void setRoot(GameObject root) {
        this.root = root;
    }

    public void onUpdate()
    {
        root = Scene.getInstance().getRoot();
        setCustomTheme();

        Iterator<List<GameObject>> reparentIterator = toReparent.iterator();
        while (reparentIterator.hasNext())
        {
            List<GameObject> pair = reparentIterator.next();
            if (pair.size() == 2)
            {
                GameObject child = pair.get(0);
                GameObject parent = pair.get(1);
                child.reparentTo(parent);
            }
            reparentIterator.remove(); // clear after processing
        }
        
        Iterator<InspectorWindow> addIterator = windowsToAdd.iterator();
        while (addIterator.hasNext())
        {
            EditorWindow win = addIterator.next();
            Editor.get().addWindow(win);
            addIterator.remove(); // remove from pending list
        }

        for (GameObject gameObject : toRemove) 
        {
            GameObject.destroy(gameObject);    
        }
        toRemove.clear();
    }

    /**
     * Called every frame to render the hierarchy window contents.
     */
    @Override
   public void onRender() 
   {
        // ImGui.pushStyleColor(ImGuiCol.WindowBg, ImGui.colorConvertFloat4ToU32(0.14f, 0.14f, 0.5f, 1.00f)); // Light blue background
        if (!isOpen || root == null) return;
        
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
        
        renderGameObjectHierarchy(Scene.getInstance().getRoot());
        ImGui.end();
        // ImGui.popStyleColor(1);

        


        // ImGui.popStyleColor(1);
    }



    /**
     * Recursively renders each GameObject in the hierarchy.
     * Handles input for renaming, context menus, and selection.
     */
    private void renderGameObjectHierarchy(GameObject obj) {
        ImGui.pushID(obj.hashCode());
    
        boolean isRoot = (obj == root);
        boolean isLeaf = obj.transform.getChildren().isEmpty();
        int flags = ImGuiTreeNodeFlags.OpenOnArrow | ImGuiTreeNodeFlags.DefaultOpen; 
        if (isLeaf) flags |= ImGuiTreeNodeFlags.Leaf;
    
        boolean nodeOpen;
    
        // --- Root is View Only ---
        if (isRoot) {
            nodeOpen = ImGui.treeNodeEx(obj.Name + " (Root)", flags);
            if (ImGui.beginPopupContextItem(obj.Name)) {    
                if (ImGui.menuItem("Add Object")) {
                    GameObject newChild = new GameObject("NewGameObject");
                    obj.addChild(newChild);
                }
                ImGui.endPopup();
            }

            if (ImGui.beginDragDropTarget()) {
                Object payloadObj = ImGui.acceptDragDropPayload("GAME_OBJECT");
                if (payloadObj instanceof GameObject) {
                    GameObject draggedObj = (GameObject) payloadObj;
                    if (draggedObj != obj) {
                        toReparent.add(Arrays.asList(draggedObj, obj)); 
                        System.out.println(toReparent);
                    }
                }
                ImGui.endDragDropTarget();
            }

        } 
        // --- Normal Object ---
        else if (renamingObject == obj) {
            ImGui.setNextItemWidth(200);
            if (ImGui.inputText("##rename", renameBuffer, ImGuiInputTextFlags.EnterReturnsTrue | ImGuiInputTextFlags.AutoSelectAll)) {
                obj.Name = renameBuffer.get().trim();
                renamingObject = null;
                renameFieldFocused = false;
            }
    
            if (ImGui.isItemActive()) {
                renameFieldFocused = true;
            } else if (renameFieldFocused && !ImGui.isItemHovered()) {
                obj.Name = renameBuffer.get().trim(); // Commit rename on blur
                renamingObject = null;
                renameFieldFocused = false;
            }
    
            nodeOpen = ImGui.treeNodeEx("##hidden", flags, "");
        } 
        else {
            nodeOpen = ImGui.treeNodeEx(obj.Name, flags);
    
            // Selection and rename
            boolean hovered = ImGui.isItemHovered();
            boolean released = ImGui.isMouseReleased(ImGuiMouseButton.Left);

            if (hovered && released && !ImGui.beginDragDropSource()) {
                // Click-and-release on item, no drag
                InspectorWindow.inspectObject = obj;
                InspectorWindow.root = this.root;
            }
    
            // F2 Shortcut
            if ((ImGui.isItemHovered() && ImGui.isKeyPressed(ImGuiKey.F2))) {
                renamingObject = obj;
                renameFieldFocused = false;
                renameBuffer.set(obj.Name);
            }

            // DELETE Shortcut
            if ((ImGui.isItemHovered() && ImGui.isKeyPressed(ImGuiKey.F2))) {
                renamingObject = obj;
                renameFieldFocused = false;
                renameBuffer.set(obj.Name);
            }

            if ((ImGui.isItemHovered() && ImGui.isKeyPressed(ImGuiKey.Delete) )) {
                toRemove.add(obj);
                if (renamingObject == obj) {
                    renamingObject = null;
                    renameFieldFocused = false;
                }
            }
    
            // Drag source
            if (ImGui.beginDragDropSource()) {
                ImGui.setDragDropPayload("GAME_OBJECT", obj); // Use the GameObject directly
                ImGui.text("Dragging: " + obj.Name);
                ImGui.endDragDropSource();
            }
    
            // Drop target
            if (ImGui.beginDragDropTarget()) {
                Object payloadObj = ImGui.acceptDragDropPayload("GAME_OBJECT");
                if (payloadObj instanceof GameObject) {
                    GameObject draggedObj = (GameObject) payloadObj;
                    if (draggedObj != obj) {
                        toReparent.add(Arrays.asList(draggedObj, obj)); // Reparent dragged object to the drop target
                    }
                }
                ImGui.endDragDropTarget();
            }
    
            // Context menu
            if (ImGui.beginPopupContextItem(obj.Name)) {
                if (ImGui.menuItem("Property")) {
                    InspectorWindow n = new InspectorWindow(true);
                    n.propertyObject = obj;
                    
                    Editor.get().queueAddWindow(n);
                }
                
                if (ImGui.menuItem("Add Object")) {
                    GameObject newChild = new GameObject("NewGameObject");
                    obj.addChild(newChild);
                }
                if (ImGui.menuItem("Remove")) {
                    toRemove.add(obj);
                    if (renamingObject == obj) {
                        renamingObject = null;
                        renameFieldFocused = false;
                    }
                }
                ImGui.endPopup();
            }
        }
    
        // Render children (always)
        if (!nodeOpen) {
            ImGui.popID();
            return;
        }
    
        List<Transform> children = obj.transform.getChildren();
        for (Transform child : children) {
            if (child == null) continue;
            GameObject childObj = child.getGameObject();
            if (childObj == null) continue;
            renderGameObjectHierarchy(childObj);
        }
    
        ImGui.treePop();
        ImGui.popID();
    }

    @Override
    public void setCustomTheme() {
        ImGuiStyle style = ImGui.getStyle();
        ImVec4[] colors = style.getColors();
        //ImGui.styleColorsLight();

        colors[ImGuiCol.Text].set(0.30f, 5.0f, 0.30f, 0.50f);
        colors[ImGuiCol.WindowBg].set(0.13f, 0.14f, 0.15f, 1.0f);
        colors[ImGuiCol.Button].set(0.2f, 0.3f, 0.4f, 1.0f);
        colors[ImGuiCol.ButtonHovered].set(0.3f, 0.4f, 0.5f, 1.0f);
        colors[ImGuiCol.ButtonActive].set(0.4f, 0.5f, 0.6f, 1.0f);        

        style.setWindowRounding(.3f);
        style.setFrameRounding(.3f);
        style.setPopupRounding(2.3f);
    }
}
