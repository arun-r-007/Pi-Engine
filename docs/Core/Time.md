# Time Class Documentation

## Function Overview
| Visibility | Function | Description |
|------------|----------|-------------|
| public static | init() | Initializes the time system. |
| public static | update() | Updates all time-related values. |
| public static | getDeltaHistory() | Returns the frame delta time history for plotting graphs. |
| public static | getHistorySize() | Gets the size of the delta history buffer. |
| public static | consumeFixedDeltaTime() | Consumes one fixed timestep from the accumulator. |
| public static | shouldRunFixedUpdate() | Checks whether enough time has accumulated to process a fixed update. |

## Overview
The `Time` class provides a centralized system for tracking time, including delta time, fixed timestep updates, time scaling, and frame timing history.

## Class Definition
```java
package org.PiEngine.Core;
public class Time
```

## Properties
| Property | Type | Description |
|----------|------|-------------|
| deltaTime | float | Scaled delta time (affected by timeScale) |
| unscaledDeltaTime | float | Unscaled real-time delta time between frames |
| fixedDeltaTime | float | Fixed delta time used for fixed updates |
| timeScale | float | Time scale multiplier |
| fixedTime | float | Accumulated time since the last fixed update |
| Time | float | Accumulated real time since start |
| MAX_HISTORY | int | Max number of frames to store in history |
| deltaHistory | float[] | Circular buffer storing previous frame delta times |
| historyIndex | int | Index of the current frame in the circular buffer |

## Methods
```java
/**
 * Initializes the time system. Should be called once before the game loop starts.
 */
public static void init()

/**
 * Updates all time-related values. Should be called once per frame.
 */
public static void update()

/**
 * Returns the frame delta time history for plotting graphs.
 * @return A float array of unscaled delta times in order.
 */
public static float[] getDeltaHistory()

/**
 * Gets the size of the delta history buffer.
 * @return Number of frames tracked in history.
 */
public static int getHistorySize()

/**
 * Consumes one fixed timestep from the accumulator. Should be called after processing a fixedUpdate step.
 */
public static void consumeFixedDeltaTime()

/**
 * Checks whether enough time has accumulated to process a fixed update.
 * @return True if a fixedUpdate step should run this frame.
 */
public static boolean shouldRunFixedUpdate()
```

## Example Usage
```java
Time.init();
while (!Window.shouldClose()) {
    Time.update();
    if (Time.shouldRunFixedUpdate()) {
        // Run physics or fixed update
        Time.consumeFixedDeltaTime();
    }
    float dt = Time.deltaTime;
}
```

## Best Practices
- Always call `Time.update()` once per frame
- Use `shouldRunFixedUpdate` and `consumeFixedDeltaTime` for physics
- Use `deltaTime` for frame-based logic, `fixedDeltaTime` for physics
- Use `getDeltaHistory` for performance graphs

## See Also
- [Window](Window.md)
