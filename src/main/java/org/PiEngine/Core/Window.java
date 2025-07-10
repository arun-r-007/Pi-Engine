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

    /**
     * Creates the window with the specified width, height, and title.
     */
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

    /**
     * Updates the window, swapping buffers and polling events.
     */
    public static void update()
    {
        glfwSwapBuffers(window);
        glfwPollEvents();
    }



    /**
     * Requests the window to close.
     */
    public static void requestClose()
    {
        glfwSetWindowShouldClose(window, true);
    }

    /**
     * Returns true if the window should close.
     * @return True if the window should close, false otherwise
     */
    public static boolean shouldClose()
    {
        return glfwWindowShouldClose(window);
    }

    /**
     * Returns the window handle.
     * @return The GLFW window handle
     */
    public static long getWindow()
    {
        return window;
    }

    public static int getWidth() { return width; }

    /**
     * Returns the height of the window in pixels.
     * @return The window height
     */
    public static int getHeight() { return height; }

    /**
     * Sets the window title.
     * @param title The new window title
     */
    public static void setTitle(String newTitle) {
        title = newTitle;
        glfwSetWindowTitle(window, title);
    }

    /**
     * Sets the window size.
     * @param width The new width in pixels
     * @param height The new height in pixels
     */
    public static void setSize(int newWidth, int newHeight) {
        width = newWidth;
        height = newHeight;
        glfwSetWindowSize(window, width, height);
    }

    /**
     * Destroys the window and terminates GLFW.
     */
    public static void destroy()
    {
        glfwDestroyWindow(window);
        glfwTerminate();
    }
}
