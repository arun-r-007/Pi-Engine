package org.PiEngine.Component;

import org.PiEngine.Main;
import org.PiEngine.Core.*;
import org.PiEngine.Math.*;
import org.PiEngine.Render.*;

public class RendererComponent extends Component
{
    /**
     * RendererComponent handles mesh rendering for a GameObject, including mesh geometry, color, shader, and texture assignment.
     */

    public boolean Render = true;
    public Mesh mesh;
    public Float size = 2.5f;
    Shader shader;
    public Vector Color;
    public Texture texture;

    /**
     * Called once when the component is first added to a GameObject.
     * Initializes color, mesh, and shader for rendering.
     */
    @Override
    public void start()
    {
        Color = new Vector(1, 1, 1);
        updateMesh();
        shader = new Shader
        (
            Main.ResourceFolder +"Shaders/Camera/Default.vert",
            Main.ResourceFolder + "Shaders/Camera/Sprite.frag",
            null
        );

        // texture = TextureLoader.loadTexture("src\\main\\resources\\Sprites\\Box.png", GL11.GL_NEAREST, GL11.GL_NEAREST);
    }

    /**
     * Called every frame. Updates the mesh geometry if needed.
     */
    @Override
    public void update()
    {
        updateMesh();
    }

    /**
     * Updates the mesh geometry based on the current size.
     */
    private void updateMesh()
    {
        float x = 0;
        float y = 0;
        float z = 0;
        float h = size * 0.5f;

        float[] updatedVertices = {
            x - h, y - h, z,     0f, 0f,
            x + h, y - h, z,     1f, 0f,
            x + h, y + h, z,     1f, 1f,

            x - h, y - h, z,     0f, 0f,
            x + h, y + h, z,     1f, 1f,
            x - h, y + h, z,     0f, 1f 
        };

        if (mesh == null)
        {
            mesh = new Mesh(updatedVertices);
        }
        else
        {
            mesh.updateVertices(updatedVertices);
        }
    }

    /**
     * Renders the mesh using the assigned shader and texture.
     * @param camera The camera to use for rendering
     */
    @Override
    public void render(Camera camera)
    {
        if(!Render) return;
        if(texture == null) return;
        texture.bind();
        shader.use();
        Matrix4 viewProj = Matrix4.multiply(camera.getProjectionMatrix(), camera.getViewMatrix());
        Matrix4 modelMatrix = transform.getWorldMatrix();
        shader.setUniformMat4("u_ViewProj", viewProj, false);
        shader.setUniformMat4("u_ModelMatrix", modelMatrix, false);
        shader.setUniformVec3("u_Color", Color);
        shader.setUniform1f("u_Time", Time.Time);
        shader.setUniform1f("u_Texture", texture.getTextureID());

        mesh.render();
    }

    /**
     * Called once before the component is removed or the GameObject is destroyed.
     * Disposes of the mesh resources.
     */
    @Override
    public void onDestroy()
    {
        mesh.dispose();
    }
}
