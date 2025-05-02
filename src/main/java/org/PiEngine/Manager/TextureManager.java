package org.PiEngine.Manager;

import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;

import org.PiEngine.IO.TextureLoader;
import org.PiEngine.Render.Texture;
import org.lwjgl.opengl.GL11;

public class TextureManager
{
    private static TextureManager instance;

    private final Map<String, Texture> textures = new HashMap<>();

    private TextureManager()
    {
    }

    public static TextureManager get()
    {
        if (instance == null)
        {
            instance = new TextureManager();
        }
        return instance;
    }

    public void load(Path filePath, String guid)
    {
        if (textures.containsKey(guid))
        {
            return;
        }
        Texture texture = TextureLoader.loadTexture(filePath.toString(), GL11.GL_NEAREST, GL11.GL_NEAREST);
        texture.setGUID(guid);
        textures.put(guid, texture);
        AssetManager.put(guid, texture);
        // System.out.println("Loaded texture: " + filePath + " as GUID: " + guid);
    }

    public Texture getByGUID(String guid)
    {
        return textures.get(guid);
    }
}
