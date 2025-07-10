# Input Class Documentation

## Function Overview
| Visibility | Function | Description |
|------------|----------|-------------|
| public static | init(long windowHandle) | Initializes the input system with the window handle. |
| public static | update() | Updates input states. Must be called once per frame. |
| public static | isKeyDown(int key) | Returns true while the key is held down. |
| public static | isKeyPressed(int key) | Returns true only on the frame the key was pressed. |
| public static | isKeyReleased(int key) | Returns true only on the frame the key was released. |
| public static | isMouseDown(int button) | Returns true while the mouse button is held down. |
| public static | isMousePressed(int button) | Returns true only on the frame the mouse button was pressed. |
| public static | isMouseReleased(int button) | Returns true only on the frame the mouse button was released. |
| public static | getMouseX() | Gets current mouse X position. |
| public static | getMouseY() | Gets current mouse Y position. |

## Overview
The `Input` class provides static methods for keyboard and mouse input handling. It tracks key/button states and mouse position, and must be updated every frame.

## Class Definition
```java
package org.PiEngine.Core;
public class Input
```

## Properties
| Property | Type | Description |
|----------|------|-------------|
| window | long | GLFW window handle |
| MAX_KEYS | int | Maximum number of keys tracked |
| MAX_BUTTONS | int | Maximum number of mouse buttons tracked |
| keys, keysLast | boolean[] | Key state arrays |
| buttons, buttonsLast | boolean[] | Mouse button state arrays |

## Methods
```java
/**
 * Initializes the input system with the window handle.
 * @param windowHandle The GLFW window handle
 */
public static void init(long windowHandle)

/**
 * Updates input states. Must be called once per frame.
 */
public static void update()

/**
 * Returns true while the key is held down.
 * @param key The GLFW key code
 * @return True if the key is down
 */
public static boolean isKeyDown(int key)

/**
 * Returns true only on the frame the key was pressed.
 * @param key The GLFW key code
 * @return True if the key was pressed this frame
 */
public static boolean isKeyPressed(int key)

/**
 * Returns true only on the frame the key was released.
 * @param key The GLFW key code
 * @return True if the key was released this frame
 */
public static boolean isKeyReleased(int key)

/**
 * Returns true while the mouse button is held down.
 * @param button The GLFW mouse button code
 * @return True if the button is down
 */
public static boolean isMouseDown(int button)

/**
 * Returns true only on the frame the mouse button was pressed.
 * @param button The GLFW mouse button code
 * @return True if the button was pressed this frame
 */
public static boolean isMousePressed(int button)

/**
 * Returns true only on the frame the mouse button was released.
 * @param button The GLFW mouse button code
 * @return True if the button was released this frame
 */
public static boolean isMouseReleased(int button)

/**
 * Gets current mouse X position.
 * @return Mouse X position in window coordinates
 */
public static float getMouseX()

/**
 * Gets current mouse Y position.
 * @return Mouse Y position in window coordinates
 */
public static float getMouseY()
```

## Example Usage
```java
Input.init(window);
while (!Window.shouldClose()) {
    Input.update();
    if (Input.isKeyPressed(GLFW_KEY_SPACE)) {
        // Space was pressed this frame
    }
    float mouseX = Input.getMouseX();
    float mouseY = Input.getMouseY();
}
```

## Best Practices
- Always call `Input.update()` once per frame before checking input
- Use `isKeyPressed`/`isKeyReleased` for single-frame events
- Use `isKeyDown` for continuous input
- Use `getMouseX`/`getMouseY` for mouse position

## See Also
- [Window](Window.md)
