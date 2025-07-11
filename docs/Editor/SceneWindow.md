# SceneWindow Class Documentation

## Function Overview
| Visibility | Function | Description |
|------------|----------|-------------|
| public     | SceneWindow(String name) | Constructs a new SceneWindow with the given name. |
| public     | SceneWindow(String name, Framebuffer fb) | Constructs a new SceneWindow with the given name and framebuffer. |
| public     | setid(int o) | Sets the output texture ID for rendering. |
| public     | setFrameBuffer(Framebuffer fb) | Sets the framebuffer to display. |
| public     | onRender() | Renders the scene window and its framebuffer output. |

## Overview
The `SceneWindow` displays the rendered output of the engine's framebuffer, allowing users to view and interact with the scene in real time.

## Class Definition
```java
package org.PiEngine.Editor;
public class SceneWindow extends EditorWindow
```

## Properties
| Property | Type | Description |
|----------|------|-------------|
| frame | Framebuffer | The framebuffer to display |
| outputTex | int | Output texture ID |
| count | int | Instance counter for window IDs |

## Methods
```java
/**
 * Constructs a new SceneWindow with the given name.
 * @param name The window name
 */
public SceneWindow(String name)

/**
 * Constructs a new SceneWindow with the given name and framebuffer.
 * @param name The window name
 * @param fb The framebuffer to display
 */
public SceneWindow(String name, Framebuffer fb)

/**
 * Sets the output texture ID for rendering.
 * @param o The texture ID
 */
public void setid(int o)

/**
 * Sets the framebuffer to display.
 * @param fb The framebuffer
 */
public void setFrameBuffer(Framebuffer fb)

/**
 * Renders the scene window and its framebuffer output.
 */
@Override
public void onRender()
```

## Example Usage
```java
editor.addWindow(new SceneWindow("Scene"));
```

## Best Practices
- Use for real-time scene visualization
- Set framebuffer before rendering

## See Also
- [EditorWindow](EditorWindow.md)
