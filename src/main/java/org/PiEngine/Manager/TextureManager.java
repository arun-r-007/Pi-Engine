package org.PiEngine.Manager;

import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;

import org.PiEngine.IO.TextureLoader;
import org.PiEngine.Render.Texture;
import org.lwjgl.opengl.GL11;

/**
 * Singleton class that manages texture resources in the Pi-Engine.
 * Handles loading, caching, and providing access to texture assets.
 */
public class TextureManager
{
    /** Singleton instance */
    private static TextureManager instance;

    /** Cache of loaded textures mapped by GUID */
    private final Map<String, Texture> textures = new HashMap<>();

    /** Private constructor for singleton pattern */
    private TextureManager()
    {
    }

    /**
     * Gets or creates the TextureManager instance.
     * @return The singleton TextureManager instance
     */
    public static TextureManager get()
    {
        if (instance == null)
        {
            instance = new TextureManager();
        }
        return instance;
    }

    /**
     * Loads a texture from a file and caches it.
     * @param filePath Path to the texture file
     * @param guid GUID to associate with the texture
     */
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
    }

    /**
     * Gets a texture by its GUID.
     * @param guid GUID of the texture to retrieve
     * @return The texture object, or null if not found
     */
    public Texture getByGUID(String guid)
    {
        return textures.get(guid);
    }
}
