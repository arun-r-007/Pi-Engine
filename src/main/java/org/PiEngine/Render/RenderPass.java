package org.PiEngine.Render;

import static org.lwjgl.opengl.GL30.*;

import java.util.ArrayList;
import java.util.List;

import org.PiEngine.Core.*;
import org.PiEngine.GameObjects.*;
import org.PiEngine.Math.*;


public class RenderPass
{
    private Framebuffer framebuffer;
    private Shader shader;
    private List<Integer> inputTextures = new ArrayList<>();
    private int width, height;

    public RenderPass(Shader shader, int width, int height)
    {
        this.shader = shader;
        this.width = width;
        this.height = height;
        this.framebuffer = new Framebuffer(width, height); // May support MRTs later
    }

    public void addInputTexture(int textureId)
    {
        inputTextures.add(textureId);
    }

    public void resize(int width, int height)
    {
        this.width = width;
        this.height = height;
        framebuffer.resize(width, height);
    }

    public void render(Camera camera, GameObject scene)
    {
        framebuffer.bind();
        glViewport(0, 0, width, height);
        glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);

        shader.use();
        Matrix4 viewProj = Matrix4.multiply(camera.getProjectionMatrix(), camera.getViewMatrix());
        shader.setUniformMat4("u_ViewProj", viewProj);

        // Bind input textures
        for (int i = 0; i < inputTextures.size(); i++)
        {
            glActiveTexture(GL_TEXTURE0 + i);
            glBindTexture(GL_TEXTURE_2D, inputTextures.get(i));
            shader.setUniform1i("u_Texture" + i, i); 
        }

        scene.render(camera);

        framebuffer.unbind();
    }

    public int getOutputTexture()
    {
        return framebuffer.getTextureId();
    }

    public Framebuffer getFramebuffer()
    {
        return framebuffer;
    }
}

