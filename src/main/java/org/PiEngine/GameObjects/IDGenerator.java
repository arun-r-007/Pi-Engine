package org.PiEngine.GameObjects;

public class IDGenerator 
{
    private static int currentID = 0;

    public static int generateUniqueID() 
    {
        return currentID++;
    }

    public static void resetIDCounter() 
    {
        currentID = 0;
    }
}