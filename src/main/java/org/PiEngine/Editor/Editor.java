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
    private static Editor instance;

    private final ImGuiImplGlfw imguiGlfw = new ImGuiImplGlfw();
    private final ImGuiImplGl3 imguiGl3 = new ImGuiImplGl3();

    private final String glslVersion = "#version 330 core";
    private long windowPtr;
    private boolean enableMultiViewport;

    private final List<EditorWindow> editorWindows = new ArrayList<>();
    private boolean initialized = false;

    private final List<EditorWindow> windowsToAdd = new ArrayList<>();
    private final List<EditorWindow> windowsToRemove = new ArrayList<>();



    private Editor() { }

    public static Editor getInstance(long windowPtr, boolean enableMultiViewport)
    {
        if (instance == null)
        {
            instance = new Editor();
            instance.windowPtr = windowPtr;
            instance.enableMultiViewport = enableMultiViewport;
        }
        return instance;
    }

    public static Editor get()
    {
        if (instance == null)
        {
            throw new IllegalStateException("Editor not initialized! Call getInstance(windowPtr, enableMultiViewport) first.");
        }
        return instance;
    }

    public void init()
    {
        if (initialized) return;

        ImGui.createContext();
        ImGuiIO io = ImGui.getIO();

        if (enableMultiViewport)
        {
            io.addConfigFlags(ImGuiConfigFlags.ViewportsEnable);
        }

        io.getFonts().addFontDefault();
        imguiGlfw.init(windowPtr, false);
        imguiGl3.init(glslVersion);
        initialized = true;
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

    public void update(float deltaTime) {
        // Add new windows
        for (EditorWindow aw : windowsToAdd) {
            addWindow(aw);
        }
        windowsToAdd.clear();
    
        // Render windows
        imguiGlfw.newFrame();
        imguiGl3.newFrame();
        ImGui.newFrame();
    
        for (EditorWindow window : editorWindows) {
            if (window.isOpen()) {
                window.onUpdate(deltaTime);
                window.onRender();
            }
        }
    
        // Remove queued windows
        for (EditorWindow window : windowsToRemove) {
            removeWindow(window);
        }
        windowsToRemove.clear();
    
        // Finalize rendering
        ImGui.render();
        imguiGl3.renderDrawData(ImGui.getDrawData());
    
        if (enableMultiViewport) {
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

    public void queueAddWindow(EditorWindow window) {
        windowsToAdd.add(window);
    }

    public void queueRemoveWindow(EditorWindow window) {
        windowsToRemove.add(window);
    }
    
    
}
