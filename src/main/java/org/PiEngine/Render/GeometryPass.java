package org.PiEngine.Render;

import org.PiEngine.Core.Camera;
import org.PiEngine.GameObjects.GameObject;

public class GeometryPass extends RenderPass
{
    public GeometryPass(String name, Shader shader, int width, int height)
    {
        super(name, shader, width, height);
    }

    @Override
    public void render(Camera camera, GameObject scene)
    {
        bindAndPrepare();
        scene.render(camera);
        framebuffer.unbind();
    }
}
