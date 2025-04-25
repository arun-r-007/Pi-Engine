package org.PiEngine.Editor.Serialization;

import imgui.ImGui;
import java.util.function.Consumer;
import java.util.function.Supplier;

public class IntField extends SerializeField<Integer> {
    private int value[] = {0};
    private Supplier<Integer> getter;
    private Consumer<Integer> setter;

    public IntField(String name, String label) {
        super(name, label);
    }
    
    public void set(Integer initialValue)
    {
        this.value[0] = initialValue;
    }

    public void syncWith(Supplier<Integer> getter, Consumer<Integer> setter) {
        this.getter = getter;
        this.setter = setter;
    }
    

    public void handle() {
        if (getter != null && setter != null) {
            if (!ImGui.isAnyItemActive()) value[0] = getter.get();
            draw();
            if (!ImGui.isAnyItemActive()) setter.accept(value[0]);
        } else {
            draw();
        }
    }

    public void draw() {
        ImGui.text(name);
        ImGui.sameLine();
        ImGui.dragInt("###" + label, value);
    }
}
