package org.PiEngine.Math;
import java.util.Random;

public class MathF
{
    private static final Random random = new Random();

    /**
     * Linearly interpolates between a and b by t.
     * @param a Start value
     * @param b End value
     * @param t Interpolation factor (0 to 1)
     * @return Interpolated value
     */
    public static float lerp(float a, float b, float t)
    {
        return a + t * (b - a);
    }

    /**
     * Clamps a value between min and max.
     */
    public static float clamp(float value, float min, float max)
    {
        return Math.max(min, Math.min(max, value));
    }

    /**
     * Returns a random float between 0.0 and 1.0.
     */
    public static float random()
    {
        return random.nextFloat();
    }

    /**
     * Returns a random float between min and max.
     */
    public static float randomRange(float min, float max)
    {
        return lerp(min, max, random());
    }

    /**
     * Returns the sign of the value (-1, 0, or 1).
     */
    public static int sign(float value)
    {
        return (int) Math.signum(value);
    }

    /**
     * Moves value towards target by maxDelta.
     */
    public static float moveTowards(float current, float target, float maxDelta)
    {
        if (Math.abs(target - current) <= maxDelta)
            return target;
        return current + Math.signum(target - current) * maxDelta;
    }

    /**
     * Converts degrees to radians.
     */
    public static float degToRad(float degrees)
    {
        return (float) Math.toRadians(degrees);
    }

    /**
     * Converts radians to degrees.
     */
    public static float radToDeg(float radians)
    {
        return (float) Math.toDegrees(radians);
    }
}

