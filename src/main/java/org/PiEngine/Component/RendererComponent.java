package org.PiEngine.Component;

import org.PiEngine.Math.Vector;
import org.PiEngine.Render.Mesh;

public class RendererComponent extends Component
{
    public Mesh mesh;
    public float size = 0.5f;

    @Override
    public void start()
    {
        updateMesh();
    }

    @Override
    public void update()
    {
        updateMesh(); // Optional: comment this out if mesh doesn't change every frame
    }

    private void updateMesh()
    {
        Vector pos = gameObject.transform.getWorldPosition();
        float x = pos.x;
        float y = pos.y;
        float z = pos.z;
        float h = size * 0.5f;

        float[] updatedVertices = {
            // Position         // UV
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

    @Override
    public void render()
    {
        mesh.render();
    }

    @Override
    public void onDestroy()
    {
        mesh.dispose();
    }
}
