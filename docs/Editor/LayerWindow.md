# LayerWindow Class Documentation

## Function Overview
| Visibility | Function | Description |
|------------|----------|-------------|
| public     | LayerWindow() | Constructs a new LayerWindow and initializes layer names. |
| public     | onRender() | Renders the layer window and allows editing layer names. |

## Overview
The `LayerWindow` provides a UI for managing render layers, allowing users to rename layers and handle layer-related errors.

## Class Definition
```java
package org.PiEngine.Editor;
public class LayerWindow extends EditorWindow
```

## Properties
| Property | Type | Description |
|----------|------|-------------|
| editableNames | ImString[] | Editable names for each layer |
| originalNames | String[] | Original names for each layer |
| showErrorPopup | boolean[] | Error popup flags for each layer |
| count | int | Instance counter for window IDs |

## Methods
```java
/**
 * Constructs a new LayerWindow and initializes layer names.
 */
public LayerWindow()

/**
 * Renders the layer window and allows editing layer names.
 */
@Override
public void onRender()
```

## Example Usage
```java
editor.addWindow(new LayerWindow());
```

## Best Practices
- Use for layer management and renaming
- Handle duplicate names and errors gracefully

## See Also
- [EditorWindow](EditorWindow.md)
