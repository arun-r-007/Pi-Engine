package org.PiEngine.Editor;

import imgui.ImGui;

public class NavigationWindow extends EditorWindow
{

    public NavigationWindow()
    {
        super("Navigation");
    }

    @Override
    public void onRender()
    {


        // Files dropdown
        ImGui.beginMainMenuBar();
        if (ImGui.beginMenu("File"))
        {
            if (ImGui.menuItem("New", "Ctrl+N"))
            {
                
            }
            if (ImGui.menuItem("Open", "Ctrl+O"))
            {
                // Handle open action
            }
            if (ImGui.menuItem("Save", "Ctrl+S"))
            {
                // Handle save action
            }
            if (ImGui.menuItem("Exit", "Ctrl+Esc"))
            {
                // Handle exit action
            }
            ImGui.endMenu();
        }

        // Edit dropdown
        if (ImGui.beginMenu("Edit"))
        {
            if (ImGui.menuItem("Undo"))
            {
                // Handle undo action
            }
            if (ImGui.menuItem("Redo"))
            {
                // Handle redo action
            }
            if (ImGui.menuItem("Copy"))
            {
                // Handle copy action
            }
            if (ImGui.menuItem("Paste"))
            {
                // Handle paste action
            }
            ImGui.endMenu();
        }

        // Window dropdown
        if (ImGui.beginMenu("Window"))
        {
            if (ImGui.menuItem("Console"))
            {
                Editor.getInstance().queueAddWindow(new ConsoleWindow());
            }
            if (ImGui.menuItem("Performance"))
            {
                Editor.getInstance().queueAddWindow(new PerfomanceWindow());
            }
            // if (ImGui.menuItem("Hierarchy"))
            // {
            //     Editor.getInstance().queueAddWindow(new HierarchyWindow());
            // }
            if (ImGui.menuItem("Inspector"))
            {
                Editor.getInstance().queueAddWindow(new InspectorWindow());
            }
            if (ImGui.menuItem("Layer"))
            {
                Editor.getInstance().queueAddWindow(new LayerWindow());
            }
            ImGui.endMenu();
        }

        // Help dropdown
        if (ImGui.beginMenu("Help"))
        {
            if (ImGui.menuItem("Documentation"))
            {
                // Handle documentation action
            }
            if (ImGui.menuItem("About"))
            {
                // Handle about action
            }
            ImGui.endMenu();
        }
        ImGui.endMainMenuBar();
        
    }
}
