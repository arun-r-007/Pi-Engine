package org.PiEngine.Utils;

/**
 * Interface for objects that need GUID-based identification.
 * Implementing classes can be uniquely identified and referenced across the engine.
 */
public interface GUIDProvider
{
    /**
     * Gets the object's GUID.
     * @return The GUID string
     */
    String getGUID();
    
    /**
     * Sets the object's GUID.
     * @param guid The GUID string to assign
     */
    void setGUID(String guid);
}
