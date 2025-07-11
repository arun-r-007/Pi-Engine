# HierarchyWindow Class Documentation

## Function Overview
| Visibility | Function | Description |
|------------|----------|-------------|
| public     | HierarchyWindow() | Constructs a new HierarchyWindow. |
| public     | setRoot(GameObject root) | Sets the root GameObject for the hierarchy view. |
| public     | onUpdate() | Called every frame to update hierarchy logic and handle reparenting. |
| public     | onRender() | Renders the hierarchy window and all GameObjects. |

## Overview
The `HierarchyWindow` displays a hierarchical view of all GameObjects in the scene, supporting selection, renaming, adding, removing, and reparenting objects.

## Class Definition
```java
package org.PiEngine.Editor;
public class HierarchyWindow extends EditorWindow
```

## Properties
| Property | Type | Description |
|----------|------|-------------|
| root | GameObject | The root GameObject for the hierarchy |
| renamingObject | GameObject | The GameObject being renamed |
| renameBuffer | ImString | Buffer for renaming input |
| toRemove | List<GameObject> | List of GameObjects to remove |
| windowsToAdd | List<InspectorWindow> | Windows to add for inspection |
| toReparent | List<List<GameObject>> | List of GameObject pairs to reparent |
| count | int | Instance counter for window IDs |

## Methods
```java
/**
 * Constructs a new HierarchyWindow.
 */
public HierarchyWindow()

/**
 * Sets the root GameObject for the hierarchy view.
 * @param root The root GameObject
 */
public void setRoot(GameObject root)

/**
 * Called every frame to update hierarchy logic and handle reparenting.
 */
@Override
public void onUpdate()

/**
 * Renders the hierarchy window and all GameObjects.
 */
@Override
public void onRender()
```

## Example Usage
```java
HierarchyWindow hierarchy = new HierarchyWindow();
hierarchy.setRoot(sceneRoot);
editor.addWindow(hierarchy);
```

## Best Practices
- Use for scene graph management
- Support drag-and-drop for reparenting

## See Also
- [EditorWindow](EditorWindow.md)
- [InspectorWindow](InspectorWindow.md)
