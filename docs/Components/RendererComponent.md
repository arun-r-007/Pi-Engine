# RendererComponent Class Documentation

## Function Overview
| Visibility | Function | Description |
|------------|----------|-------------|
| public     | RendererComponent() | Constructs a RendererComponent and initializes mesh/shader. |
| public     | start() | Initializes color, mesh, and shader for rendering. |
| public     | update() | Updates the mesh geometry if needed. |
| private    | updateMesh() | Updates the mesh geometry based on the current size. |
| public     | render(Camera camera) | Renders the mesh using the assigned shader and texture. |
| public     | onDestroy() | Disposes of the mesh resources. |

## Overview
The `RendererComponent` handles mesh rendering for a `GameObject`, including mesh geometry, color, shader, and texture assignment.

## Class Definition
```java
package org.PiEngine.Component;
public class RendererComponent extends Component
```

## Properties
| Property | Type | Description |
|----------|------|-------------|
| Render | boolean | Whether to render this component |
| mesh | Mesh | The mesh to render |
| size | Float | The size of the rendered mesh |
| shader | Shader | The shader used for rendering |
| Color | Vector | The color tint for rendering |
| texture | Texture | The texture to use for rendering |

## Methods
```java
/**
 * Constructs a RendererComponent and initializes mesh/shader.
 */
public RendererComponent()

/**
 * Called once when the component is first added to a GameObject.
 * Initializes color, mesh, and shader for rendering.
 */
public void start()

/**
 * Called every frame. Updates the mesh geometry if needed.
 */
public void update()

/**
 * Updates the mesh geometry based on the current size.
 */
private void updateMesh()

/**
 * Renders the mesh using the assigned shader and texture.
 * @param camera The camera to use for rendering
 */
public void render(Camera camera)

/**
 * Called once before the component is removed or the GameObject is destroyed.
 * Disposes of the mesh resources.
 */
public void onDestroy()
```

## Example Usage
```java
RendererComponent renderer = new RendererComponent();
GameObject obj = new GameObject();
obj.addComponent(renderer);
```

## Best Practices
- Assign a valid mesh and shader before rendering.
- Use the `Color` and `texture` fields for appearance customization.

## See Also
- [Component](Component.md)
- [GameObject](../GameObject/GameObject.md)
