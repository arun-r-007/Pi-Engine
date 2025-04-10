package org.PiEngine.Editor;

import imgui.ImGui;
import imgui.flag.ImGuiInputTextFlags;
import imgui.flag.ImGuiMouseButton;
import imgui.flag.ImGuiTreeNodeFlags;
import imgui.type.ImString;

import java.util.ArrayList;
import java.util.List;
import java.util.Iterator;

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


    public HierarchyWindow(GameObject root) {
        super("Hierarchy");
        this.root = root;
    }

    public void setRoot(GameObject root) {
        this.root = root;
    }

    public void onUpdate(float deltaTime)
    {
        Iterator<InspectorWindow> addIterator = windowsToAdd.iterator();
        while (addIterator.hasNext())
        {
            EditorWindow win = addIterator.next();
            Editor.get().addWindow(win);
            addIterator.remove(); // remove from pending list
        }
    }

    /**
     * Called every frame to render the hierarchy window contents.
     */
    @Override
    public void onRender() {

        if (!isOpen || root == null) return;
        

        ImGui.begin("Hierarchy");
        renderGameObjectHierarchy(root);
        ImGui.end();

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
            if (ImGui.isItemClicked(ImGuiMouseButton.Left)) {
                InspectorWindow.inspectObject = obj;
                InspectorWindow.root = this.root;
            }
    
            if (ImGui.isItemClicked(ImGuiMouseButton.Left) && ImGui.isMouseDoubleClicked(ImGuiMouseButton.Left)) {
                renamingObject = obj;
                renameFieldFocused = false;
                renameBuffer.set(obj.Name);
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
                        draggedObj.reparentTo(obj); // Reparent dragged object to the drop target
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
}
