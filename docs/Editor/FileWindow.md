# FileWindow Class Documentation

## Function Overview
| Visibility | Function | Description |
|------------|----------|-------------|
| public     | FileWindow() | Constructs a new FileWindow and sets the root directory. |
| public     | onUpdate() | Called every frame to update window logic. |
| public     | onRender() | Renders the file explorer window and its contents. |

## Overview
The `FileWindow` provides a file explorer for browsing and managing files in the engine's resources directory. It supports navigation, selection, and file operations.

## Class Definition
```java
package org.PiEngine.Editor;
public class FileWindow extends EditorWindow
```

## Properties
| Property | Type | Description |
|----------|------|-------------|
| rootDirectory | File | The root directory displayed in the explorer |
| count | int | Instance counter for window IDs |
| BASE_PATH | Path | Absolute path to resources folder |
| ABSOLUTE_BASE | Path | Absolute base path |
| RELATIVE_BASE | Path | Relative base path |

## Methods
```java
/**
 * Constructs a new FileWindow and sets the root directory.
 */
public FileWindow()

/**
 * Called every frame to update window logic.
 */
@Override
public void onUpdate()

/**
 * Renders the file explorer window and its contents.
 */
@Override
public void onRender()
```

## Example Usage
```java
editor.addWindow(new FileWindow());
```

## Best Practices
- Use for asset management and file navigation
- Keep file operations modular for extension

## See Also
- [EditorWindow](EditorWindow.md)
