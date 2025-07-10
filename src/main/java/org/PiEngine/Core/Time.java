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

    public static float Time;
    /**
     * Initializes the time system.
     * Should be called once before the game loop starts.
     */
    public static void init()
    {
        lastTime = glfwGetTime();
        Time = 0.0f;
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
        Time += unscaledDeltaTime;


        // Store the unscaled delta time in a circular buffer for graphing
        deltaHistory[historyIndex] = unscaledDeltaTime;
        historyIndex = (historyIndex + 1) % MAX_HISTORY;
    }

    /**
     * Returns the time in seconds since the engine started.
     * @return The elapsed time in seconds
     */
    public static float getTime()
    {
        return (float) glfwGetTime();
    }

    /**
     * Returns the time in milliseconds since the engine started.
     * @return The elapsed time in milliseconds
     */
    public static float getTimeMillis()
    {
        return (float) (glfwGetTime() * 1000.0);
    }

    /**
     * Returns the time in nanoseconds since the engine started.
     * @return The elapsed time in nanoseconds
     */
    public static float getTimeNanos()
    {
        return (float) (glfwGetTime() * 1000000.0);
    }

    /**
     * Returns the time between the last two frames in seconds (delta time).
     * @return The delta time in seconds
     */
    public static float getDeltaTime()
    {
        return deltaTime;
    }

    /**
     * Returns the unscaled time between the last two frames in seconds (unscaled delta time).
     * @return The unscaled delta time in seconds
     */
    public static float getUnscaledDeltaTime()
    {
        return unscaledDeltaTime;
    }

    /**
     * Returns the fixed delta time in seconds, used for fixed updates.
     * @return The fixed delta time in seconds
     */
    public static float getFixedDeltaTime()
    {
        return fixedDeltaTime;
    }

    /**
     * Returns the current time scale multiplier.
     * @return The time scale
     */
    public static float getTimeScale()
    {
        return timeScale;
    }

    /**
     * Sets a new time scale multiplier.
     * @param scale The new time scale
     */
    public static void setTimeScale(float scale)
    {
        timeScale = scale;
    }

    /**
     * Returns the accumulated fixed time in seconds.
     * This value increases over time and is used to determine when to perform fixed updates.
     * @return The accumulated fixed time
     */
    public static float getFixedTime()
    {
        return fixedTime;
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
