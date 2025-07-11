# RendererInspector Class Documentation

## Function Overview
| Visibility | Function | Description |
|------------|----------|-------------|
| public     | RendererInspector(Renderer renderer) | Constructs a new RendererInspector for the given renderer. |
| public     | onRender() | Renders the renderer inspector window and its passes. |

## Overview
The `RendererInspector` provides a UI for inspecting and managing render passes, allowing users to view, rename, add, and remove passes in the engine's renderer.

## Class Definition
```java
package org.PiEngine.Editor;
public class RendererInspector extends EditorWindow
```

## Properties
| Property | Type | Description |
|----------|------|-------------|
| renderer | Renderer | The renderer being inspected |
| searchBuffer | ImString | Buffer for pass search input |
| RenamePass | String[] | Buffer for renaming passes |
| passToRename | RenderPass | The pass being renamed |
| rename | boolean | Rename mode flag |
| count | int | Instance counter for window IDs |

## Methods
```java
/**
 * Constructs a new RendererInspector for the given renderer.
 * @param renderer The renderer to inspect
 */
public RendererInspector(Renderer renderer)

/**
 * Renders the renderer inspector window and its passes.
 */
@Override
public void onRender()
```

## Example Usage
```java
editor.addWindow(new RendererInspector(renderer));
```

## Best Practices
- Use for render pass management and debugging
- Support renaming and removal of passes

## See Also
- [EditorWindow](EditorWindow.md)
