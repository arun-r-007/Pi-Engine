package org.PiEngine;

import org.lwjgl.opengl.GL;
import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;

import org.PiEngine.Math.Vector;
import org.PiEngine.Core.Camera;

public class Main
{
    public static void main(String[] args)
    {
        if (!glfwInit())
        {
            throw new IllegalStateException("Unable to initialize GLFW");
        }

        long window = glfwCreateWindow(800, 600, "Simple Triangle", 0, 0);
        if (window == 0)
        {
            throw new RuntimeException("Failed to create window");
        }

        glfwMakeContextCurrent(window);
        GL.createCapabilities();

        int width = 800;
        int height = 600;   

        // 🔷 Create a Camera
        Camera camera = new Camera((float) width / height, 0.01f, 100.0f);
        camera.setPosition(new Vector(0, 0, 3.0f)); // Move camera back to see triangle
        camera.setRotation(new Vector(0, 0, 0));
        
        camera.setOrthographic(-1, 1, -1, 1, 1, 100f);

        //camera.setPerspective(30.0f, (float) 4/3, 0.01f, 100f);
        
        System.out.println("Projection:\n" + camera.getProjectionMatrix());
        System.out.println("View:\n" + camera.getViewMatrix());
        //System.out.println("View:\n" + camera.getPosition());
        
        camera.updateProjectionMatrix();
        camera.updateViewMatrix();

        glViewport(0, 0, width, height);
        glEnable(GL_DEPTH_TEST); // Optional if you're using z-buffer

        // Main loop
        while (!glfwWindowShouldClose(window))
        {
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
                camera.getPosition().x -= 0.001f; 
            }

            if (glfwGetKey(window, GLFW_KEY_D) == GLFW_PRESS) 
            {
                camera.getPosition().x += 0.001f; 
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


                
            
            camera.updateViewMatrix();

            glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
            //glClearColor(0.1f, 0.1f, 0.1f, 1.0f); // dark gray background
            camera.applyToOpenGL();


            // 🔺 Draw Triangle
           // Front face (Red)
           glBegin(GL_TRIANGLES);
           glColor3f(1f, 0.f, 0.0f);
           glVertex3f(-0.1f, -0.1f,  0.1f);
           glVertex3f( 0.1f, -0.1f,  0.1f);
           glVertex3f( 0.1f,  0.1f,  0.1f);

           glVertex3f(-0.1f, -0.1f,  0.1f);
           glVertex3f( 0.1f,  0.1f,  0.1f);
           glVertex3f(-0.1f,  0.1f,  0.1f);
           glEnd();

           // Back face (Green)
           glBegin(GL_TRIANGLES);
           glColor3f(0.0f, 1f, 0.0f);
           glVertex3f( 0.1f, -0.1f, -0.1f);
           glVertex3f(-0.1f, -0.1f, -0.1f);
           glVertex3f(-0.1f,  0.1f, -0.1f);

           glVertex3f( 0.1f, -0.1f, -0.1f);
           glVertex3f(-0.1f,  0.1f, -0.1f);
           glVertex3f( 0.1f,  0.1f, -0.1f);
           glEnd();

           // Left face (Blue)
           glBegin(GL_TRIANGLES);
           glColor3f(0.0f, 0.0f, 1f);
           glVertex3f(-0.1f, -0.1f, -0.1f);
           glVertex3f(-0.1f, -0.1f,  0.1f);
           glVertex3f(-0.1f,  0.1f,  0.1f);

           glVertex3f(-0.1f, -0.1f, -0.1f);
           glVertex3f(-0.1f,  0.1f,  0.1f);
           glVertex3f(-0.1f,  0.1f, -0.1f);
           glEnd();

           // Right face (Yellow)
           glBegin(GL_TRIANGLES);
           glColor3f(1f, 1f, 0.0f);
           glVertex3f(0.1f, -0.1f,  0.1f);
           glVertex3f(0.1f, -0.1f, -0.1f);
           glVertex3f(0.1f,  0.1f, -0.1f);

           glVertex3f(0.1f, -0.1f,  0.1f);
           glVertex3f(0.1f,  0.1f, -0.1f);
           glVertex3f(0.1f,  0.1f,  0.1f);
           glEnd();

           // Top face (Cyan)
           glBegin(GL_TRIANGLES);
           glColor3f(0.0f, 1f, 1f);
           glVertex3f(-0.1f, 0.1f,  0.1f);
           glVertex3f(0.1f, 0.1f,  0.1f);
           glVertex3f(0.1f, 0.1f, -0.1f);

           glVertex3f(-0.1f, 0.1f,  0.1f);
           glVertex3f(0.1f, 0.1f, -0.1f);
           glVertex3f(-0.1f, 0.1f, -0.1f);
           glEnd();

           // Bottom face (Magenta)
           glBegin(GL_TRIANGLES);
           glColor3f(1.0f, 0.0f, 1.0f);
           glVertex3f(-0.1f, -0.1f, -0.1f);
           glVertex3f(0.1f, -0.1f, -0.1f);
           glVertex3f(0.1f, -0.1f,  0.1f);

           glVertex3f(-0.1f, -0.1f, -0.1f);
           glVertex3f(0.1f, -0.1f,  0.1f);
           glVertex3f(-0.1f, -0.1f,  0.1f);
           glEnd();

            
            glfwSwapBuffers(window);
            glfwPollEvents();
        }

        glfwTerminate();
    }
}
