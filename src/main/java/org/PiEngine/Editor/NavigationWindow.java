package org.PiEngine.Editor;

import org.PiEngine.Engine.Scene;
import org.PiEngine.Scripting.CompileScripts;
import org.PiEngine.Scripting.ScriptLoader;

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
                Scene.getInstance().Load();   
            }
            if (ImGui.menuItem("Save", "Ctrl+S"))
            {
                Scene.getInstance().Save();
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
            if (ImGui.menuItem("Hierarchy"))
            {
                 Editor.getInstance().queueAddWindow(new HierarchyWindow());
            }
            if (ImGui.menuItem("Inspector"))
            {
                Editor.getInstance().queueAddWindow(new InspectorWindow());
            }
            if (ImGui.menuItem("Layer"))
            {
                Editor.getInstance().queueAddWindow(new LayerWindow());
            }
            if (ImGui.menuItem("Render Inspector"))
            {
                Editor.getInstance().queueAddWindow(new RendererInspector(Scene.getInstance().getGameRenderer()));
            }
            if (ImGui.menuItem("Render Graph"))
            {
                Editor.getInstance().queueAddWindow(new RenderGraphEditorWindow(Scene.getInstance().getGameRenderer()));
            }
            if (ImGui.menuItem("Scene View"))
            {
                Editor.getInstance().queueAddWindow(new SceneWindow("Scene View", Scene.getInstance().getSceneRenderer().getFinalFramebuffer()));
            }
            if (ImGui.menuItem("Game View"))
            {
                Editor.getInstance().queueAddWindow(new SceneWindow("Game View", Scene.getInstance().getGameRenderer().getFinalFramebuffer()));
            }
            if (ImGui.menuItem("Files"))
            {
                Editor.getInstance().queueAddWindow(new FileWindow());
            }
            ImGui.endMenu();
        }

        if (ImGui.beginMenu("Script"))
        {
            if (ImGui.menuItem("Compile Script"))
            {
                try 
                {
                    CompileScripts compiler = CompileScripts.getInstance("src\\main\\resources\\Scripts", "Compiled", null);
                    compiler.compileScripts();
                } 
                catch (Exception e) 
                {
                    e.printStackTrace();
                }

            }
            if (ImGui.menuItem("Load Script"))
            {
                ScriptLoader.getInstance().loadComponentScripts("Compiled/Scripts");
            }
            if (ImGui.menuItem("Compile & Load Script"))
            {
                try 
                {
                    CompileScripts compiler = CompileScripts.getInstance("src\\main\\resources\\Scripts", "Compiled", null);
                    compiler.compileScripts();
                } 
                catch (Exception e) 
                {
                    e.printStackTrace();
                }
                ScriptLoader.getInstance().loadComponentScripts("Compiled/Scripts");
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
