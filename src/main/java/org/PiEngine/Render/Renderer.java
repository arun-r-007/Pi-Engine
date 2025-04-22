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

    // Connect a pass to another pass by input index
    public void connect(String fromPassName, String toPassName, int inputIndex)
    {
        // Store the connection: target pass -> input index -> source pass
        connections
            .computeIfAbsent(toPassName, k -> new HashMap<>())
            .put(inputIndex, fromPassName);

        System.out.println("connected: " + fromPassName + " -> " + toPassName + "[" + inputIndex + "]");
    }

    // Disconnect a pass from a specific input index
    public void disconnect(String toPassName, int inputIndex)
    {
        Map<Integer, String> inputMap = connections.get(toPassName);
        if (inputMap != null && inputMap.containsKey(inputIndex))
        {
            String removed = inputMap.remove(inputIndex);
            System.out.println("disconnected: " + removed + " -/> " + toPassName + "[" + inputIndex + "]");

            if (inputMap.isEmpty())
            {
                connections.remove(toPassName);
            }
        }
    }

    // Set the final pass to be rendered at the end of the pipeline
    public void setFinalPass(String name)
    {
        finalPassName = name;
    }

    // Render the entire pipeline by passing the camera and scene
    public void renderPipeline(Camera camera, GameObject scene)
    {
        // First, go through each pass and set the input textures based on connections
        for (RenderPass pass : passes.values())
        {
            int inputCount = pass.getInputCount();
            Map<Integer, String> inputMap = connections.getOrDefault(pass.getName(), Collections.emptyMap());

            // Iterate over all inputs for this pass
            for (int i = 0; i < inputCount; i++)
            {
                // Check if this input index is connected
                String fromPassName = inputMap.get(i);
                int textureId = 0; // Default to null texture (0)

                // If a connection exists, get the texture from the source pass
                if (fromPassName != null && passes.containsKey(fromPassName))
                {
                    textureId = passes.get(fromPassName).getOutputTexture();
                }

                // Set the input texture to the determined value
                pass.setInputTexture(i, textureId);

                // Print the assigned texture for debugging
                // System.out.println("Pass " + pass.getName() + " input " + i + " set to texture ID: " + textureId);
            }
        }

        // Now render all passes in the pipeline
        for (RenderPass pass : passes.values())
        {
            pass.render(camera, scene);
            pass.unbindFramebuffer();
        }
    }

    // Get the final texture from the final pass in the pipeline
    public int getFinalTexture()
    {
        if (finalPassName != null && passes.containsKey(finalPassName))
        {
            return passes.get(finalPassName).getOutputTexture();
        }
        return 0;
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
