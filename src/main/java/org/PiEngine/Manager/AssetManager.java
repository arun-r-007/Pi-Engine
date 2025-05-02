package org.PiEngine.Manager;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.concurrent.LinkedBlockingDeque;
import org.PiEngine.Scripting.CompileScripts;
import org.PiEngine.Scripting.ScriptLoader;
import org.PiEngine.Utils.GUID;

enum AssetType
{
    TEXTURE, SHADER, FONT, SCENE, UNKNOWN, JAVA, CLASS;
    
    public static AssetType fromExtension(String ext)
    {
        return switch (ext.toLowerCase())
        {
            case "png" -> TEXTURE;
            case "java" -> JAVA;
            case "frag", "vert", "glsl" -> SHADER;
            case "ttf" -> FONT;
            case "j" -> SCENE;
            case "class" -> CLASS;
            default -> UNKNOWN;
        };
    }
}


public abstract class AssetManager implements Runnable
{
    protected static final Path BASE_PATH = Paths.get("src\\main\\resources").normalize();
    protected static final Map<String, Object> resources = new HashMap<>();
    private static final LinkedBlockingDeque<QueuedAsset> generalAssetQueue = new LinkedBlockingDeque<>();


    private static class QueuedAsset
    {
        final AssetType type;
        final String path;

        QueuedAsset(AssetType type, String path)
        {
            this.type = type;
            this.path = path;
        }
    }
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

                            switch (type)
                            {
                                case TEXTURE -> generalAssetQueue.offer(new QueuedAsset(type, filePath.toString())); 
                                case JAVA -> generalAssetQueue.addFirst(new QueuedAsset(type, filePath.toString()));
                                case CLASS -> {generalAssetQueue.addLast(new QueuedAsset(type, filePath.toString()));}
                                case SHADER -> {} 
                                case FONT -> {} 
                                case SCENE -> {} 
                                case UNKNOWN -> {}
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
        boolean isclass = true;
        while (!generalAssetQueue.isEmpty())
        {
            QueuedAsset asset = generalAssetQueue.poll();
            String guid = GUID.generateGUIDFromPath(asset.path);

            switch (asset.type)
            {
                case TEXTURE ->
                    TextureManager.get().load(Paths.get(asset.path), guid);

                case JAVA ->
                {
                    try
                    {
                        CompileScripts.getInstance().compileScript(new File(asset.path));
                    }
                    catch (Exception e)
                    {
                        System.err.println("Failed to compile: " + asset.path);
                        e.printStackTrace();
                    }
                }

                case CLASS ->
                {
                    isclass = false;
                    ScriptLoader.getInstance().loadComponentScript(new File(asset.path));
                }

                case SHADER -> {}//ShaderManager.get().load(Paths.get(asset.path), guid);

                case FONT -> {}
                    // FontManager.get().load(Paths.get(asset.path), guid);

                case SCENE -> {} 
                    // SceneManager.get().load(Paths.get(asset.path), guid);

                default -> System.out.println("No handler for asset type: " + asset.type);
            }
            
        }
        if (isclass) 
        {    
            ScriptLoader.getInstance().loadComponentFolder(new File("src/main/resources/Compiled"));
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
