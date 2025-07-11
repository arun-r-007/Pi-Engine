# EditorWindow Class Documentation

## Function Overview
| Visibility | Function | Description |
|------------|----------|-------------|
| public     | EditorWindow(String name) | Constructs a new EditorWindow with the given name. |
| public     | getName() | Returns the name of the window. |
| public     | isOpen() | Returns whether the window is currently open. |
| public     | open() | Opens the window. |
| public     | close() | Closes the window. |
| public     | toggle() | Toggles the window open/closed state. |
| public     | setPosition(float x, float y) | Sets the window position. |
| public     | setSize(float width, float height) | Sets the window size. |
| public     | onCreate() | Called when the window is created. Override for setup logic. |
| public     | setCustomTheme() | Sets a custom theme for the window. Override to customize appearance. |
| public     | onUpdate() | Called every frame to update window logic. |
| public     | renderBase() | Renders the window base and calls onRender(). |
| public     | onRender() | Called every frame to render window contents. Must be implemented by subclasses. |
| public     | onDestroy() | Called when the window is destroyed. Override for cleanup. |

## Overview
The `EditorWindow` is the base class for all editor windows in PiEngine. It provides common functionality for window management, rendering, and customization.

## Class Definition
```java
package org.PiEngine.Editor;
public abstract class EditorWindow
```

## Properties
| Property | Type | Description |
|----------|------|-------------|
| name | String | The window name |
| isOpen | boolean | Whether the window is open |
| id | int | Window ID |
| x, y | float | Window position |
| width, height | float | Window size |
| useCustomSizeAndPos | boolean | Custom size/position flag |

## Methods
```java
/**
 * Constructs a new EditorWindow with the given name.
 * @param name The window name
 */
public EditorWindow(String name)

/**
 * Returns the name of the window.
 * @return The window name
 */
public String getName()

/**
 * Returns whether the window is currently open.
 * @return True if open, false otherwise
 */
public boolean isOpen()

/**
 * Opens the window.
 */
public void open()

/**
 * Closes the window.
 */
public void close()

/**
 * Toggles the window open/closed state.
 */
public void toggle()

/**
 * Sets the window position.
 * @param x The x position
 * @param y The y position
 */
public void setPosition(float x, float y)

/**
 * Sets the window size.
 * @param width The window width
 * @param height The window height
 */
public void setSize(float width, float height)

/**
 * Called when the window is created. Override for setup logic.
 */
public void onCreate()

/**
 * Sets a custom theme for the window. Override to customize appearance.
 */
public void setCustomTheme()

/**
 * Called every frame to update window logic.
 */
public void onUpdate()

/**
 * Renders the window base and calls onRender().
 */
public void renderBase()

/**
 * Called every frame to render window contents. Must be implemented by subclasses.
 */
public abstract void onRender()

/**
 * Called when the window is destroyed. Override for cleanup.
 */
public void onDestroy()
```

## Example Usage
```java
class MyWindow extends EditorWindow {
    public MyWindow() { super("MyWindow"); }
    @Override public void onRender() { /* ... */ }
}
editor.addWindow(new MyWindow());
```

## Best Practices
- Extend for custom editor tools
- Override onRender for window content

## See Also
- [Editor](Editor.md)
