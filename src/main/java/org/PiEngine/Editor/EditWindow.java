package org.PiEngine.Editor;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;
import org.PiEngine.GameObjects.*;


import imgui.ImGui;
import imgui.ImGuiIO;
import imgui.flag.ImGuiConfigFlags;
import imgui.gl3.ImGuiImplGl3;
import imgui.glfw.ImGuiImplGlfw;
import org.lwjgl.glfw.GLFW;

public class EditWindow {
    private final ImGuiImplGlfw imguiGlfw = new ImGuiImplGlfw();
    private final ImGuiImplGl3 imguiGl3 = new ImGuiImplGl3();

    private String glslVersion = "#version 330 core";
    private long windowPtr;
    private PrimaryEditor PrimaryEditor;
    private GameObject world;

    public EditWindow(PrimaryEditor layer,long window,GameObject game_World)
    {
        world = game_World;
        PrimaryEditor = layer;
        windowPtr = window;
    }

    public void init()
    {
        initImGui();
        System.out.println("Call complete.....\n");
        imguiGlfw.init(windowPtr, true);
        System.out.println("GLFW called .....\n\n");
        imguiGl3.init(glslVersion);
        System.out.println("GLF3 called .....\n\n");
    }

    public void destroy() 
    {
        ImGui.destroyContext();
        glfwTerminate();
    }

    private void initImGui()
    {
        System.out.println("InitGui called .....\n\n");
        ImGui.createContext();
        ImGuiIO io = ImGui.getIO();
        io.addConfigFlags(ImGuiConfigFlags.ViewportsEnable);
        io.getFonts().addFontDefault();
    }



    public void run() {
        while (!glfwWindowShouldClose(windowPtr)) 
        {
            glClearColor(0.1f, 0.09f, 0.1f, 1.0f);
            glClear(GL_COLOR_BUFFER_BIT);
    
            // First initialize the frame for GLFW (imGuiGlfw)
            imguiGlfw.newFrame();
    
            // Then initialize the frame for OpenGL (imGuiGl3)
            imguiGl3.newFrame();
    
            // Finally, initialize ImGui itself
            ImGui.newFrame();
    
            // Your Editor UI
            PrimaryEditor.imgui(world);
    
            //ImGui.end();
            ImGui.render();
            imguiGl3.renderDrawData(ImGui.getDrawData());
    
            // If viewports are enabled, handle the platform windows rendering
            if (ImGui.getIO().hasConfigFlags(ImGuiConfigFlags.ViewportsEnable)) 
            {
                final long backupWindowPtr = GLFW.glfwGetCurrentContext();
                ImGui.updatePlatformWindows();
                ImGui.renderPlatformWindowsDefault();
                GLFW.glfwMakeContextCurrent(backupWindowPtr);
            }
    
            // Swap buffers and poll events
            GLFW.glfwSwapBuffers(windowPtr);
            GLFW.glfwPollEvents();
        }
    }
    
}
