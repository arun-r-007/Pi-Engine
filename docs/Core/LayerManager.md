# LayerManager Class Documentation

## Function Overview
| Visibility | Function | Description |
|------------|----------|-------------|
| public static | getLayerBit(String name) | Returns the bitmask of a layer by name. |
| public static | getIndexFromBitmask(int bitmask) | Returns the index of the first bit set in the given layer bitmask. |
| public static | getLayerNameFromBitmask(int bitmask) | Returns the name of a layer from a bitmask. |
| public static | renameLayer(int index, String newName) | Renames a layer at a given index. |
| public static | getLayerName(int index) | Get the layer name at index. |
| public static | getLayerMap() | Returns a copy of the layer name → index map. |
| public static | GetLayerNameArray() | Returns the array of layer names. |
| public static | noOfLayers() | Returns the maximum number of layers. |

## Overview
The `LayerManager` class manages layer names and bitmask logic for up to 32 layers. Useful for filtering rendering and organizing objects by layer.

## Class Definition
```java
package org.PiEngine.Core;
public class LayerManager
```

## Properties
| Property | Type | Description |
|----------|------|-------------|
| MAX_LAYERS | int | Maximum number of layers (32) |
| layerNames | String[] | Array of layer names |
| nameToIndex | Map<String, Integer> | Map from layer name to index |

## Methods
```java
/**
 * Returns the bitmask of a layer by name (e.g., "Layer2" → 4).
 * @param name The layer name
 * @return The bitmask for the layer
 */
public static int getLayerBit(String name)

/**
 * Returns the index of the first bit set in the given layer bitmask.
 * @param bitmask The layer bitmask
 * @return The index of the first set bit, or -1 if invalid
 */
public static int getIndexFromBitmask(int bitmask)

/**
 * Returns the name of a layer from a bitmask.
 * @param bitmask The layer bitmask
 * @return The layer name, or "Unknown" if not found
 */
public static String getLayerNameFromBitmask(int bitmask)

/**
 * Renames a layer at a given index (0–31).
 * @param index The layer index
 * @param newName The new name for the layer
 */
public static void renameLayer(int index, String newName)

/**
 * Get the layer name at index.
 * @param index The layer index
 * @return The layer name, or "Unknown" if out of range
 */
public static String getLayerName(int index)

/**
 * Returns a copy of the layer name → index map.
 * @return Map of layer names to indices
 */
public static Map<String, Integer> getLayerMap()

/**
 * Returns the array of layer names.
 * @return Array of layer names
 */
public static String[] GetLayerNameArray()

/**
 * Returns the maximum number of layers.
 * @return The number of layers
 */
public static int noOfLayers()
```

## Example Usage
```java
int playerLayer = LayerManager.getLayerBit("Layer1");
String name = LayerManager.getLayerNameFromBitmask(playerLayer);
LayerManager.renameLayer(2, "Enemies");
```

## Best Practices
- Use bitmasks for efficient layer filtering
- Use `renameLayer` to customize layer names for your project
- Use `getLayerMap` to display all layers in an editor

## See Also
- [Camera](Camera.md)
