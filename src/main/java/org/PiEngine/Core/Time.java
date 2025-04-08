package org.PiEngine.Core;

import static org.lwjgl.glfw.GLFW.*;

/**
 * The Time class provides a centralized system for tracking time,
 * including delta time, fixed timestep updates, time scaling, and frame timing history.
 */
public class Time
{
    // --- Time tracking variables ---

    /** Scaled delta time (affected by timeScale), used for most in-game logic. */
    public static float deltaTime;

    /** Unscaled real-time delta time between frames. */
    public static float unscaledDeltaTime;

    /** Fixed delta time used for fixed updates (like physics). */
    public static float fixedDeltaTime = 1f / 60f;

    /** Time scale multiplier to slow down or speed up game time. */
    public static float timeScale = 1.0f;

    /** Accumulated time since the last fixed update. */
    public static float fixedTime = 0.0f;

    private static double lastTime;

    // --- Frame timing history for ImGui graphs ---

    /** Max number of frames to store in history (for plotting or smoothing). */
    private static final int MAX_HISTORY = 120;

    /** Circular buffer storing previous frame delta times. */
    private static final float[] deltaHistory = new float[MAX_HISTORY];

    /** Index of the current frame in the circular buffer. */
    private static int historyIndex = 0;

    /**
     * Initializes the time system.
     * Should be called once before the game loop starts.
     */
    public static void init()
    {
        lastTime = glfwGetTime();
    }

    /**
     * Updates all time-related values.
     * Should be called once per frame, ideally at the start of the game loop.
     */
    public static void update()
    {
        double currentTime = glfwGetTime();
        unscaledDeltaTime = (float) (currentTime - lastTime);
        deltaTime = unscaledDeltaTime * timeScale;
        lastTime = currentTime;

        fixedTime += deltaTime;

        // Store the unscaled delta time in a circular buffer for graphing
        deltaHistory[historyIndex] = unscaledDeltaTime;
        historyIndex = (historyIndex + 1) % MAX_HISTORY;
    }

    /**
     * Returns the frame delta time history for plotting graphs.
     *
     * @return A float array of unscaled delta times in order.
     */
    public static float[] getDeltaHistory()
    {
        float[] ordered = new float[MAX_HISTORY];
        for (int i = 0; i < MAX_HISTORY; i++)
        {
            int index = (historyIndex + i) % MAX_HISTORY;
            ordered[i] = deltaHistory[index];
        }
        return ordered;
    }

    /**
     * Gets the size of the delta history buffer.
     *
     * @return Number of frames tracked in history.
     */
    public static int getHistorySize()
    {
        return MAX_HISTORY;
    }

    /**
     * Consumes one fixed timestep from the accumulator.
     * Should be called after processing a fixedUpdate step.
     */
    public static void consumeFixedDeltaTime()
    {
        fixedTime -= fixedDeltaTime;
    }

    /**
     * Checks whether enough time has accumulated to process a fixed update.
     *
     * @return True if a fixedUpdate step should run this frame.
     */
    public static boolean shouldRunFixedUpdate()
    {
        return fixedTime >= fixedDeltaTime;
    }

    /**
     * Calculates the average FPS based on the delta time history buffer.
     *
     * @return A smoothed average frames-per-second value.
     */
    public static float getAverageFPS()
    {
        float[] history = getDeltaHistory();
        int size = getHistorySize();

        if (size == 0)
            return 0;

        float total = 0f;
        for (int i = 0; i < size; i++)
        {
            total += 1/history[i];
        }

        float average = total / size;
        return (float) Math.floor(average);
    }
}
