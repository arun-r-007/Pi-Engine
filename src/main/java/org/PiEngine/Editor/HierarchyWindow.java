package org.PiEngine.Editor;

import imgui.ImGui;
import imgui.flag.ImGuiInputTextFlags;
import imgui.flag.ImGuiMouseButton;
import imgui.flag.ImGuiTreeNodeFlags;
import imgui.type.ImString;
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

    public HierarchyWindow(GameObject root) {
        super("Hierarchy");
        this.root = root;
    }

    public void setRoot(GameObject root) {
        this.root = root;
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
        ImGui.pushID(obj.hashCode()); // Unique ID per object

        boolean isLeaf = obj.transform.getChildren().isEmpty();
        int flags = ImGuiTreeNodeFlags.OpenOnArrow | ImGuiTreeNodeFlags.DefaultOpen;
        if (isLeaf) flags |= ImGuiTreeNodeFlags.Leaf;

        boolean nodeOpen;

        // Handle renaming UI
        if (renamingObject == obj) {
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

            nodeOpen = ImGui.treeNodeEx("##hidden", flags, ""); // Placeholder node
        } else {
            nodeOpen = ImGui.treeNodeEx(obj.Name, flags);

            // Select object on click
            if (ImGui.isItemClicked(ImGuiMouseButton.Left)) {
                InspectorWindow.inspectObject = obj;
                InspectorWindow.root = this.root;
            }

            // Rename on double-click
            if (ImGui.isItemClicked(ImGuiMouseButton.Left) && ImGui.isMouseDoubleClicked(ImGuiMouseButton.Left)) {
                renamingObject = obj;
                renameFieldFocused = false;
                renameBuffer.set(obj.Name);
            }
        }

        // Context menu for right-click options
        if (ImGui.beginPopupContextItem(obj.Name)) {
            // Add child object
            if (ImGui.menuItem("Add Object")) {
                GameObject newChild = new GameObject("NewGameObject");
                obj.transform.getChildren().add(newChild.transform);
            }

            // Remove object from hierarchy
            if (ImGui.menuItem("Remove")) {
                Transform parent = obj.transform.getParent();
                if (parent != null) {
                    parent.getChildren().remove(obj.transform);
                }
                if (renamingObject == obj) {
                    renamingObject = null;
                    renameFieldFocused = false;
                }
            }

            ImGui.endPopup();
        }

        // Recursively draw children
        if (nodeOpen) {
            for (Transform child : obj.transform.getChildren()) {
                GameObject childObj = child.getGameObject();
                if (childObj != null) {
                    renderGameObjectHierarchy(childObj);
                }
            }
            ImGui.treePop();
        }

        ImGui.popID(); // Restore previous ID
    }
}
