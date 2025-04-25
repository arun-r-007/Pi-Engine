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

            // Draw and detect changes
            ImGui.text(name);
            ImGui.sameLine();
            ImGui.pushItemWidth(70);
            ImGui.pushID(label); // make sure IDs are scoped correctly

            boolean edited = false;

            edited |= ImGui.dragFloat("##X", x, 0.1f);
            ImGui.sameLine();
            edited |= ImGui.dragFloat("##Y", y, 0.1f);
            ImGui.sameLine();
            edited |= ImGui.dragFloat("##Z", z, 0.1f);

            ImGui.popID();
            ImGui.popItemWidth();

            if (edited)
            {
                setter.accept(get());
            }
        }
        else
        {
            draw(); // fallback if not synced
        }
    }

    @Override
    public void draw()
    {
        ImGui.text(name);
        ImGui.sameLine();
        ImGui.pushItemWidth(70);

        ImGui.pushID(label);
        ImGui.dragFloat("##X", x, 0.1f);
        ImGui.sameLine();
        ImGui.dragFloat("##Y", y, 0.1f);
        ImGui.sameLine();
        ImGui.dragFloat("##Z", z, 0.1f);
        ImGui.popID();

        ImGui.popItemWidth();
    }
}
