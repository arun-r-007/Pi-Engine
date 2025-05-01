package org.PiEngine.Manager;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.concurrent.LinkedBlockingQueue;

import org.PiEngine.Utils.GUID;

enum AssetType
{
    TEXTURE, SHADER, FONT, SCENE, UNKNOWN;
    
    public static AssetType fromExtension(String ext)
    {
        return switch (ext.toLowerCase())
        {
            case "png" -> TEXTURE;
            case "frag", "vert", "glsl" -> SHADER;
            case "ttf" -> FONT;
            case "json" -> SCENE;
            default -> UNKNOWN;
        };
    }
}



public abstract class AssetManager implements Runnable
{
    protected static final Path BASE_PATH = Paths.get("src\\main\\resources").normalize();
    protected static final Map<String, Object> resources = new HashMap<>();
    private static final LinkedBlockingQueue<String> assetLoadQueue = new LinkedBlockingQueue<>();

    @Override
    public void run()
    {
        try
        {
            Files.walk(BASE_PATH)
                .filter(Files::isRegularFile)
                .forEach(filePath -> {
                    try
                    {
                        String guid = GUID.generateGUIDFromPath(filePath.toString());

                        if (!resources.containsKey(guid))
                        {
                            String ext = getExtension(filePath.getFileName().toString());
                            AssetType type = AssetType.fromExtension(ext);

                            System.out.println(filePath.toString());
                            
                            switch (type)
                            {
                                case TEXTURE -> assetLoadQueue.offer(filePath.toString()); 
                                case SHADER -> {} 
                                case FONT -> {} 
                                case SCENE -> {} 
                                case UNKNOWN -> {
                                    System.out.println("Unknown asset." + ext);
                                }
                            }
                        }
                    }
                    catch (Exception e)
                    {
                        System.err.println("Error loading: " + filePath);
                        e.printStackTrace();
                    }
                });
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    public static void processAssetQueue()
    {
        while (!assetLoadQueue.isEmpty())
        {
            String assetPath = assetLoadQueue.poll();
            String guid = GUID.generateGUIDFromPath(assetPath);
            TextureManager.get().load(Paths.get(assetPath), guid);
        }
    }

    protected String getExtension(String filename)
    {
        int dot = filename.lastIndexOf('.');
        return (dot >= 0) ? filename.substring(dot + 1) : "";
    }

    public static Object get(String guid)
    {
        return resources.get(guid);
    }

    public static void put(String guid, Object asset)
    {
        resources.put(guid, asset);
    }
}
