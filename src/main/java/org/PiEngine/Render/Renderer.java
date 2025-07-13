package org.PiEngine.Render;

import java.util.*;

import org.PiEngine.Core.Camera;
import org.PiEngine.GameObjects.GameObject;

public class Renderer
{
    private final Map<String, RenderPass> passes = new HashMap<>();
    private final Map<String, Map<Integer, String>> connections = new HashMap<>();
    private String finalPassName = null;

    /**
     * Adds a new render pass to the pipeline.
     * @param pass The RenderPass to add
     */
    public void addPass(RenderPass pass)
    {
        passes.put(pass.getName(), pass);
    }

    /**
     * Removes a render pass and its connections.
     * @param passName The name of the pass to remove
     */
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

    /**
     * Updates the name of a render pass.
     * @param oldName The old name
     * @param newName The new name
     */
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

    /**
     * Connects two render passes by input index.
     * @param fromPassName The source pass name
     * @param toPassName The target pass name
     * @param inputIndex The input index to connect
     */
    public void connect(String fromPassName, String toPassName, int inputIndex)
    {
        // Store the connection: target pass -> input index -> source pass
        connections
            .computeIfAbsent(toPassName, k -> new HashMap<>())
            .put(inputIndex, fromPassName);

    }

    /**
     * Disconnects an input from a render pass.
     * @param toPassName The target pass name
     * @param inputIndex The input index to disconnect
     */
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

    /**
     * Sets the final pass in the pipeline.
     * @param name The name of the final pass
     */
    public void setFinalPass(String name)
    {
        finalPassName = name;
    }

    /**
     * Executes the entire rendering pipeline.
     * Sets up input textures and calls render on each pass.
     * @param camera The camera to render with
     * @param scene The root GameObject to render
     */
    public void renderPipeline(Camera camera, GameObject scene)
    {
        // For each pass, set up input textures from connected passes
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

        // For each pass, perform rendering and unbind framebuffer
        for (RenderPass pass : passes.values())
        {
            pass.render(camera, scene);
            pass.unbindFramebuffer();
        }
    }

    /**
     * Gets the texture ID of the final pass output.
     * @return The texture ID
     */
    public int getFinalTexture()
    {
        if (finalPassName != null && passes.containsKey(finalPassName))
        {
            return passes.get(finalPassName).getOutputTexture();
        }
        return 0;
    }

    /**
     * Gets the framebuffer of the final pass.
     * @return The Framebuffer
     */
    public Framebuffer getFinalFramebuffer()
    {
        if (finalPassName != null && passes.containsKey(finalPassName))
        {
            return passes.get(finalPassName).getFramebuffer();
        }
        return null;
    }

    /**
     * Gets all render passes in the pipeline.
     * @return Map of pass names to RenderPass objects
     */
    public Map<String, RenderPass> getPasses()
    {
        return passes;
    }

    /**
     * Gets all connections between passes.
     * @return Map of connections
     */
    public Map<String, Map<Integer, String>> getConnections()
    {
        return connections;
    }
}
