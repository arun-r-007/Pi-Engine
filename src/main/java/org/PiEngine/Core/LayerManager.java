package org.PiEngine.Core;

import java.util.HashMap;
import java.util.Map;

/**
 * Manages layer names and bitmask logic for up to 32 layers.
 */
public class LayerManager
{
    private static final int MAX_LAYERS = 31;
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
     */
    public static String getLayerName(int index)
    {
        if (index < 0 || index >= MAX_LAYERS)
            return "Unknown";
        return layerNames[index];
    }

    /**
     * Returns a copy of the layer name → index map.
     */
    public static Map<String, Integer> getLayerMap()
    {
        return new HashMap<>(nameToIndex);
    }

    public static String[] GetLayerNameArray()
    {
        return layerNames;
    }
}

