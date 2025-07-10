package org.PiEngine.Core;

import static org.lwjgl.glfw.GLFW.*;

public class Input
{
    private static long window;

    private static final int MAX_KEYS = 350;
    private static final int MAX_BUTTONS = 8;

    private static boolean[] keys = new boolean[MAX_KEYS];
    private static boolean[] keysLast = new boolean[MAX_KEYS];

    private static boolean[] buttons = new boolean[MAX_BUTTONS];
    private static boolean[] buttonsLast = new boolean[MAX_BUTTONS];

    /**
     * Initializes the input system with the window handle.
     * @param windowHandle The GLFW window handle
     */
    public static void init(long windowHandle)
    {
        window = windowHandle;
    }

    /**
     * Updates input states. Must be called once per frame.
     */
    public static void update()
    {
        for (int i = 0; i < MAX_KEYS; i++)
        {
            keysLast[i] = keys[i];
            keys[i] = glfwGetKey(window, i) == GLFW_PRESS;
        }

        for (int i = 0; i < MAX_BUTTONS; i++)
        {
            buttonsLast[i] = buttons[i];
            buttons[i] = glfwGetMouseButton(window, i) == GLFW_PRESS;
        }
    }

    /**
     * Returns true while the key is held down.
     * @param key The GLFW key code
     * @return True if the key is down
     */
    public static boolean isKeyDown(int key)
    {
        return keys[key];
    }

    /**
     * Returns true only on the frame the key was pressed.
     * @param key The GLFW key code
     * @return True if the key was pressed this frame
     */
    public static boolean isKeyPressed(int key)
    {
        return keys[key] && !keysLast[key];
    }

    /**
     * Returns true only on the frame the key was released.
     * @param key The GLFW key code
     * @return True if the key was released this frame
     */
    public static boolean isKeyReleased(int key)
    {
        return !keys[key] && keysLast[key];
    }

    /**
     * Returns true while the mouse button is held down.
     * @param button The GLFW mouse button code
     * @return True if the button is down
     */
    public static boolean isMouseDown(int button)
    {
        return buttons[button];
    }

    /**
     * Returns true only on the frame the mouse button was pressed.
     * @param button The GLFW mouse button code
     * @return True if the button was pressed this frame
     */
    public static boolean isMousePressed(int button)
    {
        return buttons[button] && !buttonsLast[button];
    }

    /**
     * Returns true only on the frame the mouse button was released.
     * @param button The GLFW mouse button code
     * @return True if the button was released this frame
     */
    public static boolean isMouseReleased(int button)
    {
        return !buttons[button] && buttonsLast[button];
    }

    /**
     * Gets current mouse X position.
     * @return Mouse X position in window coordinates
     */
    public static float getMouseX()
    {
        double[] x = new double[1];
        glfwGetCursorPos(window, x, new double[1]);
        return (float) x[0];
    }

    /**
     * Gets current mouse Y position.
     * @return Mouse Y position in window coordinates
     */
    public static float getMouseY()
    {
        double[] y = new double[1];
        glfwGetCursorPos(window, new double[1], y);
        return (float) y[0];
    }

    /**
     * Returns the current state of the specified key.
     * @param key The key code to check
     * @return True if the key is pressed, false otherwise
     */
    public static boolean getKeyState(int key)
    {
        return keys[key];
    }

    /**
     * Returns the current state of the specified mouse button.
     * @param button The mouse button code to check
     * @return True if the mouse button is pressed, false otherwise
     */
    public static boolean getMouseButtonState(int button)
    {
        return buttons[button];
    }

    /**
     * Updates the input state. Should be called once per frame.
     */
    public static void updateInput()
    {
        update();
    }
}
