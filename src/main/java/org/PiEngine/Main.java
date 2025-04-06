package org.PiEngine;

import org.lwjgl.opengl.GL;
import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;

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

        long window = glfwCreateWindow(800, 600, "Pi-Engine", 0, 0);
        if (window == 0)
        {
            throw new RuntimeException("Failed to create window");
        }

        glfwMakeContextCurrent(window);
        GL.createCapabilities();

        int width = 800;
        int height = 800;   
        glViewport(0, 0, width, height);

        // 🔷 Create a Camera
        Camera camera = new Camera((float) width / height, 0.01f, 100.0f);
        camera.setPosition(new Vector(0, 0, 20.0f)); // Move camera back to see triangle
        camera.setRotation(new Vector(0, 0, 0));
        
        camera.setOrthographic(-10, 10, -10, 10, 1.0f, 100f);

        //camera.setPerspective(70.0f, (float) width / height, 0.01f, 100f);
        
        //System.out.println("Projection:\n" + camera.getProjectionMatrix());
        //System.out.println("View:\n" + camera.getViewMatrix());
        //System.out.println("View:\n" + camera.getPosition());
        
        camera.updateProjectionMatrix();
        camera.updateViewMatrix();

        glEnable(GL_DEPTH_TEST); // Optional if you're using z-buffer

  

        // Print all transform positions
        GameObject world = new GameObject("World");
        GameObject player = new GameObject("Player");
        GameObject Hand = new GameObject("Hand");
        GameObject gun = new GameObject("Gun");
        GameObject muzzle = new GameObject("Muzzle");
        GameObject enemy = new GameObject("Enemy");

        player.transform.setLocalPosition(new Vector(0f, 0, 0));
        gun.transform.setLocalPosition(new Vector(5, 0, 0));
        gun.transform.setLocalRotation(new Vector(0, 0, 90));
        muzzle.transform.setLocalPosition(new Vector(10, 0, 0));
        

        world.addChild(player);
        player.addChild(gun);
        gun.addChild(muzzle);
        player.addChild(Hand);
        world.addChild(enemy);
        world.printHierarchy();
       


        // Main loop
        while (!glfwWindowShouldClose(window))
        {
            glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);

            // Handle camera movement (WASD keys)
            if (glfwGetKey(window, GLFW_KEY_W) == GLFW_PRESS) 
            {
                camera.getPosition().y -= 0.001f; 
            }

            if (glfwGetKey(window, GLFW_KEY_S) == GLFW_PRESS) 
            {
                camera.getPosition().y += 0.001f; 
            }

            if (glfwGetKey(window, GLFW_KEY_A) == GLFW_PRESS) 
            {
                camera.getPosition().x += 0.001f; 
            }

            if (glfwGetKey(window, GLFW_KEY_D) == GLFW_PRESS) 
            {
                camera.getPosition().x -= 0.001f; 
            }


            if (glfwGetKey(window, GLFW_KEY_UP) == GLFW_PRESS) 
            {
                camera.getRotation().x -= 0.01f; 
            }

            if (glfwGetKey(window, GLFW_KEY_DOWN) == GLFW_PRESS) 
            {
                camera.getRotation().x += 0.01f; 
            }

            if (glfwGetKey(window, GLFW_KEY_LEFT) == GLFW_PRESS) 
            {
                camera.getRotation().y -= 0.01f; 
            }

            if (glfwGetKey(window, GLFW_KEY_RIGHT) == GLFW_PRESS) 
            {
                camera.getRotation().y += 0.01f; 
            }

            if (glfwGetKey(window, GLFW_KEY_SPACE) == GLFW_PRESS) 
            {
                player.transform.setWorldPosition(player.transform.getWorldPosition().add(new Vector(0.001f, 0, 0)));
                //world.printHierarchy();
                //world.transform.updateMatrix();
                //System.out.println(" ");
            }

            
            
            world.update();
            camera.updateViewMatrix();

            ///GameObject.debugDrawCube(world.transform.getWorldPosition(), 0.1f);
            //GameObject.debugDrawCube(gun.transform.getWorldPosition(), 0.7f);
            //System.err.println(gun.transform.getLocalPosition());
            
            camera.applyToOpenGL();


            
            glfwSwapBuffers(window);
            glfwPollEvents();
        }

        glfwTerminate();
    }
}
