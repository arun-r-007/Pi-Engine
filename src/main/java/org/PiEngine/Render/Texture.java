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

    public Texture(int[] imageData, int width, int height, int minFilter, int magFilter)
    {
        this.width = width;
        this.height = height;
        this.minFilter = minFilter;
        this.magFilter = magFilter;
        this.textureID = createTexture(imageData);
    }

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

    
    public int getTextureID()
    {
        return textureID;
    }

    public void bind()
    {
        GL11.glBindTexture(GL11.GL_TEXTURE_2D, textureID);
    }

    public void unbind()
    {
        GL11.glBindTexture(GL11.GL_TEXTURE_2D, 0);
    }

    public int getWidth()
    {
        return width;
    }

    public int getHeight()
    {
        return height;
    }

    public void dispose()
    {
        if (textureID != 0)
        {
            GL11.glDeleteTextures(textureID);
            textureID = 0;
        }
    }

    public void setGUID(String guid)
    {
        GUID = guid;

    }

    @Override
    public String getGUID() 
    {
        return GUID;
    }
}
