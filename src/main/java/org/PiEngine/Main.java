package org.PiEngine;

//import org.lwjgl.glfw.GLFW;
import org.lwjgl.opengl.GL;
import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;

public class Main {
    public static void main(String[] args)
    {

        if (!glfwInit()) throw new IllegalStateException("Unable to initialize GLFW");


        long window = glfwCreateWindow(800, 600, "Simple Triangle", 0, 0);
        if (window == 0) throw new RuntimeException("Failed to create window");


        glfwMakeContextCurrent(window);
        GL.createCapabilities();

        // Main rendering loop
        while (!glfwWindowShouldClose(window))
        {
            glClear(GL_COLOR_BUFFER_BIT);

            glBegin(GL_TRIANGLES);
            glColor3f(1.0f, 0.0f, 0.0f); glVertex2f(-0.5f, -0.5f); // Bottom left (red)
            glColor3f(0.0f, 1.0f, 0.0f); glVertex2f(0.5f, -0.5f);  // Bottom right (green)
            glColor3f(0.0f, 0.0f, 1.0f); glVertex2f(0.0f, 0.5f);   // Top (blue)
            glEnd();

            glfwSwapBuffers(window);
            glfwPollEvents();
        }

        glfwTerminate();
    }
}
