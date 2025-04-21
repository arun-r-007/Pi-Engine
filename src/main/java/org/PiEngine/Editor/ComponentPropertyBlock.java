package org.PiEngine.Editor;

import imgui.ImGui;
import imgui.type.ImFloat;

import org.PiEngine.Component.Component;
import org.PiEngine.GameObjects.GameObject;
import org.PiEngine.Math.Vector;

import java.lang.reflect.Field;
import java.util.function.Consumer;

/**
 * ComponentPropertyBlock is responsible for rendering UI controls
 * for editing public fields of a given Component using ImGui.
 */
public class ComponentPropertyBlock {
    private final String label;

    public ComponentPropertyBlock(String label) {
        this.label = label;
    }

    /**
     * Draws UI fields for all public attributes of a Component.
     * Skips internal fields like 'gameObject' or 'transform'.
     * Supports editing float, Vector, and GameObject references.
     */
    public void drawComponentFields(Component c, GameObject root) {
        Field[] fields = c.getClass().getFields(); // Only public fields

        for (Field field : fields) {
            String fieldName = field.getName();

            // Skip non-editable or internal references
            if (fieldName.equals("gameObject") || fieldName.equals("transform")) {
                continue;
            }

            try {
                Object value = field.get(c);

                if (value instanceof Float f) {
                    drawFloatField(fieldName, f, newVal -> {
                        try {
                            field.set(c, newVal);
                        } catch (IllegalAccessException e) {
                            ImGui.text("Cannot modify: " + fieldName);
                        }
                    });

                } else if (value instanceof Vector v) {
                    VectorPropertyBlock vecBlock = new VectorPropertyBlock(fieldName);
                    vecBlock.set(v);
                    vecBlock.draw(fieldName);

                    if (!ImGui.isAnyItemActive()) {
                        field.set(c, vecBlock.get());
                    }

                } else if (value instanceof GameObject go) {
                    ImGui.text(fieldName + ":");
                    ImGui.sameLine();
                
                    ImGui.pushID(fieldName); // Unique ID to isolate drag/drop slots
                
                    // Display current object's name or fallback label
                    String displayName = (go != null && go.Name != null) ? go.Name : "(None)";
                    ImGui.button(displayName); // Display as button-like label
                
                    // Accept Drag-and-Drop
                    if (ImGui.beginDragDropTarget()) {
                        Object payloadObj = ImGui.acceptDragDropPayload("GAME_OBJECT");
                        if (payloadObj instanceof GameObject droppedObj) {
                            field.set(c, droppedObj);
                        }
                        ImGui.endDragDropTarget();
                    }
                
                    ImGui.popID();                
                } else {
                    ImGui.text(fieldName + ": [Unsupported Type]");
                }

            } catch (IllegalAccessException e) {
                ImGui.text("Failed to access: " + fieldName);
            }
        }
    }

    /**
     * Draws an editable float input field.
     * Calls the setter once the value is no longer being edited.
     */
    private void drawFloatField(String name, float value, Consumer<Float> setter) {
        ImFloat val = new ImFloat(value);
        ImGui.pushItemWidth(100);
        ImGui.text(name + ":"); ImGui.sameLine();
        ImGui.inputFloat("##" + label + "_" + name, val);

        if (!ImGui.isItemActive()) {
            setter.accept(val.get());
        }
    }
}
