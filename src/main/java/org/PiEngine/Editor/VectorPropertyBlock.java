package org.PiEngine.Editor;

import imgui.ImGui;
import imgui.type.ImFloat;
import org.PiEngine.Math.Vector;

public class VectorPropertyBlock {
    private final ImFloat x = new ImFloat();
    private final ImFloat y = new ImFloat();
    private final ImFloat z = new ImFloat();

    private final String label;

    public VectorPropertyBlock(String label) {
        this.label = label;
    }

    public void set(Vector initial) {
        x.set(initial.x);
        y.set(initial.y);
        z.set(initial.z);
    }

    public Vector get() {
        return new Vector(x.get(), y.get(), z.get());
    }

    public void draw(String vectorLabel) {
        ImGui.text(vectorLabel);
        ImGui.sameLine();
        ImGui.pushItemWidth(80);
        ImGui.inputFloat("##" + label + "_x", x);
        ImGui.sameLine();
        ImGui.inputFloat("##" + label + "_y", y);
        ImGui.sameLine();
        ImGui.inputFloat("##" + label + "_z", z);
        ImGui.popItemWidth();
    }
}
