package org.PiEngine.Render.Passes;


import static org.lwjgl.opengl.GL30.*;

import org.PiEngine.Core.Camera;
import org.PiEngine.GameObjects.GameObject;
import org.PiEngine.Render.RenderPass;
import org.PiEngine.Render.Shader;

public class PostProcessingPass extends RenderPass
{
    private int fullscreenVAO = -1;
    private int fullscreenVBO = -1;

    public PostProcessingPass()
    {
        super("Default Postprocessing", new Shader( "src\\main\\resources\\Shaders\\PostProcess\\SCREEN.vert", "src\\main\\resources\\Shaders\\PostProcess\\CRT.frag", null), 800, 600, 2);
    }

    public PostProcessingPass(String name, Shader shader, int width, int height, int itextures)
    {
        super(name, shader, width, height, itextures);
        setupFullscreenTriangle();
    }

    private void setupFullscreenTriangle()
    {
        float[] triangleVertices = {
            -1.0f, -1.0f,
             3.0f, -1.0f,
            -1.0f,  3.0f
        };

        fullscreenVAO = glGenVertexArrays();
        fullscreenVBO = glGenBuffers();

        glBindVertexArray(fullscreenVAO);
        glBindBuffer(GL_ARRAY_BUFFER, fullscreenVBO);
        glBufferData(GL_ARRAY_BUFFER, triangleVertices, GL_STATIC_DRAW);

        glEnableVertexAttribArray(0);
        glVertexAttribPointer(0, 2, GL_FLOAT, false, 0, 0);

        glBindVertexArray(0);
    }

    @Override
    public void render(Camera camera, GameObject scene)
    {
        bindAndPrepare();
        glBindVertexArray(fullscreenVAO);
        glDrawArrays(GL_TRIANGLES, 0, 3);
        glBindVertexArray(0);

        framebuffer.unbind();
    }

    public void dispose()
    {
        if (fullscreenVAO != -1) glDeleteVertexArrays(fullscreenVAO);
        if (fullscreenVBO != -1) glDeleteBuffers(fullscreenVBO);
    }
}
