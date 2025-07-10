# Window Class Documentation

## Function Overview
| Visibility | Function | Description |
|------------|----------|-------------|
| public static | create() | Creates and initializes the window and OpenGL context. |
| public static | update() | Swaps buffers and polls window events. |
| public static | shouldClose() | Returns true if the window should close. |
| public static | getWindow() | Returns the GLFW window handle. |
| public static | getWidth() | Returns the window width. |
| public static | getHeight() | Returns the window height. |
| public static | destroy() | Destroys the window and terminates GLFW. |

## Overview
The `Window` class manages the creation, updating, and destruction of the main application window and OpenGL context.

## Class Definition
```java
package org.PiEngine.Core;
public class Window
```

## Properties
| Property | Type | Description |
|----------|------|-------------|
| window | long | GLFW window handle |
| width | int | Window width in pixels |
| height | int | Window height in pixels |
| title | String | Window title |

## Methods
```java
/**
 * Creates and initializes the window and OpenGL context.
 */
public static void create()

/**
 * Swaps buffers and polls window events.
 */
public static void update()

/**
 * Returns true if the window should close.
 * @return True if the window should close
 */
public static boolean shouldClose()

/**
 * Returns the GLFW window handle.
 * @return The window handle
 */
public static long getWindow()

/**
 * Returns the window width.
 * @return The window width in pixels
 */
public static int getWidth()

/**
 * Returns the window height.
 * @return The window height in pixels
 */
public static int getHeight()

/**
 * Destroys the window and terminates GLFW.
 */
public static void destroy()
```

## Example Usage
```java
Window.create();
while (!Window.shouldClose()) {
    Window.update();
    // Render here
}
Window.destroy();
```

## Best Practices
- Always call `Window.create()` before any rendering
- Use `Window.update()` once per frame
- Use `shouldClose()` to control the main loop
- Call `Window.destroy()` on exit

## See Also
- [Input](Input.md)
- [Time](Time.md)
