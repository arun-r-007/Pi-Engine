package org.PiEngine.Render;

import static org.lwjgl.opengl.GL30.*;

import org.PiEngine.Core.Camera;
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

    public void setInputTexture(int index, int textureId)
    {
        if (index >= 0 && index < inputCount)
        {
            inputTextures[index] = textureId;
        }
    }

    public int getInputTexture(int index)
    {
        if (index >= 0 && index < inputCount)
        {
            return inputTextures[index];
        }
        return 0;
    }

    public void clearInputTexture(int index)
    {
        if (index >= 0 && index < inputCount)
        {
            inputTextures[index] = 0;
        }
    }

    public int getInputCount()
    {
        return inputCount;
    }

    public void resize(int width, int height)
    {
        this.width = width;
        this.height = height;
        framebuffer.resize(width, height);
    }

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

    public void unbindFramebuffer()
    {
        framebuffer.unbind();
    }

    public abstract void render(Camera camera, GameObject scene);

    public int getOutputTexture()
    {
        return framebuffer.getTextureId();
    }

    public Framebuffer getFramebuffer()
    {
        return framebuffer;
    }

    public int getLayerMask()
    {
        return layerMask;
    }

    public void setLayerMask(int mask)
    {
        this.layerMask = mask;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }
}
