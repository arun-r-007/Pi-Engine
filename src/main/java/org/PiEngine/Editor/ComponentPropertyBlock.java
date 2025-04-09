package org.PiEngine.Editor;

import imgui.ImGui;
import imgui.type.ImFloat;
import org.PiEngine.Component.Component;
import org.PiEngine.GameObjects.GameObject;
import org.PiEngine.Math.Vector;

import java.lang.reflect.Field;
import java.util.function.Consumer;

public class ComponentPropertyBlock {
    private final String label;

    public ComponentPropertyBlock(String label) {
        this.label = label;
    }

    public void drawComponentFields(Component c) {
        Field[] fields = c.getClass().getFields();

        for (Field field : fields) {
            String fieldName = field.getName();
            try {
                Object value = field.get(c);
                if (value instanceof Float f) {
                    inputFloat(fieldName, f, val -> {
                        try {
                            field.set(c, val);
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
                    ImGui.text(fieldName + ": " + (go != null ? go.Name : "null"));
                } else {
                    ImGui.text(fieldName + ": [Unsupported Type]");
                }
            } catch (IllegalAccessException e) {
                ImGui.text("Failed to access: " + fieldName);
            }
        }
    }

    private void inputFloat(String name, float value, Consumer<Float> setter) {
        ImFloat val = new ImFloat(value);
        ImGui.pushItemWidth(80);
        ImGui.text(name + ":"); ImGui.sameLine();
        ImGui.inputFloat("##" + label + "_" + name, val);

        if (!ImGui.isItemActive()) {
            setter.accept(val.get());
        }
    }
}
