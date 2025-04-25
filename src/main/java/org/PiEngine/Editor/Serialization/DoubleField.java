package org.PiEngine.Editor.Serialization;

import imgui.ImGui;
import java.util.function.Consumer;
import java.util.function.Supplier;

public class DoubleField extends SerializeField<Double> {
    private float[] value = {0.0f};  // Use a float array for compatibility with ImGui
    private Supplier<Double> getter;
    private Consumer<Double> setter;

    public DoubleField(String name, String label) {
        super(name, label);
    }
    
    public void set(Double initialValue)
    {
        this.value[0] = initialValue.floatValue();  // Convert double to float
    }

    public void syncWith(Supplier<Double> getter, Consumer<Double> setter) {
        this.getter = getter;
        this.setter = setter;
    }

    public void handle() {
        if (getter != null && setter != null) {
            if (!ImGui.isAnyItemActive()) {
                value[0] = getter.get().floatValue();  // Convert Double to float explicitly
            }
            draw();
            if (!ImGui.isAnyItemActive()) {
                setter.accept((double) value[0]);  // Convert float back to Double explicitly
            }
        } else {
            draw();
        }
    }

    public void draw() {
        ImGui.text(name);
        ImGui.sameLine();
        ImGui.dragFloat("###" + label, value, 0.01f);  // Pass the float[] value
    }
}
