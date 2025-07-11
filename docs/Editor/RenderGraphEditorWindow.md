# RenderGraphEditorWindow Class Documentation

## Function Overview
| Visibility | Function | Description |
|------------|----------|-------------|
| public     | RenderGraphEditorWindow(Renderer renderer) | Constructs a new RenderGraphEditorWindow for the given renderer. |
| public     | onCreate() | Called when the window is created. Sets up ImNodes context. |
| public     | onRender() | Renders the render graph editor window and its nodes. |

## Overview
The `RenderGraphEditorWindow` provides a node-based editor for managing render passes and their connections, using ImNodes for interactive graph editing.

## Class Definition
```java
package org.PiEngine.Editor;
public class RenderGraphEditorWindow extends EditorWindow
```

## Properties
| Property | Type | Description |
|----------|------|-------------|
| renderer | Renderer | The renderer being edited |
| nodeIds | Map<String, Integer> | Node ID map for graph nodes |
| inputPinIds | Map<String, List<Integer>> | Input pin IDs for nodes |
| outputPinIds | Map<String, Integer> | Output pin IDs for nodes |
| linkMap | Map<Integer, LinkInfo> | Map of graph links |
| nextId | int | Next available node ID |
| count | int | Instance counter for window IDs |

## Methods
```java
/**
 * Constructs a new RenderGraphEditorWindow for the given renderer.
 * @param renderer The renderer to edit
 */
public RenderGraphEditorWindow(Renderer renderer)

/**
 * Called when the window is created. Sets up ImNodes context.
 */
@Override
public void onCreate()

/**
 * Renders the render graph editor window and its nodes.
 */
@Override
public void onRender()
```

## Example Usage
```java
editor.addWindow(new RenderGraphEditorWindow(renderer));
```

## Best Practices
- Use for node-based render pass editing
- Register passes before creating the window

## See Also
- [EditorWindow](EditorWindow.md)
- [RendererInspector](RendererInspector.md)
