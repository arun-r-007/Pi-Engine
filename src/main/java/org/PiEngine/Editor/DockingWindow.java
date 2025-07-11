package org.PiEngine.Editor;

import java.util.ArrayList;
import java.util.List;

import imgui.*;
import imgui.flag.ImGuiStyleVar;
import imgui.flag.ImGuiWindowFlags;

public class DockingWindow extends EditorWindow {

    private final List<EditorWindow> dockedWindows = new ArrayList<>();

    /**
     * Constructs a new DockingWindow.
     */
    public DockingWindow() {
        super("Docker");
    }

    /**
     * Adds an EditorWindow to the docked windows list.
     * @param window The window to dock
     */
    public void addDockedWindow(EditorWindow window) {
        dockedWindows.add(window);
    }

    /**
     * Called when the window is created. Sets up size and position.
     */
    @Override
    public void onCreate() {
        ImGuiIO io = ImGui.getIO();
        float screenWidth = io.getDisplaySizeX();
        float screenHeight = io.getDisplaySizeY();

        this.setPosition(0, 0);
        this.setSize(screenWidth, screenHeight);

        for (EditorWindow window : dockedWindows) {
            window.onCreate();
        }
    }

    /**
     * Called every frame to update docked windows.
     */
    @Override
    public void onUpdate() {
        for (EditorWindow window : dockedWindows) {
            window.onUpdate();
        }
    }

    /**
     * Renders the docking window and all docked windows.
     */
    @Override
    public void onRender() {
        ImGuiIO io = ImGui.getIO();
        float screenWidth = io.getDisplaySizeX();
        float screenHeight = io.getDisplaySizeY();
    
        int windowFlags = ImGuiWindowFlags.NoTitleBar
                        | ImGuiWindowFlags.NoCollapse
                        | ImGuiWindowFlags.NoResize
                        | ImGuiWindowFlags.NoMove
                        | ImGuiWindowFlags.NoBringToFrontOnFocus
                        | ImGuiWindowFlags.MenuBar;
    
        ImGui.setNextWindowPos(0, 0);
        ImGui.setNextWindowSize(screenWidth, screenHeight);
    
        ImGui.pushStyleVar(ImGuiStyleVar.WindowRounding, 0.0f);
        ImGui.pushStyleVar(ImGuiStyleVar.WindowBorderSize, 0.0f);
    
        ImGui.begin(name, windowFlags);
    
        ImGui.popStyleVar(2);
    
        int dockspaceId = ImGui.getID("MyDockspace");
        ImGui.dockSpace(dockspaceId);
    
        ImGui.end();
    }


    @Override
    public void onDestroy() {
        for (EditorWindow window : dockedWindows) {
            window.onDestroy();
        }
    }
}
