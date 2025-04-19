package org.PiEngine.Editor;

import java.util.ArrayList;
import java.util.List;

import imgui.*;
import imgui.flag.ImGuiStyleVar;
import imgui.flag.ImGuiWindowFlags;

public class DockingWindow extends EditorWindow {

    private final List<EditorWindow> dockedWindows = new ArrayList<>();

    public DockingWindow(String name) {
        super(name);
    }

    public void addDockedWindow(EditorWindow window) {
        dockedWindows.add(window);
    }

    @Override
    public void onCreate() {
        // Make DockingWindow fullscreen
        ImGuiIO io = ImGui.getIO();
        float screenWidth = 1280;
        float screenHeight = 720;

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
        // Setup the window to act as a fullscreen dockspace
        int windowFlags = ImGuiWindowFlags.NoTitleBar
                        | ImGuiWindowFlags.NoCollapse
                        | ImGuiWindowFlags.NoResize
                        | ImGuiWindowFlags.NoMove
                        | ImGuiWindowFlags.NoBringToFrontOnFocus
                        | ImGuiWindowFlags.MenuBar;

        ImGui.setNextWindowPos(0, 0);
        ImGui.setNextWindowSize(1280, 720);

        ImGui.pushStyleVar(ImGuiStyleVar.WindowRounding, 0.0f);
        ImGui.pushStyleVar(ImGuiStyleVar.WindowBorderSize, 0.0f);

        ImGui.begin(name, windowFlags);

        ImGui.popStyleVar(2);

        // 👇 This creates the DockSpace
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
