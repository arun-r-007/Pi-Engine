# ComponentPropertyBlock Class Documentation

## Function Overview
| Visibility | Function | Description |
|------------|----------|-------------|
| public     | ComponentPropertyBlock(String label) | Constructs a new ComponentPropertyBlock with the given label. |
| public     | drawComponentFields(Component c) | Draws UI fields for all public attributes of a Component. |

## Overview
The `ComponentPropertyBlock` renders UI controls for editing public fields of a given Component using ImGui. It supports various field types and skips internal references.

## Class Definition
```java
package org.PiEngine.Editor;
public class ComponentPropertyBlock
```

## Properties
| Property | Type | Description |
|----------|------|-------------|
| label | String | The label for the property block |
| fieldTypeMap | Map<Class<?>, Class<?>> | Mapping of field types to field classes |

## Methods
```java
/**
 * Constructs a new ComponentPropertyBlock with the given label.
 * @param label The label for the property block
 */
public ComponentPropertyBlock(String label)

/**
 * Draws UI fields for all public attributes of a Component.
 * Skips internal fields like 'gameObject' or 'transform'.
 * Supports editing float, Vector, and GameObject references.
 * @param c The component to edit
 */
public void drawComponentFields(Component c)
```

## Example Usage
```java
ComponentPropertyBlock block = new ComponentPropertyBlock("Transform");
block.drawComponentFields(component);
```

## Best Practices
- Use for custom component editors
- Extend fieldTypeMap for new field types

## See Also
- [InspectorWindow](InspectorWindow.md)
