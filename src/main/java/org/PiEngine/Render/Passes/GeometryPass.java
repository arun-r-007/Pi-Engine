package org.PiEngine.Render.Passes;

import org.PiEngine.Core.Camera;
import org.PiEngine.GameObjects.GameObject;
import org.PiEngine.Render.RenderPass;
import org.PiEngine.Render.Shader;

import static org.lwjgl.opengl.GL30.*;

public class GeometryPass extends RenderPass
{
    public GeometryPass(String name, Shader shader, int width, int height)
    {
        super(name, shader, width, height, 0);
    }

    public GeometryPass()
    {
        super("Default GeometryPass", new Shader( "src\\main\\resources\\Shaders\\Camera\\Default.vert", "src\\main\\resources\\Shaders\\Camera\\Default.frag", null), 800, 600, 0);
    }

    @Override
    public void render(Camera camera, GameObject scene)
    {
        bindAndPrepare();
        glDepthMask(false);
        scene.render(camera, layerMask);
        glDepthMask(true);
        framebuffer.unbind();
    }
}
