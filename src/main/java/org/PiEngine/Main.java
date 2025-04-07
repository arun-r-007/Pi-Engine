package org.PiEngine;

import org.lwjgl.opengl.GL;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;

import imgui.ImGui;
import imgui.ImGuiIO;
import imgui.flag.ImGuiConfigFlags;
import imgui.gl3.ImGuiImplGl3;
import imgui.glfw.ImGuiImplGlfw;
import imgui.ImVec2;




import org.PiEngine.Math.*;
import org.PiEngine.Core.*;
import org.PiEngine.GameObjects.*;



public class Main
{
    public static void main(String[] args)
    {
        if (!glfwInit())
        {
            throw new IllegalStateException("Unable to initialize GLFW");
        }

        long window = glfwCreateWindow(800, 800, "Pi-Engine", 0, 0);


        if (window == 0)
        {
            throw new RuntimeException("Failed to create window");
        }

        Input.init(window);

        glfwMakeContextCurrent(window);
        GL.createCapabilities();

        int width = 800;
        int height = 800;   
        glViewport(0, 0, width, height);

        // Initialize ImGui
        ImGui.createContext();
        ImGuiIO io = ImGui.getIO();
        io.addConfigFlags(ImGuiConfigFlags.DockingEnable);
        ImGuiImplGlfw imguiGlfw = new ImGuiImplGlfw();
        ImGuiImplGl3 imguiGl3 = new ImGuiImplGl3();
        imguiGlfw.init(window, true);
        imguiGl3.init("#version 330 core");

        // 🔷 Create a Camera
        Camera camera = new Camera((float) width / height, 0.01f, 100.0f);
        camera.setPosition(new Vector(0, 0, 20.0f)); // Move camera back to see triangle
        camera.setRotation(new Vector(0, 0, 0));
        //camera.setOrthographic(-10, 10, -10, 10, 1.0f, 100f);
        camera.setPerspective(70.0f, (float) width / height, 0.01f, 100f);
        camera.updateProjectionMatrix();
        camera.updateViewMatrix();

        glEnable(GL_DEPTH_TEST); // Optional if you're using z-buffer

        // Setup GameObjects
        GameObject world = new GameObject("World");
        GameObject player = new GameObject("Player");
        GameObject Hand = new GameObject("Hand");
        GameObject gun = new GameObject("Gun");
        GameObject muzzle = new GameObject("Muzzle");
        GameObject enemy = new GameObject("Enemy");

        player.transform.setLocalPosition(new Vector(0f, 0, 0));
        gun.transform.setLocalPosition(new Vector(5, 0, 0));
        gun.transform.setLocalRotation(new Vector(0, 0, 90));
        muzzle.transform.setLocalPosition(new Vector(5, 0, 5));

        world.addChild(player);
        player.addChild(gun);
        gun.addChild(muzzle);
        player.addChild(Hand);
        world.addChild(enemy);
        world.printHierarchy();

        // Main loop
        while (!glfwWindowShouldClose(window))
        {
            Time.update();
            glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);

            Input.update();

            if (Input.isKeyDown(GLFW_KEY_W)) camera.getPosition().y -= 0.01f;
            if (Input.isKeyDown(GLFW_KEY_S)) camera.getPosition().y += 0.01f;
            if (Input.isKeyDown(GLFW_KEY_A)) camera.getPosition().x += 0.01f;
            if (Input.isKeyDown(GLFW_KEY_D)) camera.getPosition().x -= 0.01f;

            if (Input.isKeyDown(GLFW_KEY_UP)) camera.getRotation().z -= 0.01f;
            if (Input.isKeyDown(GLFW_KEY_DOWN)) camera.getRotation().z += 0.01f;
            if (Input.isKeyDown(GLFW_KEY_LEFT)) camera.getRotation().y -= 0.01f;
            if (Input.isKeyDown(GLFW_KEY_RIGHT)) camera.getRotation().y += 0.01f;

            if (Input.isKeyDown(GLFW_KEY_SPACE))
            {
                player.transform.setWorldPosition(player.transform.getWorldPosition().add(new Vector(0.01f, 0, 0)));
            }

            world.update();
            camera.updateViewMatrix();
            camera.applyToOpenGL();

            imguiGlfw.newFrame();
            imguiGl3.newFrame();
            ImGui.newFrame();

            ImGui.begin("PiEngine Debug");
            ImGui.text("Camera Pos: " + camera.getPosition());
            ImGui.text("Player Pos: " + player.transform.getWorldPosition());
            ImGui.text("Deltatime : " + Time.deltaTime);
            ImGui.text("FPS : " + 1/Time.deltaTime);
            ImGui.plotLines("Delta Time (ms)", Time.getDeltaHistory(), Time.getHistorySize(), 0, null, 0.0f, 0.01f,new ImVec2(0f, 80f));

            ImGui.end();

            ImGui.render();
            glDisable(GL_DEPTH_TEST);
            imguiGl3.renderDrawData(ImGui.getDrawData());
            glEnable(GL_DEPTH_TEST);


            glfwSwapBuffers(window);
            glfwPollEvents();
        }


        ImGui.destroyContext();
        glfwTerminate();
    }
}
