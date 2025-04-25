package org.PiEngine.Editor.Serialization;

import imgui.ImGui;
import imgui.type.ImString;

import java.util.function.Consumer;
import java.util.function.Supplier;

public class StringField extends SerializeField<String> {
    private String value = "";
    private Supplier<String> getter;
    private Consumer<String> setter;

    public StringField(String name, String label) {
        super(name, label);
    }

    public void set(String initialValue)
    {
        this.value = initialValue;
    }
    
    public void syncWith(Supplier<String> getter, Consumer<String> setter) {
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
        ImGui.inputText("###" + label, new ImString(value));
    }
}
