package org.PiEngine.Editor;

import imgui.ImGui;
import imgui.ImGuiIO;
//import imgui.ImGuiColor;
// import imgui.ImGuiStyle;
// import imgui.ImVec4;
import imgui.flag.ImGuiCol;
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

    public static Editor getInstance()
    {
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

    public void init() {
        if (initialized) return;
    
        ImGui.createContext();
        // setTheme();
        
        ImGuiIO io = ImGui.getIO();
     
        if (enableMultiViewport) {
            io.addConfigFlags(ImGuiConfigFlags.ViewportsEnable);
        }
    
        
        io.getFonts().addFontDefault();
        imguiGlfw.init(windowPtr, false);

        imguiGl3.init(glslVersion);    
        initialized = true;
    }

    public static void setTheme() {

        ImGui.pushStyleColor(ImGuiCol.WindowBg, ImGui.colorConvertFloat4ToU32(0.12f, 0.12f, 0.12f, 1.00f));
    
        // Push other UI colors
        ImGui.pushStyleColor(ImGuiCol.Text, ImGui.colorConvertFloat4ToU32(1.00f, 1.00f, 1.00f, 1.00f)); // Text color
        ImGui.pushStyleColor(ImGuiCol.ChildBg, ImGui.colorConvertFloat4ToU32(0.10f, 0.10f, 0.10f, 1.00f)); // Child window background
        ImGui.pushStyleColor(ImGuiCol.PopupBg, ImGui.colorConvertFloat4ToU32(0.12f, 0.12f, 0.12f, 1.00f)); // Popup background
        ImGui.pushStyleColor(ImGuiCol.Border, ImGui.colorConvertFloat4ToU32(0.20f, 0.20f, 0.20f, 0.70f)); // Border color
        ImGui.pushStyleColor(ImGuiCol.FrameBg, ImGui.colorConvertFloat4ToU32(0.25f, 0.25f, 0.25f, 1.00f)); // Frame background
        ImGui.pushStyleColor(ImGuiCol.Button, ImGui.colorConvertFloat4ToU32(0.30f, 0.50f, 0.80f, 1.00f)); // Button color (blue)
        ImGui.pushStyleColor(ImGuiCol.ButtonHovered, ImGui.colorConvertFloat4ToU32(0.40f, 0.60f, 1.00f, 1.00f)); // Hovered button (lighter blue)
        ImGui.pushStyleColor(ImGuiCol.ButtonActive, ImGui.colorConvertFloat4ToU32(0.20f, 0.40f, 0.80f, 1.00f)); // Active button (darker blue)
        ImGui.pushStyleColor(ImGuiCol.Header, ImGui.colorConvertFloat4ToU32(0.80f, 0.30f, 0.30f, 0.80f)); // Header (light red)
        ImGui.pushStyleColor(ImGuiCol.HeaderHovered, ImGui.colorConvertFloat4ToU32(0.90f, 0.40f, 0.40f, 1.00f)); // Hovered header (bright red)
        ImGui.pushStyleColor(ImGuiCol.HeaderActive, ImGui.colorConvertFloat4ToU32(0.70f, 0.20f, 0.20f, 1.00f)); // Active header (dark red)
        ImGui.pushStyleColor(ImGuiCol.Tab, ImGui.colorConvertFloat4ToU32(0.10f, 0.10f, 0.10f, 1.00f)); // Tab background
        ImGui.pushStyleColor(ImGuiCol.TabHovered, ImGui.colorConvertFloat4ToU32(0.30f, 0.30f, 0.50f, 1.00f)); // Hovered tab (purple)
        ImGui.pushStyleColor(ImGuiCol.TabActive, ImGui.colorConvertFloat4ToU32(0.40f, 0.40f, 0.70f, 1.00f)); // Active tab (blue)
        ImGui.pushStyleColor(ImGuiCol.TextSelectedBg, ImGui.colorConvertFloat4ToU32(0.20f, 0.50f, 0.30f, 0.70f)); // Selected text background (green)
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
    }

    public void addWindow(EditorWindow window)
    {
        editorWindows.add(window);
    }

    public void removeWindow(EditorWindow window)
    {
        editorWindows.remove(window);
    }

    public void queueAddWindow(EditorWindow window) 
    {
        windowsToAdd.add(window);
    }

    public void queueRemoveWindow(EditorWindow window) 
    {
        windowsToRemove.add(window);
    }
    
    
}
