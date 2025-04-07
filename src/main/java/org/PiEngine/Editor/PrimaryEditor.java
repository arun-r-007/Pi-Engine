package org.PiEngine.Editor;

import imgui.*;
import org.PiEngine.GameObjects.GameObject;
import org.PiEngine.GameObjects.Transform;
import org.PiEngine.Math.*;
import imgui.type.ImFloat;
import java.util.List;
import java.util.HashMap;
import java.util.Map;

public class PrimaryEditor {
    private ImFloat x = new ImFloat(0);  // Position X
    private ImFloat y = new ImFloat(0);  // Position Y
    private ImFloat z = new ImFloat(0);  // Position Z

    private Map<GameObject, Boolean> buttonPressedMap = new HashMap<>();  // Button press state
    private Map<GameObject, Boolean> dropdownStateMap = new HashMap<>();  // Dropdown state
    private Map<GameObject, Boolean> subPropertiesStateMap = new HashMap<>();  // Sub-properties state

    /**
     * Renders the main editor window and manages game object input UI.
     */
    public void imgui(GameObject world) {
        ImGui.begin("Game Object Input");

        if (ImGui.button("SCENE OBJECTS")) {
            toggleVisibility(world);  // Toggle visibility
        }

        printGameObjectAndChildren(world, 0);  // Print children

        ImGui.end();
    }

    /**
     * Toggles visibility and state of the game object dropdown.
     */
    private void toggleVisibility(GameObject obj) {
        boolean currentState = !buttonPressedMap.getOrDefault(obj, false);
        buttonPressedMap.put(obj, currentState);
        dropdownStateMap.put(obj, currentState);
        subPropertiesStateMap.put(obj, false);  // Reset sub-properties
    }

    /**
     * Toggles the visibility of sub-properties.
     */
    private void toggleSubPropertiesVisibility(GameObject obj) {
        subPropertiesStateMap.put(obj, !subPropertiesStateMap.getOrDefault(obj, false));
    }

    /**
     * Recursively renders the game object and its children in the editor.
     */
    private void printGameObjectAndChildren(GameObject obj, int depth) {
        String indent = " ".repeat(depth * 2);

        Boolean isDropdownOpen = dropdownStateMap.getOrDefault(obj, false);
        Boolean areSubPropertiesVisible = subPropertiesStateMap.getOrDefault(obj, false);

        if (ImGui.button(indent + obj.Name)) {
            toggleVisibility(obj);  // Toggle dropdown visibility
        }

        if (isDropdownOpen) {
            ImGui.beginChild("##" + obj.Name + "Dropdown", new ImVec2(0, 100), true);  // Dropdown child
            {
                ImGui.text("Details of " + obj.Name);  // Display name

                // Add buttons for each transform property
                if (ImGui.button("World Position: " + obj.transform.getWorldPosition())) {
                    ChangePositionInput(obj, true);  // Show world position input
                }

                if (ImGui.button("Local Position: " + obj.transform.getLocalPosition())) {
                    ChangePositionInput(obj, false);  // Show local position input
                }

                if (ImGui.button("Local Rotation: " + obj.transform.getLocalRotation())) {
                    ChangeRotationInput(obj);  // Show rotation input
                }

                if (ImGui.button("Local Scale: " + obj.transform.getLocalScale())) {
                    ChangeScaleInput(obj);  // Show scale input
                }

                if (ImGui.button("Change Propertiers")) {
                    toggleSubPropertiesVisibility(obj);  // Toggle sub-properties dropdown
                }

                if (areSubPropertiesVisible) {
                    ImGui.beginChild("##" + obj.Name + "SubProperties", new ImVec2(0, 300), false);  // Sub-properties child
                    {
                        ImGui.text("All Transform Properties of " + obj.Name);

                        // World Position Inputs
                        ImGui.pushItemWidth(100);
                        ImGui.inputFloat("World Position X", x);
                        ImGui.sameLine();
                        ImGui.inputFloat("World Position Y", y);
                        ImGui.sameLine();
                        ImGui.inputFloat("World Position Z", z);
                        ImGui.sameLine();
                        if (ImGui.button("Set World Position")) {
                            obj.transform.setWorldPosition(new Vector(x.get(), y.get(), z.get()));
                        }
                        ImGui.popItemWidth();  // Reset width

                        ImGui.separator();  // Separator

                        // Local Position Inputs
                        ImGui.pushItemWidth(100);
                        ImGui.inputFloat("Local Position X", x);
                        ImGui.sameLine();
                        ImGui.inputFloat("Local Position Y", y);
                        ImGui.sameLine();
                        ImGui.inputFloat("Local Position Z", z);
                        ImGui.sameLine();
                        if (ImGui.button("Set Local Position")) {
                            obj.transform.setLocalPosition(new Vector(x.get(), y.get(), z.get()));
                        }
                        ImGui.popItemWidth();  // Reset width

                        ImGui.separator();  // Separator

                        // Local Rotation Inputs
                        ImGui.pushItemWidth(100);
                        ImGui.inputFloat("Local Rotation X", x);
                        ImGui.sameLine();
                        ImGui.inputFloat("Local Rotation Y", y);
                        ImGui.sameLine();
                        ImGui.inputFloat("Local Rotation Z", z);
                        ImGui.sameLine();
                        if (ImGui.button("Set Local Rotation")) {
                            obj.transform.setLocalRotation(new Vector(x.get(), y.get(), z.get()));
                        }
                        ImGui.popItemWidth();  // Reset width

                        ImGui.separator();  // Separator

                        // Local Scale Inputs
                        ImGui.pushItemWidth(100);
                        ImGui.inputFloat("Local Scale X", x);
                        ImGui.sameLine();
                        ImGui.inputFloat("Local Scale Y", y);
                        ImGui.sameLine();
                        ImGui.inputFloat("Local Scale Z", z);
                        ImGui.sameLine();
                        if (ImGui.button("Set Local Scale")) {
                            obj.transform.setLocalScale(new Vector(x.get(), y.get(), z.get()));
                        }
                        ImGui.popItemWidth();  // Reset width
                    }
                    ImGui.endChild();
                }

            }
            ImGui.endChild();
        }

        List<Transform> children = obj.transform.getChildren();
        for (int i = 0; i < children.size(); i++) {
            GameObject childObj = children.get(i).getGameObject();
            printGameObjectAndChildren(childObj, depth + 1);  // Recursive call
        }
    }

