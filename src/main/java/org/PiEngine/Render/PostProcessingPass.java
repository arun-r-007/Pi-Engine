package org.PiEngine.Render;


import static org.lwjgl.opengl.GL30.*;


import org.PiEngine.Core.Camera;
import org.PiEngine.GameObjects.GameObject;

public class PostProcessingPass extends RenderPass
{
    private int[] inputTextures;
    int fullscreenVAO;

    public PostProcessingPass(Shader shader, int width, int height, int... inputTextures)
    {
        super(shader, width, height);
        this.inputTextures = inputTextures;
    }

    @Override
    public void render(Camera camera, GameObject scene)
    {
        bindAndPrepare();

        shader.use();
        for (int i = 0; i < inputTextures.length; i++)
        {
            glActiveTexture(GL_TEXTURE0 + i);
            glBindTexture(GL_TEXTURE_2D, inputTextures[i]);
            shader.setUniform1i("iChannel" + i, i); 
        }

        if (fullscreenVAO == 0)
        {
            fullscreenVAO = glGenVertexArrays(); 
            glBindVertexArray(fullscreenVAO);
            glEnableVertexAttribArray(0);
        }
        else
        {
            glBindVertexArray(fullscreenVAO);
        }

        glBindVertexArray(fullscreenVAO);
        glDrawArrays(GL_TRIANGLES, 0, 3); 


        framebuffer.unbind();
    }
}

