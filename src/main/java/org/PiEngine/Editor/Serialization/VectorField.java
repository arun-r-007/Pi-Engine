package org.PiEngine.Editor.Serialization;

import imgui.ImGui;
import org.PiEngine.Math.Vector;

import java.util.function.Consumer;
import java.util.function.Supplier;

public class VectorField extends SerializeField<Vector>
{
    private final float[] x = {0.0f};
    private final float[] y = {0.0f};
    private final float[] z = {0.0f};

    private Supplier<Vector> getter;
    private Consumer<Vector> setter;

    public VectorField(String name, String label)
    {
        super(name, label);
    }

    public void set(Vector initial)
    {
        x[0] = initial.x;
        y[0] = initial.y;
        z[0] = initial.z;
    }

    public Vector get()
    {
        return new Vector(x[0], y[0], z[0]);
    }

    public void syncWith(Supplier<Vector> getter, Consumer<Vector> setter)
    {
        this.getter = getter;
        this.setter = setter;
    }

    public void handle()
    {
        if (getter != null && setter != null)
        {
            if (!ImGui.isAnyItemActive())
            {
                set(getter.get());
            }

            draw();

            if (!ImGui.isAnyItemActive())
            {
                setter.accept(get());
            }
        }
        else
        {
            draw();
        }
    }

    @Override
    public void draw()
    {
        ImGui.text(name);
        ImGui.sameLine();
        ImGui.pushItemWidth(70);
        ImGui.dragFloat("##" + label + "_x", x, 0.01f);
        ImGui.sameLine();
        ImGui.dragFloat("##" + label + "_y", y, 0.01f);
        ImGui.sameLine();
        ImGui.dragFloat("##" + label + "_z", z, 0.01f);
        ImGui.popItemWidth();
    }
}
