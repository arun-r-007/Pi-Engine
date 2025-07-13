package org.PiEngine.Render.Passes;

import org.PiEngine.Main;
import org.PiEngine.Core.Camera;
import org.PiEngine.GameObjects.GameObject;
import org.PiEngine.Render.RenderPass;
import org.PiEngine.Render.Shader;

import static org.lwjgl.opengl.GL30.*;

public class GeometryPass extends RenderPass
{
    /**
     * Creates a geometry pass with custom parameters.
     * @param name The pass name
     * @param shader The shader to use
     * @param width The width
     * @param height The height
     */
    public GeometryPass(String name, Shader shader, int width, int height)
    {
        super(name, shader, width, height, 0);
    }

    /**
     * Creates a default geometry pass with standard settings.
     */
    public GeometryPass()
    {
        super("Default GeometryPass", new Shader( Main.ResourceFolder + "Shaders/Camera/Default.vert", Main.ResourceFolder + "Shaders/Camera/Default.frag", null), 1600, 900, 0);
    }

    /**
     * Renders the scene geometry with depth handling.
     * @param camera The camera to render with
     * @param scene The root GameObject to render
     */
    @Override
    public void render(Camera camera, GameObject scene) {
        // Prepare framebuffer and OpenGL state for geometry rendering
        bindAndPrepare();
        // Disable writing to the depth buffer for transparent objects
        glDepthMask(false);
        // Render the scene with layer masking
        scene.render(camera, layerMask);
        // Re-enable writing to the depth buffer
        glDepthMask(true);
        // Unbind the framebuffer to complete the render pass
        framebuffer.unbind();
    }
}