    /**
     * Displays position input fields (X, Y, Z) for either world or local position.
     */
    private void ChangePositionInput(GameObject obj, boolean isWorldPosition) {
        boolean isModified = false;

        if (isWorldPosition) {
            ImGui.inputFloat("World Position X", x);
            ImGui.inputFloat("World Position Y", y);
            ImGui.inputFloat("World Position Z", z);

            if (ImGui.button("Set World Position")) {
                obj.transform.setWorldPosition(new Vector(x.get(), y.get(), z.get()));
                isModified = true;
            }
        } else {
            ImGui.inputFloat("Local Position X", x);
            ImGui.inputFloat("Local Position Y", y);
            ImGui.inputFloat("Local Position Z", z);

            if (ImGui.button("Set Local Position")) {
                obj.transform.setLocalPosition(new Vector(x.get(), y.get(), z.get()));
                isModified = true;
            }
        }

        if (isModified) {
            dropdownStateMap.put(obj, false);  // Close dropdown
        }
    }

    /**
     * Displays rotation input fields (X, Y, Z) for local rotation.
     */
    private void ChangeRotationInput(GameObject obj) {
        boolean isModified = false;

        ImGui.inputFloat("Local Rotation X", x);
        ImGui.inputFloat("Local Rotation Y", y);
        ImGui.inputFloat("Local Rotation Z", z);

        if (ImGui.button("Set Local Rotation")) {
            obj.transform.setLocalRotation(new Vector(x.get(), y.get(), z.get()));
            isModified = true;
        }

        if (isModified) {
            dropdownStateMap.put(obj, false);  // Close dropdown
        }
    }

    /**
     * Displays scale input fields (X, Y, Z) for local scale.
     */
    private void ChangeScaleInput(GameObject obj) {
        boolean isModified = false;

        ImGui.inputFloat("Local Scale X", x);
        ImGui.inputFloat("Local Scale Y", y);
        ImGui.inputFloat("Local Scale Z", z);

        if (ImGui.button("Set Local Scale")) {
            obj.transform.setLocalScale(new Vector(x.get(), y.get(), z.get()));
            isModified = true;
        }

        if (isModified) {
            dropdownStateMap.put(obj, false);  // Close dropdown
        }
    }
}
