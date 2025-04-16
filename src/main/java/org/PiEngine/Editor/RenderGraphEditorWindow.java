package org.PiEngine.Editor;

import org.PiEngine.Render.*;

import imgui.ImGui;
import imgui.ImGuiIO;
import imgui.flag.ImGuiWindowFlags;
import imgui.type.ImFloat;

import java.util.HashMap;
import java.util.Map;

public class RenderGraphEditorWindow extends EditorWindow
{
    private RenderGraph graph;

    private static class NodeData
    {
        public float x, y;
        public NodeData(float x, float y) { this.x = x; this.y = y; }
    }

    private final Map<RenderPass, NodeData> nodePositions = new HashMap<>();

    public RenderGraphEditorWindow(RenderGraph graph)
    {
        super("Render Graph Editor");
        this.graph = graph;
    }

    @Override
    public void onRender()
    {
        // Make the full window behave like a canvas
        ImGui.beginChild("NodeCanvas", 0, 0, true,
            ImGuiWindowFlags.NoScrollbar | ImGuiWindowFlags.NoScrollWithMouse);

        float canvasOffsetX = ImGui.getCursorScreenPosX();
        float canvasOffsetY = ImGui.getCursorScreenPosY();

        for (RenderPass pass : graph.getAllPasses())
        {
            nodePositions.putIfAbsent(pass, new NodeData(100 + 200 * nodePositions.size(), 100));

            NodeData pos = nodePositions.get(pass);

            ImGui.setCursorScreenPos(pos.x + canvasOffsetX, pos.y + canvasOffsetY);

            ImGui.beginChild("Node_" + pass.getName(), 280, 180, true, ImGuiWindowFlags.AlwaysAutoResize);
            ImGui.text(pass.getName());

            int texID = pass.getOutputTexture();
            if (texID > 0)
            {
                ImGui.image(texID, 256, 144, 0, 1, 1, 0);
            }
            else
            {
                ImGui.text("No output texture.");
            }

            ImGui.endChild();

            // Allow dragging the node
            if (ImGui.isItemActive() && ImGui.isMouseDragging(0))
            {
                pos.x += ImGui.getIO().getMouseDeltaX();
                pos.y += ImGui.getIO().getMouseDeltaY();
            }
        }

        ImGui.endChild();
    }
}
