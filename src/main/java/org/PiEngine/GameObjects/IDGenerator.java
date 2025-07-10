package org.PiEngine.GameObjects;

/**
 * Utility class for generating unique integer IDs for GameObjects or other entities.
 */
public class IDGenerator 
{
    /** The current ID counter (auto-incremented) */
    private static int currentID = 0;

    /**
     * Generates and returns a unique integer ID.
     * @return Unique integer ID (auto-incremented)
     */
    public static int generateUniqueID() 
    {
        return currentID++;
    }

    /**
     * Resets the ID counter to zero.
     */
    public static void resetIDCounter() 
    {
        currentID = 0;
    }
}