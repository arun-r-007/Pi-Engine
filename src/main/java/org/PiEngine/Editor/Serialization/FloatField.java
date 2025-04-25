package org.PiEngine.Editor.Serialization;

import imgui.ImGui;
import java.util.function.Consumer;
import java.util.function.Supplier;

public class FloatField extends SerializeField<Float> {
    private final float[] value = {0.0f};
    private Supplier<Float> getter;
    private Consumer<Float> setter;

    public FloatField(String name, String label) {
        super(name, label);
    }

    public void set(Float initialValue) {
        this.value[0] = initialValue;
    }

    public void syncWith(Supplier<Float> getter, Consumer<Float> setter) {
        this.getter = getter;
        this.setter = setter;
    }

    public void handle() {
        if (getter != null && setter != null) {
            if (!ImGui.isAnyItemActive()) {
                set(getter.get());
            }

            ImGui.text(name);
            ImGui.sameLine();

            ImGui.pushID(label);
            boolean edited = ImGui.dragFloat("###input", value, 0.1f);
            ImGui.popID();

            if (edited) {
                setter.accept(value[0]);
            }
        } else {
            draw();
        }
    }

    @Override
    public void draw() {
        ImGui.text(name);
        ImGui.sameLine();

        ImGui.pushID(label);
        ImGui.dragFloat("###input", value, 0.1f);
        ImGui.popID();
    }
}
