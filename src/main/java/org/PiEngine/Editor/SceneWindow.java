package org.PiEngine.Editor;


import imgui.ImGui;
import imgui.ImVec2;

public class SceneWindow extends EditorWindow
{

    int outputTex;

    public SceneWindow(String name)
    {
        super(name);
    }

    public void setid(int o)
    {
        outputTex = o;
    }

    @Override
    public void onRender()
    {
        ImGui.begin(name);

        ImGui.image(
        outputTex,                                 
        new ImVec2(1280/2, 720/2),                          
        new ImVec2(0, 1), new ImVec2(1, 0)                 
        );

        ImGui.end();
    }

}
