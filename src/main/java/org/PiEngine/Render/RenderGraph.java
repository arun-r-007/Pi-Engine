package org.PiEngine.Render;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class RenderGraph
{
    private final Map<String, RenderPass> passes = new HashMap<>();

    public void addPass(RenderPass pass)
    {
        passes.put(pass.getName(), pass);
    }

    public RenderPass getPass(String name)
    {
        return passes.get(name);
    }

    public Collection<RenderPass> getAllPasses()
    {
        return passes.values();
    }
}
