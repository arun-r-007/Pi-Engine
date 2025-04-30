package org.PiEngine.Editor.Serialization;

import imgui.ImGui;
import java.util.function.Consumer;
import java.util.function.Supplier;

public class BooleanField extends SerializeField<Boolean> {
    private boolean value;
    private Supplier<Boolean> getter;
    private Consumer<Boolean> setter;

    public BooleanField(String name, String label) {
        super(name, label);
    }

    public void set(Boolean initialValue) {
        this.value = initialValue;
    }

    public void syncWith(Supplier<Boolean> getter, Consumer<Boolean> setter) {
        this.getter = getter;
        this.setter = setter;
    }

    public void handle() {
        if (getter != null && setter != null) {
            if (!ImGui.isAnyItemActive()) {
                value = getter.get();
            }

            boolean temp = value ;
            // ImGui.text(name);
            // ImGui.sameLine();

            ImGui.pushID(label);
            boolean changed = ImGui.checkbox(name, temp);

            ImGui.popID();

            if (changed) {
                value = temp;
                setter.accept(value);
            }
        } else {
            draw();
        }
    }

    @Override
    public void draw() {
        // ImGui.text(name);
        // ImGui.sameLine();

        boolean temp = value;
        ImGui.pushID(label);
        ImGui.checkbox(name, temp);
        ImGui.popID();
    }
}
