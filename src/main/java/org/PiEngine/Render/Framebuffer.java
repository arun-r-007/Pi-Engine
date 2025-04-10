package org.PiEngine.Render;

import static org.lwjgl.opengl.GL32.*;

/**
 * Represents an OpenGL framebuffer with a color texture and a depth renderbuffer.
 * Used for offscreen rendering and post-processing effects.
 */
public class Framebuffer
{
    private int fboId;
    private int colorTexture;
    private int depthRenderbuffer;
    private int width, height;

    /**
     * Creates a new framebuffer with the given width and height.
     *
     * @param width  The width of the framebuffer.
     * @param height The height of the framebuffer.
     */
    public Framebuffer(int width, int height)
    {
        this.width = width;
        this.height = height;
        createFramebuffer();
    }

    /**
     * Binds the framebuffer so that rendering is directed to it.
     */
    public void bind()
    {
        glBindFramebuffer(GL_FRAMEBUFFER, fboId);
    }

    /**
     * Unbinds the framebuffer, restoring default framebuffer (screen).
     */
    public void unbind()
    {
        glBindFramebuffer(GL_FRAMEBUFFER, 0);
    }

    /**
     * Gets the OpenGL texture ID for the color attachment.
     *
     * @return The texture ID.
     */
    public int getTextureId()
    {
        return colorTexture;
    }

    /**
     * Returns the current width of the framebuffer.
     *
     * @return Width in pixels.
     */
    public int getWidth()
    {
        return width;
    }

    /**
     * Returns the current height of the framebuffer.
     *
     * @return Height in pixels.
     */
    public int getHeight()
    {
        return height;
    }

    /**
     * Resizes the framebuffer and its attachments to the new width and height.
     *
     * @param newWidth  The new width in pixels.
     * @param newHeight The new height in pixels.
     */
    public void resize(int newWidth, int newHeight)
    {
        if (newWidth == width && newHeight == height) return;

        this.width = newWidth;
        this.height = newHeight;

        // Delete old attachments
        glDeleteTextures(colorTexture);
        glDeleteRenderbuffers(depthRenderbuffer);

        // Recreate with new dimensions
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
            throw new RuntimeException("Resized framebuffer is incomplete!");
        }

        glBindFramebuffer(GL_FRAMEBUFFER, 0);
    }

    /**
     * Creates the OpenGL framebuffer and its attachments.
     */
    private void createFramebuffer()
    {
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

    /**
 * Disposes of all OpenGL resources used by this framebuffer.
 */
public void dispose()
{
    glDeleteFramebuffers(fboId);
    glDeleteTextures(colorTexture);
    glDeleteRenderbuffers(depthRenderbuffer);
}

}
