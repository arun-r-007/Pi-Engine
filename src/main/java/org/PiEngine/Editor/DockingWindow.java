package org.PiEngine.Editor;

import java.util.ArrayList;
import java.util.List;

import imgui.*;
import imgui.flag.ImGuiStyleVar;
import imgui.flag.ImGuiWindowFlags;

public class DockingWindow extends EditorWindow {

    private final List<EditorWindow> dockedWindows = new ArrayList<>();

    public DockingWindow() {
        super("Docker");
    }

    public void addDockedWindow(EditorWindow window) {
        dockedWindows.add(window);
    }

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

    @Override
    public void onUpdate(float deltaTime) {
        for (EditorWindow window : dockedWindows) {
            window.onUpdate(deltaTime);
        }
    }

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
