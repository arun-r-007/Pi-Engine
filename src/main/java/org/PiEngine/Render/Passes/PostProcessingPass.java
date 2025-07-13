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

    /**
     * Creates a default post-processing pass with CRT effect shader.
     */
    public PostProcessingPass()
    {
        super("Default Postprocessing", new Shader( "src\\main\\resources\\Shaders\\PostProcess\\SCREEN.vert", "src\\main\\resources\\Shaders\\PostProcess\\CRT.frag", null), 800, 600, 2);
    }

    /**
     * Creates a custom post-processing pass.
     * @param name The pass name
     * @param shader The shader to use
     * @param width The width
     * @param height The height
     * @param itextures Number of input textures
     */
    public PostProcessingPass(String name, Shader shader, int width, int height, int itextures)
    {
        super(name, shader, width, height, itextures);
        setupFullscreenTriangle();
    }

    /**
     * Sets up the VAO/VBO for the fullscreen triangle.
     */
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

    /**
     * Renders the post-processing effect using a fullscreen triangle.
     * @param camera The camera to render with
     * @param scene The root GameObject to render
     */
    @Override
    public void render(Camera camera, GameObject scene)
    {   
        // Prepare framebuffer and OpenGL state for post-processing
        bindAndPrepare();
        // Draw fullscreen triangle for screen-space effect
        glBindVertexArray(fullscreenVAO);
        glDrawArrays(GL_TRIANGLES, 0, 3);
        glBindVertexArray(0);
        // Unbind framebuffer
        framebuffer.unbind();
    }

    /**
     * Cleans up OpenGL resources.
     */
    public void dispose()
    {
        if (fullscreenVAO != -1) glDeleteVertexArrays(fullscreenVAO);
        if (fullscreenVBO != -1) glDeleteBuffers(fullscreenVBO);
    }
}
