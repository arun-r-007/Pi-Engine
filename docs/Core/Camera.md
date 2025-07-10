# Camera Class Documentation

## Function Overview
| Visibility | Function | Description |
|------------|----------|-------------|
| public     | Camera() | Default constructor for Camera. |
| public     | Camera(float aspectRatio, float nearPlane, float farPlane) | Constructs a new Camera with the specified projection parameters. |
| public     | setOrthographic(float left, float right, float bottom, float top, float near, float far) | Sets this camera to use orthographic projection. |
| public     | setPerspective(float fov, float aspectRatio, float near, float far) | Sets this camera to use perspective projection. |
| public     | updateProjectionMatrix() | Updates the projection matrix based on current parameters. |
| public     | updateViewMatrix() | Updates the view matrix based on the camera's current position and rotation. |
| public     | getViewMatrix() | Returns the view matrix. |
| public     | getProjectionMatrix() | Returns the projection matrix. |
| public     | getPosition() | Returns the position of the camera. |
| public     | setPosition(Vector position) | Sets the camera position. |
| public     | getRotation() | Returns the rotation of the camera. |
| public     | setRotation(Vector rotation) | Sets the camera rotation. |
| public     | applyToOpenGL() | Applies this camera's projection and view matrix to the OpenGL pipeline. |
| public     | applyToShader(Shader shader) | Applies this camera's projection and view matrix to the given shader. |
| public     | getRenderLayerMask() | Returns the current render layer bitmask. |
| public     | setRenderLayerMask(int mask) | Sets the render layer bitmask. |
| public     | addRenderLayer(int layerBit) | Enables rendering for the given layer. |
| public     | removeRenderLayer(int layerBit) | Disables rendering for the given layer. |
| public     | canRenderLayer(int layerBit) | Checks whether this camera is set to render the given layer. |

## Overview
The `Camera` class handles view and projection matrix generation for a 3D scene using position, rotation, and projection parameters. Supports both perspective and orthographic projections, and layer-based rendering.

## Class Definition
```java
package org.PiEngine.Core;
public class Camera
```

## Properties
| Property | Type | Description |
|----------|------|-------------|
| position | Vector | Position of the camera in world space |
| rotation | Vector | Rotation of the camera (pitch, yaw, roll) |
| viewMatrix | Matrix4 | View matrix (world-to-camera transformation) |
| projectionMatrix | Matrix4 | Projection matrix (camera-to-clip transformation) |
| fov | float | Field of view (in degrees) |
| aspectRatio | float | Aspect ratio (width / height) |
| nearPlane | float | Near clipping plane |
| farPlane | float | Far clipping plane |
| isOrthographic | boolean | Whether the camera uses orthographic projection |
| orthoLeft, orthoRight, orthoBottom, orthoTop | float | Orthographic projection bounds |
| renderLayerMask | int | Bitmask for which layers this camera will render |

## Methods
```java
/**
 * Default constructor for Camera.
 */
public Camera()

/**
 * Constructs a new Camera with the specified projection parameters.
 * @param aspectRatio The aspect ratio (width/height)
 * @param nearPlane The near clipping plane
 * @param farPlane The far clipping plane
 */
public Camera(float aspectRatio, float nearPlane, float farPlane)

/**
 * Sets this camera to use orthographic projection.
 * @param left Left plane
 * @param right Right plane
 * @param bottom Bottom plane
 * @param top Top plane
 * @param near Near plane
 * @param far Far plane
 */
public void setOrthographic(float left, float right, float bottom, float top, float near, float far)

/**
 * Sets this camera to use perspective projection.
 * @param fov Field of view in degrees
 * @param aspectRatio Aspect ratio (width/height)
 * @param near Near plane
 * @param far Far plane
 */
public void setPerspective(float fov, float aspectRatio, float near, float far)

/**
 * Updates the projection matrix based on current parameters.
 */
public void updateProjectionMatrix()

/**
 * Updates the view matrix based on the camera's current position and rotation.
 */
public void updateViewMatrix()

/**
 * Returns the view matrix.
 * @return The view matrix
 */
public Matrix4 getViewMatrix()

/**
 * Returns the projection matrix.
 * @return The projection matrix
 */
public Matrix4 getProjectionMatrix()

/**
 * Returns the position of the camera.
 * @return The camera position
 */
public Vector getPosition()

/**
 * Sets the camera position.
 * @param position The new camera position
 */
public void setPosition(Vector position)

/**
 * Returns the rotation of the camera.
 * @return The camera rotation
 */
public Vector getRotation()

/**
 * Sets the camera rotation.
 * @param rotation The new camera rotation
 */
public void setRotation(Vector rotation)

/**
 * Applies this camera's projection and view matrix to the OpenGL pipeline.
 */
public void applyToOpenGL()

/**
 * Applies this camera's projection and view matrix to the given shader.
 * @param shader The shader to apply camera matrices to
 */
public void applyToShader(Shader shader)

/**
 * Returns the current render layer bitmask.
 * @return The render layer bitmask
 */
public int getRenderLayerMask()

/**
 * Sets the render layer bitmask. Only GameObjects with matching layers will be rendered.
 * @param mask The new render layer bitmask
 */
public void setRenderLayerMask(int mask)

/**
 * Enables rendering for the given layer (bitwise OR).
 * @param layerBit The layer bit to enable
 */
public void addRenderLayer(int layerBit)

/**
 * Disables rendering for the given layer (bitwise AND NOT).
 * @param layerBit The layer bit to disable
 */
public void removeRenderLayer(int layerBit)

/**
 * Checks whether this camera is set to render the given layer.
 * @param layerBit The layer bit to check
 * @return True if the camera renders the layer, false otherwise
 */
public boolean canRenderLayer(int layerBit)
```

## Example Usage
```java
Camera camera = new Camera(16f/9f, 0.1f, 1000f);
camera.setPerspective(60f, 16f/9f, 0.1f, 1000f);
camera.setPosition(new Vector(0, 5, -10));
camera.setRotation(new Vector(0, 0, 0));
camera.addRenderLayer(1 << 2); // Enable rendering for layer 2
if (camera.canRenderLayer(1 << 2)) {
    // Render objects on layer 2
}
```

## Best Practices
- Use `setPerspective` or `setOrthographic` to configure the camera for your scene
- Use `addRenderLayer` and `removeRenderLayer` to control which layers are visible
- Always call `updateProjectionMatrix` and `updateViewMatrix` after changing camera parameters
- Use `applyToOpenGL` or `applyToShader` to bind camera matrices before rendering

## See Also
- [Matrix4](../Math/Matrix4.md)
- [Vector](../Math/Vector.md)
