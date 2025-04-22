package org.PiEngine.Editor;

import imgui.ImGui;
import org.PiEngine.Math.Vector;

public class VectorPropertyBlock {
    private final float[] x = {0.0f};
    private final float[] y = {0.0f};
    private final float[] z = {0.0f};

    private final String label;

    public VectorPropertyBlock(String label) {
        this.label = label;
    }

    public void set(Vector initial) {
        x[0] = initial.x;
        y[0] = initial.y;
        z[0] = initial.z;
    }

    public Vector get() {
        return new Vector(x[0], y[0], z[0]);
    }

    public void draw(String vectorLabel) {
        ImGui.text(vectorLabel);
        ImGui.sameLine();
        ImGui.pushItemWidth(80);
        ImGui.dragFloat("##" + label + "_x", x, 0.1f);
        ImGui.sameLine();
        ImGui.dragFloat("##" + label + "_y", y, 0.1f);
        ImGui.sameLine();
        ImGui.dragFloat("##" + label + "_z", z, 0.1f);
        ImGui.popItemWidth();
    }
}
