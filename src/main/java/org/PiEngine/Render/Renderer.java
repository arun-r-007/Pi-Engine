package org.PiEngine.Render;

import org.PiEngine.Core.Camera;
import org.PiEngine.GameObjects.GameObject;
import org.PiEngine.Math.Matrix4;


import static org.lwjgl.opengl.GL30.*;


public class Renderer
{
    private Framebuffer framebuffer;
    private Shader shader;

    public Renderer(int width, int height, Shader shader)
    {
        this.shader = shader;
        this.framebuffer = new Framebuffer(width, height);
    }

    public void render(Camera camera, GameObject scene)
    {
        framebuffer.bind();
        glViewport(0, 0, framebuffer.getWidth(), framebuffer.getHeight());
        glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
        
        shader.use();
        glClearColor(0f, 0f, 0f, 1f);
        Matrix4 viewProj = Matrix4.multiply(camera.getProjectionMatrix(), camera.getViewMatrix());
        shader.setUniformMat4("u_ViewProj", viewProj);

        
        scene.render();

        framebuffer.unbind();
    }

    public int getOutputTexture()
    {
        return framebuffer.getTextureId();
    }
}