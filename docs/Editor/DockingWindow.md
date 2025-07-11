# DockingWindow Class Documentation

## Function Overview
| Visibility | Function | Description |
|------------|----------|-------------|
| public     | DockingWindow() | Constructs a new DockingWindow. |
| public     | addDockedWindow(EditorWindow window) | Adds an EditorWindow to the docked windows list. |
| public     | onCreate() | Called when the window is created. Sets up size and position. |
| public     | onUpdate() | Called every frame to update docked windows. |
| public     | onRender() | Renders the docking window and all docked windows. |

## Overview
The `DockingWindow` manages multiple docked editor windows, providing a unified interface for window management and layout.

## Class Definition
```java
package org.PiEngine.Editor;
public class DockingWindow extends EditorWindow
```

## Properties
| Property | Type | Description |
|----------|------|-------------|
| dockedWindows | List<EditorWindow> | List of docked editor windows |

## Methods
```java
/**
 * Constructs a new DockingWindow.
 */
public DockingWindow()

/**
 * Adds an EditorWindow to the docked windows list.
 * @param window The window to dock
 */
public void addDockedWindow(EditorWindow window)

/**
 * Called when the window is created. Sets up size and position.
 */
@Override
public void onCreate()

/**
 * Called every frame to update docked windows.
 */
@Override
public void onUpdate()

/**
 * Renders the docking window and all docked windows.
 */
@Override
public void onRender()
```

## Example Usage
```java
DockingWindow docker = new DockingWindow();
docker.addDockedWindow(new ConsoleWindow());
editor.addWindow(docker);
```

## Best Practices
- Use docking for complex editor layouts
- Add windows before calling `onCreate()`

## See Also
- [EditorWindow](EditorWindow.md)
