package org.PiEngine.Render;

import static org.lwjgl.opengl.GL30.*;

import java.util.ArrayList;
import java.util.List;

import org.PiEngine.Core.Camera;
import org.PiEngine.GameObjects.GameObject;

public abstract class RenderPass
{
    protected Framebuffer framebuffer;
    protected Shader shader;
    protected List<Integer> inputTextures = new ArrayList<>();
    protected int width, height;

    public RenderPass(Shader shader, int width, int height)
    {
        this.shader = shader;
        this.width = width;
        this.height = height;
        this.framebuffer = new Framebuffer(width, height);
    }

    public void addInputTexture(int textureId)
    {
        inputTextures.add(textureId);
    }

    public List<Integer> getInputTextures()
    {
        return inputTextures;
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
        glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);

        shader.use();
        glClearColor(0.05f, 0.05f, 0.05f, 1f);
        for (int i = 0; i < inputTextures.size(); i++)
        {
            glActiveTexture(GL_TEXTURE0 + i);
            glBindTexture(GL_TEXTURE_2D, inputTextures.get(i));
            shader.setUniform1i("u_Texture" + i, i);
        }
        inputTextures.clear();
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

    public void setInputTextures(int... inputTextures)
    {
        if(inputTextures != null)
        {
            this.inputTextures.add(inputTextures[0]);
        }
    }
}
