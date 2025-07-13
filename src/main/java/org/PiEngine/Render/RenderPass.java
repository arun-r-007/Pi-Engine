package org.PiEngine.Render;


import static org.lwjgl.opengl.GL30.*;

import org.PiEngine.Core.Camera;
import org.PiEngine.Core.Time;
import org.PiEngine.GameObjects.GameObject;
import org.PiEngine.Math.Vector;
import org.lwjgl.opengl.GL11;

public abstract class RenderPass
{
    protected String name;
    protected Shader shader;
    public Framebuffer framebuffer;

    protected int[] inputTextures;
    protected int inputCount; // defined by each subclass

    protected int width, height;
    protected int layerMask = 0xFFFFFFFF;

    /**
     * Constructor for RenderPass.
     * @param name The name of the render pass
     * @param shader The shader program to use
     * @param width The width of the render target
     * @param height The height of the render target
     * @param inputCount The number of input textures
     */
    public RenderPass(String name, Shader shader, int width, int height, int inputCount)
    {
        this.name = name;
        this.shader = shader;
        this.width = width;
        this.height = height;
        this.inputCount = inputCount;
        this.inputTextures = new int[inputCount];

        // Initialize all textures as "unset"
        for (int i = 0; i < inputCount; i++)
        {
            inputTextures[i] = 0;
        }

        this.framebuffer = new Framebuffer(width, height);
    }

    /**
     * Sets an input texture at the specified index.
     * @param index The input index
     * @param textureId The texture ID
     */
    public void setInputTexture(int index, int textureId)
    {
        if (index >= 0 && index < inputCount)
        {
            inputTextures[index] = textureId;
        }
    }

    /**
     * Gets the input texture ID at the specified index.
     * @param index The input index
     * @return The texture ID
     */
    public int getInputTexture(int index)
    {
        if (index >= 0 && index < inputCount)
        {
            return inputTextures[index];
        }
        return 0;
    }

    /**
     * Clears the input texture at the specified index.
     * @param index The input index
     */
    public void clearInputTexture(int index)
    {
        if (index >= 0 && index < inputCount)
        {
            inputTextures[index] = 0;
        }
    }

    /**
     * Gets the number of input textures.
     * @return The input count
     */
    public int getInputCount()
    {
        return inputCount;
    }

    /**
     * Resizes the render target and framebuffer.
     * @param width The new width
     * @param height The new height
     */
    public void resize(int width, int height)
    {
        this.width = width;
        this.height = height;
        framebuffer.resize(width, height);
    }

    /**
     * Sets up the render pass for rendering.
     * Binds framebuffer, sets viewport, clears buffers, and binds input textures.
     */
    public void bindAndPrepare()
    {
        framebuffer.bind();
        glViewport(0, 0, width, height);
        glClearColor(0.0f, 0.0f, 0.0f, 0f);
        glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
        GL11.glEnable(GL11.GL_BLEND);
        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);

        glEnable(GL_DEPTH_TEST);
        

        shader.use();
        shader.setUniformVec2("u_Resolution", new Vector(framebuffer.getWidth(), framebuffer.getHeight(), 0));
        shader.setUniform1f("u_Time", Time.Time);

        for (int i = 0; i < inputCount; i++)
        {
            glActiveTexture(GL_TEXTURE0 + i);
            glBindTexture(GL_TEXTURE_2D, inputTextures[i]);
            shader.setUniform1i("u_Texture" + i, i);
            //System.out.println(name + " Input[" + i +"] :" +  inputTextures[i]);

        }

        for (int i = 0; i < inputCount; i++)
        {
            inputTextures[i] = 0;
        }
    }

    /**
     * Unbinds the framebuffer after rendering.
     */
    public void unbindFramebuffer()
    {
        framebuffer.unbind();
    }

    /**
     * Abstract method to perform the actual rendering.
     * @param camera The camera to render with
     * @param scene The root GameObject to render
     */
    public abstract void render(Camera camera, GameObject scene);

    /**
     * Gets the output texture ID.
     * @return The output texture ID
     */
    public int getOutputTexture()
    {
        return framebuffer.getTextureId();
    }

    /**
     * Gets the framebuffer object.
     * @return The Framebuffer
     */
    public Framebuffer getFramebuffer()
    {
        return framebuffer;
    }

    /**
     * Gets the current layer mask.
     * @return The layer mask
     */
    public int getLayerMask()
    {
        return layerMask;
    }

    /**
     * Sets the layer mask for filtering objects.
     * @param mask The layer mask
     */
    public void setLayerMask(int mask)
    {
        this.layerMask = mask;
    }

    /**
     * Gets the render pass name.
     * @return The name
     */
    public String getName()
    {
        return name;
    }

    /**
     * Sets the render pass name.
     * @param name The new name
     */
    public void setName(String name)
    {
        this.name = name;
    }
}
