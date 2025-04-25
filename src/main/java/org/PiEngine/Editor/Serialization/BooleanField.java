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
    
    public void set(Boolean initialValue)
    {
        this.value = initialValue;
    }

    public void syncWith(Supplier<Boolean> getter, Consumer<Boolean> setter) {
        this.getter = getter;
        this.setter = setter;
    }

    public void handle() {
        if (getter != null && setter != null) {
            if (!ImGui.isAnyItemActive()) value = getter.get();
            draw();
            if (!ImGui.isAnyItemActive()) setter.accept(value);
        } else {
            draw();
        }
    }

    public void draw() {
        ImGui.text(name);
        ImGui.sameLine();
        ImGui.checkbox("###" + label, value);
    }
}
