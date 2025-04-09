package org.PiEngine.Render;

import static org.lwjgl.opengl.GL32.*; 

public class Framebuffer
{
    private int fboId;
    private int colorTexture;
    private int depthRenderbuffer;
    private int width, height;

    public Framebuffer(int width, int height)
    {
        this.width = width;
        this.height = height;

        fboId = glGenFramebuffers();
        glBindFramebuffer(GL_FRAMEBUFFER, fboId);

        colorTexture = glGenTextures();
        glBindTexture(GL_TEXTURE_2D, colorTexture);
        glTexImage2D(GL_TEXTURE_2D, 0, GL_RGBA8, width, height, 0, GL_RGBA, GL_UNSIGNED_BYTE, 0);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_NEAREST);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_NEAREST);
        glFramebufferTexture2D(GL_FRAMEBUFFER, GL_COLOR_ATTACHMENT0, GL_TEXTURE_2D, colorTexture, 0);

        depthRenderbuffer = glGenRenderbuffers();
        glBindRenderbuffer(GL_RENDERBUFFER, depthRenderbuffer);
        glRenderbufferStorage(GL_RENDERBUFFER, GL_DEPTH_COMPONENT, width, height);
        glFramebufferRenderbuffer(GL_FRAMEBUFFER, GL_DEPTH_ATTACHMENT, GL_RENDERBUFFER, depthRenderbuffer);

        if (glCheckFramebufferStatus(GL_FRAMEBUFFER) != GL_FRAMEBUFFER_COMPLETE)
        {
            throw new RuntimeException("Framebuffer is incomplete!");
        }

        glBindFramebuffer(GL_FRAMEBUFFER, 0);
    }

    public void bind()
    {
        glBindFramebuffer(GL_FRAMEBUFFER, fboId);
    }

    public void unbind()
    {
        glBindFramebuffer(GL_FRAMEBUFFER, 0);
    }

    public int getTextureId()
    {
        return colorTexture;
    }

    public int getWidth()
    {
        return width;
    }

    public int getHeight()
    {
        return height;
    }
}

