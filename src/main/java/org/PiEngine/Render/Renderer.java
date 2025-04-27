package org.PiEngine.Render;

import java.util.*;

import org.PiEngine.Core.Camera;
import org.PiEngine.GameObjects.GameObject;

public class Renderer
{
    private final Map<String, RenderPass> passes = new HashMap<>();
    private final Map<String, Map<Integer, String>> connections = new HashMap<>();
    private String finalPassName = null;

    public void addPass(RenderPass pass)
    {
        passes.put(pass.getName(), pass);
    }

    public void removePass(String passName)
    {
        // Remove the pass itself
        passes.remove(passName);
        
        // Remove all connections to this pass from other passes
        for (Map.Entry<String, Map<Integer, String>> entry : connections.entrySet())
        {
            Map<Integer, String> inputMap = entry.getValue();
            inputMap.entrySet().removeIf(input -> input.getValue().equals(passName));

            // If no more connections exist for this pass, remove the entry
            if (inputMap.isEmpty())
            {
                connections.remove(entry.getKey());
            }
        }

        connections.remove(passName);

        // If the removed pass was the final pass, reset it
        if (passName.equals(finalPassName))
        {
            finalPassName = null;
        }
    }

    public void updatePassName(String oldName, String newName)
    {
        if (passes.containsKey(oldName))
        {
            RenderPass pass = passes.get(oldName);
            
            // Update the name in the passes map
            passes.remove(oldName);
            pass.setName(newName); // Assuming setName is implemented in RenderPass
            passes.put(newName, pass);
            
            // Update the connections to reflect the name change
            if (connections.containsKey(oldName)) {
                Map<Integer, String> inputMap = connections.get(oldName);
                connections.remove(oldName);  // Remove the old connections
        
                // Reassign the connections to the new name
                connections.put(newName, inputMap);
            }
        }
    }

    // Connect a pass to another pass by input index
    public void connect(String fromPassName, String toPassName, int inputIndex)
    {
        // Store the connection: target pass -> input index -> source pass
        connections
            .computeIfAbsent(toPassName, k -> new HashMap<>())
            .put(inputIndex, fromPassName);

    }

    // Disconnect a pass from a specific input index
    public void disconnect(String toPassName, int inputIndex)
    {
        Map<Integer, String> inputMap = connections.get(toPassName);
        if (inputMap != null && inputMap.containsKey(inputIndex))
        {
            //String removed = inputMap.remove(inputIndex);
            inputMap.remove(inputIndex);

            if (inputMap.isEmpty())
            {
                connections.remove(toPassName);
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
            int inputCount = pass.getInputCount();
            Map<Integer, String> inputMap = connections.getOrDefault(pass.getName(), Collections.emptyMap());

            for (int i = 0; i < inputCount; i++)
            {
                String fromPassName = inputMap.get(i);
                int textureId = 0; 

                if (fromPassName != null && passes.containsKey(fromPassName))
                {
                    textureId = passes.get(fromPassName).getOutputTexture();
                }

                pass.setInputTexture(i, textureId);
            }
        }

        for (RenderPass pass : passes.values())
        {
            pass.render(camera, scene);
            pass.unbindFramebuffer();
        }
    }

    public int getFinalTexture()
    {
        if (finalPassName != null && passes.containsKey(finalPassName))
        {
            return passes.get(finalPassName).getOutputTexture();
        }
        return 0;
    }

    public Framebuffer getFinalFramebuffer()
    {
        if (finalPassName != null && passes.containsKey(finalPassName))
        {
            return passes.get(finalPassName).getFramebuffer();
        }
        return null;
    }

    // Getter for all passes in the pipeline
    public Map<String, RenderPass> getPasses()
    {
        return passes;
    }

    // Getter for the connections between passes
    public Map<String, Map<Integer, String>> getConnections()
    {
        return connections;
    }
}
