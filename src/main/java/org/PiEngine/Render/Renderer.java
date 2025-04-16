package org.PiEngine.Render;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.HashMap;

import org.PiEngine.Core.Camera;
import org.PiEngine.GameObjects.GameObject;

public class Renderer
{
    private final Map<String, RenderPass> passes = new HashMap<>();
    private final Map<String, List<String>> connections = new HashMap<>(); // passName → inputPassNames
    private String finalPassName = null;

    public void addPass(RenderPass pass)
    {
        passes.put(pass.getName(), pass);
    }

    public void connect(String fromPassName, String toPassName)
    {
        connections.computeIfAbsent(toPassName, k -> new ArrayList<>()).add(fromPassName);
    }

    public void setFinalPass(String name)
    {
        finalPassName = name;
    }

    public void renderPipeline(Camera camera, GameObject scene)
    {
        for (String passName : passes.keySet())
        {
            RenderPass pass = passes.get(passName);
            List<String> inputPasses = connections.get(passName);

            if (inputPasses != null)
            {
                for (String inputName : inputPasses)
                {
                    RenderPass inputPass = passes.get(inputName);
                    if (inputPass != null)
                    {
                        pass.addInputTexture(inputPass.getOutputTexture());
                    }
                }
            }

            pass.render(camera, scene);
        }
    }

    public int getFinalTexture()
    {
        if (finalPassName != null && passes.containsKey(finalPassName))
        {
            return passes.get(finalPassName).getOutputTexture();
        }
        return -1;
    }

    public Map<String, RenderPass> getPasses()
    {
        return passes;
    }

    public Map<String, List<String>> getConnections()
    {
        return connections;
    }
}
