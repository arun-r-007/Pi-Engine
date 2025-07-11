# InspectorWindow Class Documentation

## Function Overview
| Visibility | Function | Description |
|------------|----------|-------------|
| public     | InspectorWindow() | Constructs a new InspectorWindow. |
| public     | InspectorWindow(boolean isproperty) | Constructs a new InspectorWindow, optionally as a property editor. |
| public     | onRender() | Renders the inspector window and its property fields. |

## Overview
The `InspectorWindow` allows inspection and editing of GameObject properties and components. It supports adding, removing, and editing components, as well as layer and transform management.

## Class Definition
```java
package org.PiEngine.Editor;
public class InspectorWindow extends EditorWindow
```

## Properties
| Property | Type | Description |
|----------|------|-------------|
| inspectObject | GameObject | The currently inspected GameObject |
| propertyObject | GameObject | The GameObject for property editing |
| actAsProperty | boolean | Whether this window acts as a property editor |
| count | int | Instance counter for window IDs |
| searchBuffer | ImString | Buffer for component search input |

## Methods
```java
/**
 * Constructs a new InspectorWindow.
 */
public InspectorWindow()

/**
 * Constructs a new InspectorWindow, optionally as a property editor.
 * @param isproperty Whether to act as a property editor
 */
public InspectorWindow(boolean isproperty)

/**
 * Renders the inspector window and its property fields.
 */
@Override
public void onRender()
```

## Example Usage
```java
editor.addWindow(new InspectorWindow());
```

## Best Practices
- Use for component and property inspection
- Support search and filtering for components

## See Also
- [EditorWindow](EditorWindow.md)
- [HierarchyWindow](HierarchyWindow.md)
