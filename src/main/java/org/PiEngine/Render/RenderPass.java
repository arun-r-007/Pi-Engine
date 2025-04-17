package org.PiEngine.Render;

import static org.lwjgl.opengl.GL30.*;

import java.util.ArrayList;
import java.util.List;

import org.PiEngine.Core.Camera;
import org.PiEngine.GameObjects.GameObject;
import org.PiEngine.Math.Vector;

public abstract class RenderPass
{
    protected String name;
    protected Shader shader;
    protected Framebuffer framebuffer;

    protected List<Integer> inputTextures = new ArrayList<>();
    protected int width, height;

    protected int layerMask = 0xFFFFFFFF; // Default: all layers enabled

    public RenderPass(String name, Shader shader, int width, int height)
    {
        this.name = name;
        this.shader = shader;
        this.width = width;
        this.height = height;
        this.framebuffer = new Framebuffer(width, height);
    }

    // Add single input texture
    public void addInputTexture(int textureId)
    {
        inputTextures.add(textureId);
    }

    // Add multiple input textures
    public void setInputTextures(int... textures)
    {
        inputTextures.clear();
        if (textures != null)
        {
            for (int tex : textures)
            {
                inputTextures.add(tex);
            }
        }
    }

    // Returns input textures (for debugging or graph use)
    public List<Integer> getInputTextures()
    {
        return inputTextures;
    }

    // Resize framebuffer and internal state
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
        glClearColor(0.0f, 0.0f, 0.0f, 1f); // full black clear
        glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);

        shader.use();

        // Bind inputs
        for (int i = 0; i < inputTextures.size(); i++)
        {
            glActiveTexture(GL_TEXTURE0 + i);
            glBindTexture(GL_TEXTURE_2D, inputTextures.get(i));
            shader.setUniform1i("u_Texture" + i, i);
        }

        shader.setUniformVec2("u_Resolution", new Vector(width, height, 0));
    }


    // Render the pass: subclasses must implement logic
    public abstract void render(Camera camera, GameObject scene);

    // Output texture from this pass
    public int getOutputTexture()
    {
        return framebuffer.getTextureId();
    }

    public Framebuffer getFramebuffer()
    {
        return framebuffer;
    }

    // Layer mask getter/setter
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
