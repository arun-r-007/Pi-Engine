package org.PiEngine.Render;

import org.PiEngine.Core.Camera;
import org.PiEngine.GameObjects.GameObject;
import org.PiEngine.Math.Matrix4;

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

        Matrix4 viewProj = Matrix4.multiply(camera.getProjectionMatrix(), camera.getViewMatrix());
        shader.setUniformMat4("u_ViewProj", viewProj);

        scene.render(camera);

        framebuffer.unbind();
    }
}
