package org.PiEngine.Render;

import java.util.ArrayList;
import java.util.List;

import org.PiEngine.Core.Camera;
import org.PiEngine.GameObjects.GameObject;

public class Renderer
{
    private final List<RenderPass> passes = new ArrayList<>();
    private int finalTextureId = -1;


    public void addPass(RenderPass pass)
    {
        passes.add(pass);
    }

    public void renderPipeline(Camera camera, GameObject scene)
    {
        int[] previousOutputs = null;

        for (RenderPass pass : passes)
        {
            pass.setInputTextures(previousOutputs);
            pass.render(camera, scene);        
            previousOutputs = new int[] { pass.getOutputTexture() };
            //System.err.println(pass.getOutputTexture() + " " + pass);
        }

        if (!passes.isEmpty())
        {
            finalTextureId = passes.get(passes.size() - 1).getOutputTexture();
        }
    }


    public int getFinalTexture()
    {
        return finalTextureId;
    }
}
