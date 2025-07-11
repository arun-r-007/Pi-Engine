# NavigationWindow Class Documentation

## Function Overview
| Visibility | Function | Description |
|------------|----------|-------------|
| public     | NavigationWindow() | Constructs a new NavigationWindow. |
| public     | onRender() | Renders the navigation window and its menu bar. |

## Overview
The `NavigationWindow` provides a main menu bar for file and edit operations, including scene loading, saving, and basic editing actions.

## Class Definition
```java
package org.PiEngine.Editor;
public class NavigationWindow extends EditorWindow
```

## Methods
```java
/**
 * Constructs a new NavigationWindow.
 */
public NavigationWindow()

/**
 * Renders the navigation window and its menu bar.
 */
@Override
public void onRender()
```

## Example Usage
```java
editor.addWindow(new NavigationWindow());
```

## Best Practices
- Use for top-level menu actions
- Extend for custom menu items

## See Also
- [EditorWindow](EditorWindow.md)
