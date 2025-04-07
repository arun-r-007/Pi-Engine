package org.PiEngine.Core;

import org.lwjgl.opengl.GL;
import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;

public class Window
{
    private static long window;
    private static int width = 1280;
    private static int height = 720;
    private static String title = "PiEngine";

    public static void create()
    {
        if (!glfwInit())
        {
            throw new IllegalStateException("Failed to initialize GLFW");
        }

        glfwWindowHint(GLFW_CONTEXT_VERSION_MAJOR, 3);
        glfwWindowHint(GLFW_CONTEXT_VERSION_MINOR, 3);
        glfwWindowHint(GLFW_OPENGL_PROFILE, GLFW_OPENGL_CORE_PROFILE);

        window = glfwCreateWindow(width, height, title, 0, 0);
        if (window == 0)
        {
            throw new RuntimeException("Failed to create GLFW window");
        }

        glfwMakeContextCurrent(window);
        GL.createCapabilities();
        glfwSwapInterval(1);
        glViewport(0, 0, width, height);
    }

    public static void update()
    {
        glfwSwapBuffers(window);
        glfwPollEvents();
    }

    public static boolean shouldClose()
    {
        return glfwWindowShouldClose(window);
    }

    public static long getWindow()
    {
        return window;
    }

    public static int getWidth() { return width; }
    public static int getHeight() { return height; }

    public static void destroy()
    {
        glfwTerminate();
    }
}
