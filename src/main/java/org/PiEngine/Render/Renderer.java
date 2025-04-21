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

    public void disconnect(String fromPass, String toPass)
    {
        List<String> inputs = connections.get(toPass);
        if (inputs != null)
        {
            inputs.remove(fromPass);
            if (inputs.isEmpty())
            {
                connections.remove(toPass);
            }
        }
    }


    public void setFinalPass(String name)
    {
        finalPassName = name;
    }

    public void renderPipeline(Camera camera, GameObject scene)
    {
        for (RenderPass pass : passes.values())
        {
            pass.setInputTextures();
        }

        for (RenderPass pass : passes.values())
        {
            List<String> inputPassNames = connections.get(pass.getName());

            if (inputPassNames == null || inputPassNames.isEmpty())
            {
                // No connections = no input → push invalid texture ID
                pass.addInputTexture(-1);
            }
            else
            {
                for (String fromPassName : inputPassNames)
                {
                    RenderPass fromPass = passes.get(fromPassName);
                    if (fromPass != null)
                    {
                        pass.addInputTexture(fromPass.getOutputTexture());
                    }
                }
            }
        }

        for (RenderPass pass : passes.values())
        {
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
