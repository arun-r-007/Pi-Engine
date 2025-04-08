package org.PiEngine.Editor;

import imgui.ImGui;
import imgui.flag.ImGuiTreeNodeFlags;
import org.PiEngine.GameObjects.GameObject;
import org.PiEngine.GameObjects.Transform;

import java.util.List;

public class HierarchyWindow extends EditorWindow
{
    private GameObject root;

    public HierarchyWindow(GameObject root)
    {
        super("Hierarchy");
        this.root = root;
    }

    public void setRoot(GameObject root)
    {
        this.root = root;
    }

    @Override
    public void onRender()
    {
        if (!isOpen || root == null) return;

        ImGui.begin("Hierarchy"); 

        renderGameObjectHierarchy(root);

        ImGui.end();
    }

    private void renderGameObjectHierarchy(GameObject obj)
    {
        int flags = ImGuiTreeNodeFlags.OpenOnArrow | ImGuiTreeNodeFlags.DefaultOpen;

        if (obj.transform.getChildren().isEmpty())
        {
            flags |= ImGuiTreeNodeFlags.Leaf;
        }

        boolean nodeOpen = ImGui.treeNodeEx(obj.Name, flags);

        if (ImGui.isItemClicked())
        {
            // Set the static object in Inspector
            InspectorWindow.inspectObject = obj;
        }

        if (nodeOpen)
        {
            List<Transform> children = obj.transform.getChildren();
            for (Transform child : children)
            {
                GameObject childObj = child.getGameObject();
                if (childObj != null)
                {
                    renderGameObjectHierarchy(childObj);
                }
            }

            ImGui.treePop();
        }
    }
}
