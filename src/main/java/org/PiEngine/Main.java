package org.PiEngine;

import org.lwjgl.opengl.GL;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL30.*;

import imgui.ImGui;
import imgui.ImGuiIO;
import imgui.flag.ImGuiConfigFlags;
import imgui.gl3.ImGuiImplGl3;
import imgui.glfw.ImGuiImplGlfw;

import org.PiEngine.Core.*;
import org.PiEngine.Editor.Editor;
import org.PiEngine.Engine.Scene;
import org.PiEngine.Manager.AssetManager;
import org.PiEngine.Scripting.CompileScripts;


public class Main
{
    public static long Windowthis = 0;
    public static String ResourceFolder = "src/main/resources/";
    public static void main(String[] args)
    {
        
        if (!glfwInit())
        {
            throw new IllegalStateException("Unable to initialize GLFW");
        }

        int width = 1600;
        int height = 900;
        long window = glfwCreateWindow(width, height, "Pi-Engine", 0, 0);
        if (window == 0)
        {
            throw new RuntimeException("Failed to create window");
        }

        Input.init(window);
        glfwMakeContextCurrent(window);
        GL.createCapabilities();
        
        glViewport(0, 0, width, height);
        
        ImGui.createContext();
        ImGuiIO io = ImGui.getIO();
        io.addConfigFlags(ImGuiConfigFlags.DockingEnable);
        ImGuiImplGlfw imguiGlfw = new ImGuiImplGlfw();
        ImGuiImplGl3 imguiGl3 = new ImGuiImplGl3();
        imguiGlfw.init(window, true);
        imguiGl3.init("#version 330 core");
        
        CompileScripts.getInstance(Main.ResourceFolder + "Scripts", "Compiled", null);


        Thread assetThread = new Thread(() -> {
            AssetManager assetManager = new AssetManager() {};
            assetManager.run();
        });
        assetThread.start();

        String javaHome = System.getProperty("java.home");
        System.out.println("Running with JRE at: " + javaHome);
        
        Editor.getInstance(window, false);
        Editor.getInstance().init();


        Scene.getInstance().init(window, width, height);


        boolean isLoop = false;

        
        while (!glfwWindowShouldClose(window))
        {
            glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
            AssetManager.processAssetQueue();
            Time.update();
            Input.update();

            if(Input.isKeyPressed(GLFW_KEY_SPACE)) 
            {
                isLoop = !isLoop;
            }

            if(isLoop)
            {
                Scene.getInstance().update();
            }
            Scene.getInstance().render();
            
            Editor.getInstance().update();
            
            glfwSwapBuffers(window);
            glfwPollEvents();
        }

        ImGui.destroyContext();
        glfwTerminate();
    }
}
