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
import org.PiEngine.Component.*;


public class Main
{
    public static void main(String[] args)
    {
        if (!glfwInit())
        {
            throw new IllegalStateException("Unable to initialize GLFW");
        }

        long window = glfwCreateWindow(1280, 720, "Pi-Engine", 0, 0);


        if (window == 0)
        {
            throw new RuntimeException("Failed to create window");
        }

        Input.init(window);

        glfwMakeContextCurrent(window);
        GL.createCapabilities();

        int width = 1280;
        int height = 720;   
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
        //camera.setOrthographic( 8*-2, 8*2, -2 *4.5f, 2*4.5f, 1.0f, 100f);
        camera.setPerspective(70.0f, (float) width / height, 0.01f, 100f);
        camera.updateProjectionMatrix();
        camera.updateViewMatrix();

        glEnable(GL_DEPTH_TEST); // Optional if you're using z-buffer

        // Setup GameObjects
        GameObject world = new GameObject("World");
        GameObject player = new GameObject("Player");
        GameObject enemy = new GameObject("Enemy");

        GameObject Holder = new GameObject("Holder");
        GameObject ChildHolder = new GameObject("ChindHolder");
        GameObject CChildHolder = new GameObject("CChindHolder");

        




        //GameObject Hand = new GameObject("Hand");
        //GameObject gun = new GameObject("Gun");
        //GameObject muzzle = new GameObject("Muzzle");

        player.transform.setLocalPosition(new Vector(0f, 0, 0));

        Holder.transform.setLocalPosition(new Vector(4f, 0, 0));
        ChildHolder.transform.setLocalPosition(new Vector(5f, 0, 0));
        CChildHolder.transform.setLocalPosition(new Vector(5f, 0, 0));




        //gun.transform.setLocalPosition(new Vector(5, 0, 0));
        //gun.transform.setLocalRotation(new Vector(0, 0, 90));
        //muzzle.transform.setLocalPosition(new Vector(5, 0, 0));

        world.addChild(player);
        //player.addChild(gun);
        //gun.addChild(muzzle);
        //player.addChild(Hand);
        world.addChild(enemy);
        world.printHierarchy();

<<<<<<< Updated upstream
        world.addChild(Holder);
        Holder.addChild(ChildHolder);
        ChildHolder.addChild(CChildHolder);




        player.addComponent(new Movemet());
        enemy.addComponent(new Follower());
        Holder.addComponent(new SpinComponent());
        ChildHolder.addComponent(new SpinComponent());

        enemy.getComponent(Follower.class).Target = player;




        //player.addComponent(new SpinComponent());
        //gun.addComponent(new SpinComponent());

        //player.getComponent(SpinComponent.class).speed = 50.0f;

        Time.timeScale = 1.0f;
        float fp = 0;
=======
        // EditWindow PrimaryEditor = new EditWindow(new PrimaryEditor(), window,world);
        // PrimaryEditor.init();
        // PrimaryEditor.run();
        // PrimaryEditor.destroy();
>>>>>>> Stashed changes

        // Main loop
        while (!glfwWindowShouldClose(window))
        {
            Time.update();
            glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);

            Input.update();

            if (Input.isKeyDown(GLFW_KEY_UP))
            camera.getPosition().y -= 2 * Time.deltaTime;

            if (Input.isKeyDown(GLFW_KEY_DOWN))
            camera.getPosition().y += 2 * Time.deltaTime;

            if (Input.isKeyDown(GLFW_KEY_LEFT))
            camera.getPosition().x -= 2 * Time.deltaTime;

            if (Input.isKeyDown(GLFW_KEY_RIGHT))
            camera.getPosition().x += 2 * Time.deltaTime;
            
            world.update();
            world.debugRender();
            camera.updateViewMatrix();
            camera.applyToOpenGL();

<<<<<<< Updated upstream
            imguiGlfw.newFrame();   
            imguiGl3.newFrame();
            ImGui.newFrame();

            ImGui.begin("PiEngine Debug");
            ImGui.text("Camera Pos: " + camera.getPosition());
            ImGui.text("Player Pos: " + player.transform.getWorldPosition());
            ImGui.text("Enemy Pos: " + enemy.transform.getWorldPosition());
            ImGui.text("DeltaTime : " + String.format("%.10f", Time.unscaledDeltaTime));
            ImGui.text("FPS : " + 1/Time.unscaledDeltaTime);

            float ifs = Time.getAverageFPS();
            if(ifs%20 == 0)
            {
                ImGui.text("Average FPS : " + ifs);
                fp = ifs;
            }
            else
            {
                ImGui.text("Average FPS : " + fp);
            }

            ImGui.plotLines("Delta Time (ms)", Time.getDeltaHistory(), Time.getHistorySize(), 0, null, 0.0f, 0.01f,new ImVec2(0f, 80f));
=======
            // imguiGlfw.newFrame();
            // imguiGl3.newFrame();
            // ImGui.newFrame();

            // ImGui.begin("PiEngine Debug");
            // ImGui.text("Camera Pos: " + camera.getPosition());
            // ImGui.text("Player Pos: " + player.transform.getWorldPosition());
            // ImGui.text("Deltatime : " + Time.deltaTime);
            // ImGui.text("FPS : " + 1/Time.deltaTime);
            // ImGui.plotLines("Delta Time (ms)", Time.getDeltaHistory(), Time.getHistorySize(), 0, null, 0.0f, 0.01f,new ImVec2(0f, 80f));
>>>>>>> Stashed changes

            // ImGui.end();

            //ImGui.render();
            //glDisable(GL_DEPTH_TEST);
            //imguiGl3.renderDrawData(ImGui.getDrawData());
            //glEnable(GL_DEPTH_TEST);


            glfwSwapBuffers(window);
            glfwPollEvents();
        }

        ImGui.destroyContext();
        glfwTerminate();
    }
}