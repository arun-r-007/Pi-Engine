package org.PiEngine.Editor;

import imgui.ImGui;
import imgui.ImGuiIO;
import imgui.flag.ImGuiConfigFlags;
import imgui.gl3.ImGuiImplGl3;
import imgui.glfw.ImGuiImplGlfw;
import org.lwjgl.glfw.GLFW;

import java.util.ArrayList;
import java.util.List;

public class Editor
{
    private final ImGuiImplGlfw imguiGlfw = new ImGuiImplGlfw();
    private final ImGuiImplGl3 imguiGl3 = new ImGuiImplGl3();

    private final String glslVersion = "#version 330 core";
    private final long windowPtr;

    private final List<EditorWindow> editorWindows = new ArrayList<>();
    private final boolean enableMultiViewport;


    public Editor(long windowPtr, boolean enableMultiViewport)
    {
        this.windowPtr = windowPtr;
        this.enableMultiViewport = enableMultiViewport;
    }


    public void init()
    {
        ImGui.createContext();
        ImGuiIO io = ImGui.getIO();

        if (enableMultiViewport)
        {
            io.addConfigFlags(ImGuiConfigFlags.ViewportsEnable);
        }

        io.getFonts().addFontDefault();
        imguiGlfw.init(windowPtr, false);
        imguiGl3.init(glslVersion);
    }


    public void destroy()
    {
        for (EditorWindow window : editorWindows)
        {
            window.onDestroy();
        }

        ImGui.destroyContext();
        GLFW.glfwTerminate();
    }

    public void update(float deltaTime)
    {

        imguiGlfw.newFrame();
        imguiGl3.newFrame();
        ImGui.newFrame();

        for (EditorWindow window : editorWindows)
        {
            if (window.isOpen())
            {
                window.onUpdate(deltaTime);
                window.onRender();
            }
        }

        ImGui.render();
        imguiGl3.renderDrawData(ImGui.getDrawData());

        // if (ImGui.getIO().hasConfigFlags(ImGuiConfigFlags.ViewportsEnable))
        // {
        //     final long backupWindowPtr = GLFW.glfwGetCurrentContext();
        //     ImGui.updatePlatformWindows();
        //     ImGui.renderPlatformWindowsDefault();
        //     GLFW.glfwMakeContextCurrent(backupWindowPtr);
        // }

        if (enableMultiViewport)
        {
            final long backupWindowPtr = GLFW.glfwGetCurrentContext();
            ImGui.updatePlatformWindows();
            ImGui.renderPlatformWindowsDefault();
            GLFW.glfwMakeContextCurrent(backupWindowPtr);
        }

    }

    public void addWindow(EditorWindow window)
    {
        editorWindows.add(window);
    }

    public void removeWindow(EditorWindow window)
    {
        editorWindows.remove(window);
    }
}
