package org.PiEngine.Core;

import java.util.HashMap;
import java.util.Map;

/**
 * Manages layer names and bitmask logic for up to 32 layers.
 */
public class LayerManager
{
    private static final int MAX_LAYERS = 32;
    private static final String[] layerNames = new String[MAX_LAYERS];
    private static final Map<String, Integer> nameToIndex = new HashMap<>();

    static {
        for (int i = 0; i < MAX_LAYERS; i++)
        {
            layerNames[i] = "Layer" + i;
            nameToIndex.put(layerNames[i], i);
        }
    }

    public static int noOfLayers(){return MAX_LAYERS;}

    /**
     * Returns the bitmask of a layer by name (e.g., "Layer2" → 4).
     * @param name The name of the layer
     * @return The bitmask for the layer, or 0 if not found
     */
    public static int getLayerBit(String name)
    {
        Integer index = nameToIndex.get(name);
        if (index == null)
            return 0;
        return 1 << index;
    }

    /**
     * Returns the index of the first bit set in the given layer bitmask.
     * (e.g., 4 → 2)
     * @param bitmask The bitmask to check
     * @return The index of the first bit set, or -1 if invalid
     */
    public static int getIndexFromBitmask(int bitmask)
    {
        for (int i = 0; i < MAX_LAYERS; i++)
        {
            if (((bitmask >> i) & 1) == 1)
                return i;
        }
        return -1; // Invalid bitmask
    }

    /**
     * Returns the name of a layer from a bitmask.
     * @param bitmask The bitmask of the layer
     * @return The name of the layer, or null if not found
     */
    public static String getLayerNameFromBitmask(int bitmask)
    {
        int index = getIndexFromBitmask(bitmask);
        if (index == -1)
            return "Unknown";
        return layerNames[index];
    }

    /**
     * Renames a layer at a given index (0–31).
     * @param index The index of the layer to rename
     * @param newName The new name for the layer
     */
    public static void renameLayer(int index, String newName)
    {
        if (index < 0 || index >= MAX_LAYERS)
            return;

        // Remove old name
        nameToIndex.remove(layerNames[index]);

        // Set new name
        layerNames[index] = newName;
        nameToIndex.put(newName, index);
    }

    /**
     * Get the layer name at index.
     * @param index The index of the layer
     * @return The name of the layer, or "Unknown" if invalid
     */
    public static String getLayerName(int index)
    {
        if (index < 0 || index >= MAX_LAYERS)
            return "Unknown";
        return layerNames[index];
    }

    /**
     * Returns a copy of the layer name → index map.
     * @return A map containing layer names and their corresponding indices
     */
    public static Map<String, Integer> getLayerMap()
    {
        return new HashMap<>(nameToIndex);
    }

    /**
     * Returns an array of layer names.
     * @return An array containing the names of all layers
     */
    public static String[] GetLayerNameArray()
    {
        return layerNames;
    }
}

