package org.PiEngine.Math;
import java.util.Random;

/**
 * Provides mathematical utility functions for common game development operations.
 * Includes methods for interpolation, clamping, random number generation, and mathematical conversions.
 */
public class MathF
{
    private static final Random random = new Random();

    /**
     * Linearly interpolates between a and b by t.
     * @param a Start value
     * @param b End value
     * @param t Interpolation factor (0 to 1)
     * @return Interpolated value between a and b
     */
    public static float lerp(float a, float b, float t)
    {
        return a + t * (b - a);
    }

    /**
     * Clamps a value between min and max.
     * @param value The value to clamp
     * @param min Minimum allowed value
     * @param max Maximum allowed value
     * @return The clamped value between min and max
     */
    public static float clamp(float value, float min, float max)
    {
        return Math.max(min, Math.min(max, value));
    }

    /**
     * Returns a random float between 0.0 and 1.0.
     * @return Random float in the range [0,1]
     */
    public static float random()
    {
        return random.nextFloat();
    }

    /**
     * Returns a random float between min and max.
     * @param min Minimum value (inclusive)
     * @param max Maximum value (inclusive)
     * @return Random float in the range [min,max]
     */
    public static float randomRange(float min, float max)
    {
        return lerp(min, max, random());
    }

    /**
     * Returns the sign of the value (-1, 0, or 1).
     * @param value Input value
     * @return -1 if negative, 0 if zero, 1 if positive
     */
    public static int sign(float value)
    {
        return (int) Math.signum(value);
    }

    /**
     * Moves value towards target by maxDelta.
     * @param current Current value
     * @param target Target value
     * @param maxDelta Maximum change allowed
     * @return New value moved towards target
     */
    public static float moveTowards(float current, float target, float maxDelta)
    {
        if (Math.abs(target - current) <= maxDelta)
            return target;
        return current + Math.signum(target - current) * maxDelta;
    }

    /**
     * Converts degrees to radians.
     * @param degrees Angle in degrees
     * @return Angle in radians
     */
    public static float degToRad(float degrees)
    {
        return (float) Math.toRadians(degrees);
    }

    /**
     * Converts radians to degrees.
     * @param radians Angle in radians
     * @return Angle in degrees
     */
    public static float radToDeg(float radians)
    {
        return (float) Math.toDegrees(radians);
    }
}

