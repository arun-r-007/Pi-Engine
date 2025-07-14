package org.PiEngine.Utils;

import java.nio.file.*;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Utility class for generating globally unique identifiers from file paths.
 * Uses SHA-256 hashing to create consistent 16-character GUIDs.
 */
public class GUID
{
    /**
     * Generates a 16-character GUID from a file path.
     * The path is normalized before hashing to ensure consistent results.
     * 
     * @param path The file path to generate a GUID from
     * @return A 16-character hexadecimal GUID, or null if generation fails
     */
    public static String generateGUIDFromPath(String path)
    {
        try
        {
            Path normalizedPath = Paths.get(path).normalize();

            String normalizedPathStr = normalizedPath.toString();

            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hashBytes = digest.digest(normalizedPathStr.getBytes());

            StringBuilder hexString = new StringBuilder();
            for (byte b : hashBytes)
            {
                hexString.append(String.format("%02x", b));
            }

            return hexString.toString().substring(0, 16);
        }
        catch (NoSuchAlgorithmException e)
        {
            e.printStackTrace();
            return null; 
        }
    }
}
