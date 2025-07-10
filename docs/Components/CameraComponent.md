# CameraComponent Class Documentation

## Function Overview
| Visibility | Function | Description |
|------------|----------|-------------|
| public     | CameraComponent() | Constructs a CameraComponent and initializes its Camera. |
| public     | getCamera() | Returns the managed Camera instance. |
| public     | start() | Sets the camera position to the GameObject's world position. |
| public     | update() | Updates the camera's position and rotation to match the GameObject's transform. |

## Overview
The `CameraComponent` attaches a `Camera` to a `GameObject`, synchronizing its position and rotation with the object's transform. It is used to define the viewpoint for rendering.

## Class Definition
```java
package org.PiEngine.Component;
public class CameraComponent extends Component
```

## Properties
| Property | Type | Description |
|----------|------|-------------|
| Near | Float | Near clipping plane distance |
| Far | Float | Far clipping plane distance |
| camera | Camera | The managed Camera instance |

## Methods
```java
/**
 * Constructs a CameraComponent and initializes its Camera.
 */
public CameraComponent()

/**
 * Returns the Camera instance managed by this component.
 * @return The Camera instance
 */
public Camera getCamera()

/**
 * Called once when the component is first added to a GameObject.
 * Sets the camera position to the GameObject's world position.
 */
public void start()

/**
 * Called every frame. Updates the camera's position and rotation to match the GameObject's transform.
 */
public void update()
```

## Example Usage
```java
CameraComponent camComp = new CameraComponent();
GameObject cameraObject = new GameObject();
cameraObject.addComponent(camComp);
```

## Best Practices
- Attach only one `CameraComponent` per camera GameObject.
- Use `getCamera()` to access camera settings and matrices.

## See Also
- [Camera](../Core/Camera.md)
- [Component](Component.md)
