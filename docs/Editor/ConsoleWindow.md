# ConsoleWindow Class Documentation

## Function Overview
| Visibility | Function | Description |
|------------|----------|-------------|
| public     | ConsoleWindow() | Constructs a new ConsoleWindow. |
| public     | onRender() | Renders the Console window, including filter dropdown and message area. |

## Overview
The `ConsoleWindow` displays engine and application messages, with filtering and color coding for warnings and errors. It allows clearing the console and filtering messages by type.

## Class Definition
```java
package org.PiEngine.Editor;
public class ConsoleWindow extends EditorWindow
```

## Properties
| Property | Type | Description |
|----------|------|-------------|
| filters | String[] | Available message filters (All, Warning, Error) |
| currentFilter | int | Current filter index |
| count | int | Instance counter for window IDs |

## Methods
```java
/**
 * Constructs a new ConsoleWindow.
 */
public ConsoleWindow()

/**
 * Renders the Console window, including filter dropdown and message area.
 */
@Override
public void onRender()
```

## Example Usage
```java
editor.addWindow(new ConsoleWindow());
```

## Best Practices
- Use filters to focus on warnings/errors
- Clear the console regularly for readability

## See Also
- [EditorWindow](EditorWindow.md)
