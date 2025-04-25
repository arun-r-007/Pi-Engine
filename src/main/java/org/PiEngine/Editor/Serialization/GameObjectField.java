package org.PiEngine.Editor.Serialization;

import imgui.ImGui;
import imgui.flag.*;
import imgui.type.ImString;

import org.PiEngine.GameObjects.GameObject;

import java.lang.reflect.Field;
import java.util.function.Consumer;
import java.util.function.Supplier;

public class GameObjectField extends SerializeField<GameObject> {
    private GameObject value;
    private Supplier<GameObject> getter;
    private Consumer<GameObject> setter;
    
    public GameObjectField(String name, String label) {
        super(name, label);
    }
    
    public void set(GameObject initialValue) {
        this.value = initialValue;
    }

    private Field field = null;
        public void set(Field initialValue) {
        this.field = initialValue;
        System.out.println(field);
    }

    public void syncWith(Supplier<GameObject> getter, Consumer<GameObject> setter) {
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
        ImGui.text(label);
        ImGui.sameLine();

        // Display the GameObject's name or "NULL" if not set
        String displayName = (value != null && value.Name != null) ? value.Name : "NULL";
        ImGui.inputText("", new ImString(displayName), ImGuiInputTextFlags.ReadOnly); 

        
        // Drag and Drop to set the GameObject
        if (ImGui.beginDragDropTarget()) {
            Object payloadObj = ImGui.acceptDragDropPayload("GAME_OBJECT");
            if (payloadObj instanceof GameObject droppedObj) {
                value = droppedObj;
                if(setter != null) setter.accept(value);
            }
            ImGui.endDragDropTarget();
        }

        // Add a "Set to NULL" button next to the text box
        if (value != null) {
            ImGui.sameLine();
            if(ImGui.button("NULL"))
            {
                value = null; 
            }
        }

    }

    public GameObject get() {
        return value;
    }
}
