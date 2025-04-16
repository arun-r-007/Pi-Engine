package org.PiEngine.Render;

import static org.lwjgl.opengl.GL30.*;

import org.PiEngine.Core.Camera;
import org.PiEngine.GameObjects.GameObject;

public class PostProcessingPass extends RenderPass
{
    private int fullscreenVAO = -1;
    private int fullscreenVBO = -1;

    public PostProcessingPass(String name, Shader shader, int width, int height)
    {
        super(name, shader, width, height);
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

        shader.use();

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
