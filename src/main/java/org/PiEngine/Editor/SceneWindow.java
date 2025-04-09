package org.PiEngine.Editor;


import imgui.ImGui;
import imgui.ImVec2;

public class SceneWindow extends EditorWindow
{

    int outputTex;

    public SceneWindow()
    {
        super("Perfomance");
    }

    public void setid(int o)
    {
        outputTex = o;
    }

    @Override
    public void onRender()
    {
        ImGui.begin("Viewport");

        ImGui.image(
        outputTex,                                 // texture ID
        new ImVec2(1280/2, 720/2),                          // size (adjust to your framebuffer size or resize dynamically)
        new ImVec2(0, 1), new ImVec2(1, 0)                 // UV coords: flip vertically because OpenGL and ImGui have different Y origin
        );

        ImGui.end();
    }

}
