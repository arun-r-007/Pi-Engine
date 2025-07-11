# PerfomanceWindow Class Documentation

## Function Overview
| Visibility | Function | Description |
|------------|----------|-------------|
| public     | PerfomanceWindow() | Constructs a new PerfomanceWindow. |
| public     | PerfomanceWindow(String Name) | Constructs a new PerfomanceWindow with a custom name. |
| public     | onRender() | Renders the performance window, showing FPS and memory usage. |

## Overview
The `PerfomanceWindow` displays engine performance metrics, including frame rate, delta time, and memory usage, with graphical plots for analysis.

## Class Definition
```java
package org.PiEngine.Editor;
public class PerfomanceWindow extends EditorWindow
```

## Methods
```java
/**
 * Constructs a new PerfomanceWindow.
 */
public PerfomanceWindow()

/**
 * Constructs a new PerfomanceWindow with a custom name.
 * @param Name The window name
 */
public PerfomanceWindow(String Name)

/**
 * Renders the performance window, showing FPS and memory usage.
 */
@Override
public void onRender()
```

## Example Usage
```java
editor.addWindow(new PerfomanceWindow());
```

## Best Practices
- Use for performance monitoring and debugging
- Plot delta time and memory usage for analysis

## See Also
- [EditorWindow](EditorWindow.md)
