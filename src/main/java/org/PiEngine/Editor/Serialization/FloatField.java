package org.PiEngine.Editor.Serialization;

import imgui.ImGui;
import java.util.function.Consumer;
import java.util.function.Supplier;

public class FloatField extends SerializeField<Float> {
    private float value[] = {0.0f};
    private Supplier<Float> getter;
    private Consumer<Float> setter;

    public FloatField(String name, String label) {
        super(name, label);
    }

    
    public void set(Float initialValue)
    {
        this.value[0] = initialValue;  // Convert double to float
    }
    
    public void syncWith(Supplier<Float> getter, Consumer<Float> setter) {
        this.getter = getter;
        this.setter = setter;
    }

    
    public void handle() {
        if (getter != null && setter != null) {
            if (!ImGui.isItemActivated()) value[0] = getter.get();
            draw();
            if (!ImGui.isAnyItemActive()) setter.accept(value[0]);
        } else {
            draw();
        }
    }

    
    public void draw() {
        ImGui.text(name);
        ImGui.sameLine();
        ImGui.dragFloat("###" + label, value, 0.01f);
    }
}
