package org.PiEngine.Component;

import org.PiEngine.Main;
import org.PiEngine.Core.*;
import org.PiEngine.Math.*;
import org.PiEngine.Render.*;

public class RendererComponent extends Component
{
    public boolean Render = true;
    public boolean FlipX = false;
    public boolean FlipY = false;

    public Mesh mesh;
    public float size = 2.5f;

    Shader shader;
    public Vector Color;
    public Texture texture;

    @Override
    public void start()
    {
        Color = new Vector(1, 1, 1);
        updateMesh();
        shader = new Shader(
            Main.ResourceFolder + "Shaders/Camera/Default.vert",
            Main.ResourceFolder + "Shaders/Camera/Sprite.frag",
            null
        );
    }

    @Override
    public void update()
    {
        updateMesh();
    }

    private void updateMesh()
    {
        float x = 0;
        float y = 0;
        float z = 0;
        float h = size * 0.5f;

        // Determine UV coordinates based on FlipX and FlipY
        float u0 = FlipX ? 1f : 0f;
        float u1 = FlipX ? 0f : 1f;
        float v0 = FlipY ? 1f : 0f;
        float v1 = FlipY ? 0f : 1f;

        float[] updatedVertices = {
            x - h, y - h, z,     u0, v0,
            x + h, y - h, z,     u1, v0,
            x + h, y + h, z,     u1, v1,

            x - h, y - h, z,     u0, v0,
            x + h, y + h, z,     u1, v1,
            x - h, y + h, z,     u0, v1
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

    @Override
    public void render(Camera camera)
    {
        if (!Render || texture == null) return;

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

    @Override
    public void onDestroy()
    {
        mesh.dispose();
    }
}
