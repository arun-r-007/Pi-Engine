package org.PiEngine.Utils;

import java.nio.file.*;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class GUID
{
    
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
