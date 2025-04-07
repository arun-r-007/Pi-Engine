package org.PiEngine.Core;

import static org.lwjgl.glfw.GLFW.*;

public class Time
{
    public static float deltaTime;           // Scaled time between frames
    public static float unscaledDeltaTime;   // Real time between frames
    public static float fixedDeltaTime = 1f / 60f; // Fixed step (e.g., 60 FPS)

    public static float timeScale = 1.0f;    // Multiplier for slowing/speeding time
    public static float fixedTime = 0.0f;    // Time accumulator for fixed updates

    private static double lastTime;

    // Graph buffer
    private static final int MAX_HISTORY = 1200; // last 2 seconds at 60fps
    private static final float[] deltaHistory = new float[MAX_HISTORY];
    private static int historyIndex = 0;

    public static void init()
    {
        lastTime = glfwGetTime();
    }

    public static void update()
    {
        double currentTime = glfwGetTime();
        unscaledDeltaTime = (float) (currentTime - lastTime);
        deltaTime = unscaledDeltaTime * timeScale;
        lastTime = currentTime;

        fixedTime += deltaTime;

        // Add current frame time to the circular buffer
        deltaHistory[historyIndex] = unscaledDeltaTime;
        historyIndex = (historyIndex + 1) % MAX_HISTORY;
    }

    public static float[] getDeltaHistory()
    {
        // Rearrange into chronological order for ImGui plotting
        float[] ordered = new float[MAX_HISTORY];
        for (int i = 0; i < MAX_HISTORY; i++)
        {
            int index = (historyIndex + i) % MAX_HISTORY;
            ordered[i] = deltaHistory[index];
        }
        return ordered;
    }

    public static int getHistorySize()
    {
        return MAX_HISTORY;
    }

    public static void consumeFixedDeltaTime()
    {
        fixedTime -= fixedDeltaTime;
    }

    public static boolean shouldRunFixedUpdate()
    {
        return fixedTime >= fixedDeltaTime;
    }
}
