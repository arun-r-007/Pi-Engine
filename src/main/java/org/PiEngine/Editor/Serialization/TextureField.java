package org.PiEngine.Editor.Serialization;

import imgui.ImGui;
import imgui.flag.ImGuiInputTextFlags;
import imgui.type.ImString;

import java.util.function.Consumer;
import java.util.function.Supplier;

import org.PiEngine.Render.Texture;

public class TextureField extends SerializeField<Texture>
{
    private Texture value;
    private Supplier<Texture> getter;
    private Consumer<Texture> setter;

    public TextureField(String name, String label) {
        super(name, label);
    }

    public void set(Texture initialValue) {
        this.value = initialValue;
    }

    public void syncWith(Supplier<Texture> getter, Consumer<Texture> setter) {
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

    @Override
    public void draw() {
        
        String displayName = (value != null) ? "Texture ID: " + value.getTextureID() : "NULL";
        ImGui.inputText("Texture ID", new ImString(displayName), ImGuiInputTextFlags.ReadOnly);
        
        if (value != null) {
            ImGui.image(value.getTextureID(), 64, 64); // Show texture preview
        }
        // ImGui.sameLine();

        
        if (ImGui.beginDragDropTarget()) {
            Object payloadObj = ImGui.acceptDragDropPayload("TEXTURE");
            if (payloadObj instanceof Texture droppedTex) {
                value = droppedTex;
                if (setter != null) {
                    setter.accept(value);
                }
            }
            ImGui.endDragDropTarget();
        }

    }
}
