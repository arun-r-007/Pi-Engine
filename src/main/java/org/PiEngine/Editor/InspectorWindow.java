package org.PiEngine.Editor;

import imgui.ImGui;
import org.PiEngine.GameObjects.*;
import 

public class InspectorWindow extends EditorWindow
{
    public static GameObject inspectObject = null;
    public GameObject propertyObject = null;

    public boolean actAsProperty = false;

    public InspectorWindow(boolean isproperty)
    {
        super(!isproperty? "Inspector": "Property");
        actAsProperty = isproperty;
    }

    @Override
    public void onRender()
    {
        GameObject current = actAsProperty ? propertyObject : inspectObject;
        ImGui.begin(name); 
        
        //ImGui.begin( actAsProperty ? "Property" : "Inspector");
        
        if (current == null)
        {
            ImGui.text("No GameObject selected.");
            ImGui.end();
            return;
        }
        
        
        ImGui.text("Inspecting: " + current.Name);
        ImGui.separator();
        
        if (ImGui.collapsingHeader("Transform"))
        {
            renderComponentEditor(current);
        }
        
        
        ImGui.end();
    }

    private void renderTransformEditor(GameObject obj)
    {
        ImGui.text("All Transform Properties of " + obj.Name);
        ImGui.separator();

        ImGui.text("GLOBAL");
        ImGui.separator();

        VectorPropertyBlock worldPosBlock = new VectorPropertyBlock("worldPos");
        worldPosBlock.set(obj.transform.getWorldPosition());
        worldPosBlock.draw("Position  ");
        if (!ImGui.isAnyItemActive())
        {
            obj.transform.setWorldPosition(worldPosBlock.get());
        }

        VectorPropertyBlock worldRotBlock = new VectorPropertyBlock("worldRot");
        worldRotBlock.set(obj.transform.getWorldRotation());
        worldRotBlock.draw("Rotation  ");
        if (!ImGui.isAnyItemActive())
        {
            obj.transform.setWorldRotation(worldRotBlock.get());
        }

        VectorPropertyBlock worldScaleBlock = new VectorPropertyBlock("worldScale");
        worldScaleBlock.set(obj.transform.getWorldScale());
        worldScaleBlock.draw("Size      ");
        if (!ImGui.isAnyItemActive())
        {
            obj.transform.setWorldScale(worldScaleBlock.get());
        }

        ImGui.separator();

        ImGui.text("LOCAL");
        ImGui.separator();

        VectorPropertyBlock localPosBlock = new VectorPropertyBlock("localPos");
        localPosBlock.set(obj.transform.getLocalPosition());
        localPosBlock.draw("Position  ");
        if (!ImGui.isAnyItemActive())
        {
            obj.transform.setLocalPosition(localPosBlock.get());
        }

        VectorPropertyBlock localRotBlock = new VectorPropertyBlock("localRot");
        localRotBlock.set(obj.transform.getLocalRotation());
        localRotBlock.draw("Rotation  ");
        if (!ImGui.isAnyItemActive())
        {
            obj.transform.setLocalRotation(localRotBlock.get());
        }

        VectorPropertyBlock localScaleBlock = new VectorPropertyBlock("localScale");
        localScaleBlock.set(obj.transform.getLocalScale());
        localScaleBlock.draw("Size      ");
        if (!ImGui.isAnyItemActive())
        {
            obj.transform.setLocalScale(localScaleBlock.get());
        }

        ImGui.separator();
    }

    private void renderComponentEditor(GameObject obj) {
        ImGui.separator();
        ImGui.spacing();                          // Add some vertical space
        ImGui.textColored(1.0f, 1.0f, 0.4f, 1.0f, "Component Properties"); // Colored bold-like title
        ImGui.spacing(); 
    
        List<Component> components = obj.getAllComponents();
        for (Component c : components) {
            String compName = c.getClass().getSimpleName();
            if (ImGui.collapsingHeader(compName)) {
                // Create a block for each component to draw its fields
                ComponentPropertyBlock comp = new ComponentPropertyBlock(compName);
                comp.drawComponentFields(c);
            }
        }
    }
}
