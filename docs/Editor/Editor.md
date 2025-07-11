# Editor System Documentation

## Overview
The Editor system provides an ImGui-based editor environment for PiEngine, allowing users to interact with the scene, inspect objects, and manage windows. The Editor is modular, supporting multiple custom windows derived from the EditorWindow base class.

## Function Overview
| Visibility | Function | Description |
|------------|----------|-------------|
| public static | getInstance(long windowPtr, boolean enableMultiViewport) | Returns the singleton Editor instance, initializing if needed |
| public static | getInstance() | Returns the singleton Editor instance |
| public static | get() | Returns the Editor instance, throws if not initialized |
| public | init() | Initializes the ImGui context and editor environment |
| public static | setTheme() | Sets the custom ImGui theme for the editor UI |
| public | destroy() | Destroys the editor and releases resources |
| public | update() | Updates the editor and all windows (per frame) |
| public | addWindow(EditorWindow window) | Adds an EditorWindow to the editor |
| public | removeWindow(EditorWindow window) | Removes an EditorWindow from the editor |
| public | queueAddWindow(EditorWindow window) | Queues an EditorWindow to be added next frame |
| public | queueRemoveWindow(EditorWindow window) | Queues an EditorWindow to be removed next frame |

## Properties
| Property | Type | Description |
|----------|------|-------------|
| instance | Editor | Singleton instance of the editor |
| imguiGlfw | ImGuiImplGlfw | ImGui GLFW backend |
| imguiGl3 | ImGuiImplGl3 | ImGui OpenGL backend |
| glslVersion | String | GLSL version string |
| windowPtr | long | GLFW window pointer |
| enableMultiViewport | boolean | Multi-viewport support flag |
| editorWindows | List<EditorWindow> | List of active editor windows |
| initialized | boolean | Whether the editor is initialized |
| windowsToAdd | List<EditorWindow> | Windows queued for addition |
| windowsToRemove | List<EditorWindow> | Windows queued for removal |

## Methods
```java
/**
 * Returns the singleton instance of the Editor, initializing it if necessary.
 * @param windowPtr The window pointer for ImGui
 * @param enableMultiViewport Whether to enable multi-viewport support
 * @return The Editor instance
 */
public static Editor getInstance(long windowPtr, boolean enableMultiViewport)

/**
 * Returns the singleton instance of the Editor.
 * @return The Editor instance
 */
public static Editor getInstance()

/**
 * Returns the singleton instance of the Editor, throws if not initialized.
 * @return The Editor instance
 * @throws IllegalStateException if Editor is not initialized
 */
public static Editor get()

/**
 * Initializes the ImGui context and sets up the editor environment.
 */
public void init()

/**
 * Sets the custom ImGui theme for the editor UI.
 */
public static void setTheme()

/**
 * Destroys the editor and releases all resources.
 */
public void destroy()

/**
 * Updates the editor and all windows. Should be called once per frame.
 */
public void update()

/**
 * Adds an EditorWindow to the editor.
 * @param window The window to add
 */
public void addWindow(EditorWindow window)

/**
 * Removes an EditorWindow from the editor.
 * @param window The window to remove
 */
public void removeWindow(EditorWindow window)

/**
 * Queues an EditorWindow to be added next frame.
 * @param window The window to add
 */
public void queueAddWindow(EditorWindow window)

/**
 * Queues an EditorWindow to be removed next frame.
 * @param window The window to remove
 */
public void queueRemoveWindow(EditorWindow window)
```

## Example Usage
```java
Editor editor = Editor.getInstance(windowPtr, true);
editor.init();
editor.addWindow(new HierarchyWindow("Hierarchy"));
editor.update();
editor.destroy();
```

## Best Practices
- Always initialize the editor before adding windows or rendering
- Use `EditorWindow` as a base for custom windows
- Queue window additions/removals for thread safety
- Customize the ImGui theme for a consistent look

## See Also
- [EditorWindow](EditorWindow.md)
- [HierarchyWindow](HierarchyWindow.md)
- [InspectorWindow](InspectorWindow.md)
- [Core Package](../Core/README.md)
- [GameObject Package](../GameObject/README.md)
