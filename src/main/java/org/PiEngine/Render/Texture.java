package org.PiEngine.Render;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL30;
import org.PiEngine.Utils.*;
import org.lwjgl.BufferUtils;

import java.nio.ByteBuffer;
import java.nio.IntBuffer;

public class Texture implements GUIDProvider
{
    private int textureID;
    private int width;
    private int height;

    private int minFilter;
    private int magFilter;

    private String GUID;

    /**
     * Creates a texture from raw RGBA pixel data with specified dimensions and filtering.
     * @param imageData The RGBA pixel data
     * @param width The texture width
     * @param height The texture height
     * @param minFilter The minification filter
     * @param magFilter The magnification filter
     */
    public Texture(int[] imageData, int width, int height, int minFilter, int magFilter)
    {
        this.width = width;
        this.height = height;
        this.minFilter = minFilter;
        this.magFilter = magFilter;
        this.textureID = createTexture(imageData);
    }

    /**
     * Creates an OpenGL texture from pixel data.
     * @param imageData The RGBA pixel data
     * @return The OpenGL texture ID
     */
    private int createTexture(int[] imageData)
    {
        IntBuffer buffer = BufferUtils.createIntBuffer(1);
        GL11.glGenTextures(buffer);
        int textureID = buffer.get(0);

        GL11.glBindTexture(GL11.GL_TEXTURE_2D, textureID);

        GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MIN_FILTER, minFilter);
        GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MAG_FILTER, magFilter);
        GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_S, GL11.GL_REPEAT);
        GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_T, GL11.GL_REPEAT);

        ByteBuffer imageBuffer = BufferUtils.createByteBuffer(imageData.length * 4);

        for (int i = 0; i < imageData.length; i++)
        {
            int pixel = imageData[i];
            imageBuffer.put((byte) ((pixel >> 16) & 0xFF));
            imageBuffer.put((byte) ((pixel >> 8) & 0xFF));
            imageBuffer.put((byte) ((pixel) & 0xFF));
            imageBuffer.put((byte) ((pixel >> 24) & 0xFF));
        }

        imageBuffer.flip();

        GL11.glTexImage2D(GL11.GL_TEXTURE_2D, 0, GL11.GL_RGBA, width, height, 0, GL11.GL_RGBA, GL11.GL_UNSIGNED_BYTE, imageBuffer);
        GL30.glGenerateMipmap(GL11.GL_TEXTURE_2D);

        GL11.glBindTexture(GL11.GL_TEXTURE_2D, 0);

        return textureID;
    }

    /**
     * Gets the OpenGL texture ID.
     * @return The texture ID
     */
    public int getTextureID()
    {
        return textureID;
    }

    /**
     * Binds the texture for rendering.
     */
    public void bind()
    {
        GL11.glBindTexture(GL11.GL_TEXTURE_2D, textureID);
    }

    /**
     * Unbinds the texture.
     */
    public void unbind()
    {
        GL11.glBindTexture(GL11.GL_TEXTURE_2D, 0);
    }

    /**
     * Gets the texture width.
     * @return The width
     */
    public int getWidth()
    {
        return width;
    }

    /**
     * Gets the texture height.
     * @return The height
     */
    public int getHeight()
    {
        return height;
    }

    /**
     * Deletes the texture and frees GPU memory.
     */
    public void dispose()
    {
        if (textureID != 0)
        {
            GL11.glDeleteTextures(textureID);
            textureID = 0;
        }
    }

    /**
     * Sets the texture's GUID.
     * @param guid The GUID string
     */
    public void setGUID(String guid)
    {
        GUID = guid;

    }

    /**
     * Gets the texture's GUID.
     * @return The GUID string
     */
    @Override
    public String getGUID()
    {
        return GUID;
    }
}
