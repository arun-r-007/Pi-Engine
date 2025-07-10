# Transform Class Documentation

## Function Overview
| Visibility | Function | Description |
|------------|----------|-------------|
| public     | Transform() | Constructs a new Transform with default values. |
| public     | addChild(Transform child) | Adds a child Transform. |
| public     | removeChild(Transform child) | Removes a child Transform. |
| public     | getParent() | Returns the parent Transform. |
| public     | getChildren() | Returns the list of child Transforms. |
| public     | getLocalPosition() | Returns the local position. |
| public     | getLocalRotation() | Returns the local rotation. |
| public     | getLocalScale() | Returns the local scale. |
| public     | setLocalPosition(Vector pos) | Sets the local position. |
| public     | setLocalRotation(Vector rot) | Sets the local rotation. |
| public     | setLocalScale(Vector scale) | Sets the local scale. |
| public     | getLocalMatrix() | Returns the local transformation matrix. |
| public     | getWorldMatrix() | Returns the world transformation matrix. |
| public     | getWorldPosition() | Returns the world position. |
| public     | getWorldRotation() | Returns the world rotation. |
| public     | getWorldScale() | Returns the world scale. |
| public     | setWorldPosition(Vector pos) | Sets the world position. |
| public     | setWorldRotation(Vector rot) | Sets the world rotation. |
| public     | setWorldScale(Vector scale) | Sets the world scale. |
| public     | setGameObject(GameObject go) | Sets the owning GameObject. |
| public     | getGameObject() | Gets the owning GameObject. |
| public     | destroy() | Destroys this Transform and its children. |

## Overview
The `Transform` class represents an object's position, rotation, and scale in the scene graph. It supports parent-child relationships, allowing for hierarchical transformations. This is similar to Unity's Transform component.

## Class Definition
```java
package org.PiEngine.GameObjects;
public class Transform
```

## Properties
| Property | Type | Description |
|----------|------|-------------|
| position | Vector | Local position relative to parent |
| rotation | Vector | Local rotation (Z used in 2D) |
| scale | Vector | Local scale |
| parent | Transform | Reference to parent transform |
| childrens | List<Transform> | List of child transforms |
| transformMatrix | Matrix4 | Cached world transformation matrix |
| gameObject | GameObject | Reference to owning GameObject |

## Methods
```java
/**
 * Constructs a new Transform with default position (0,0,0), rotation (0,0,0), and scale (1,1,1).
 */
public Transform()

/**
 * Adds a child Transform to this Transform.
 * @param child The child Transform to add
 */
public void addChild(Transform child)

/**
 * Removes a child Transform from this Transform.
 * @param child The child Transform to remove
 */
public void removeChild(Transform child)

/**
 * Returns the parent Transform.
 * @return The parent Transform, or null if root
 */
public Transform getParent()

/**
 * Returns the list of child Transforms.
 * @return List of child Transforms
 */
public List<Transform> getChildren()

/**
 * Returns the local position relative to the parent.
 * @return Local position vector
 */
public Vector getLocalPosition()

/**
 * Returns the local rotation.
 * @return Local rotation vector
 */
public Vector getLocalRotation()

/**
 * Returns the local scale.
 * @return Local scale vector
 */
public Vector getLocalScale()

/**
 * Sets the local position.
 * @param pos The new local position
 */
public void setLocalPosition(Vector pos)

/**
 * Sets the local rotation.
 * @param rot The new local rotation
 */
public void setLocalRotation(Vector rot)

/**
 * Sets the local scale.
 * @param scale The new local scale
 */
public void setLocalScale(Vector scale)

/**
 * Returns the transformation matrix built from position, rotation, and scale.
 * @return Local transformation Matrix4
 */
public Matrix4 getLocalMatrix()

/**
 * Returns the world transformation matrix by combining with parent transforms recursively.
 * @return World transformation Matrix4
 */
public Matrix4 getWorldMatrix()

/**
 * Gets the position in world space.
 * @return World position vector
 */
public Vector getWorldPosition()

/**
 * Gets the rotation in world space by recursively adding parent rotations.
 * @return World rotation vector
 */
public Vector getWorldRotation()

/**
 * Gets the scale in world space by accumulating parent scales.
 * @return World scale vector
 */
public Vector getWorldScale()

/**
 * Sets the world position, adjusting local values accordingly.
 * @param pos The new world position
 */
public void setWorldPosition(Vector pos)

/**
 * Sets the world rotation, adjusting local values accordingly.
 * @param rot The new world rotation
 */
public void setWorldRotation(Vector rot)

/**
 * Sets the world scale, adjusting local values accordingly.
 * @param scale The new world scale
 */
public void setWorldScale(Vector scale)

/**
 * Sets the owning GameObject for this Transform.
 * @param go The GameObject to set
 */
public void setGameObject(GameObject go)

/**
 * Gets the owning GameObject for this Transform.
 * @return The GameObject
 */
public GameObject getGameObject()

/**
 * Destroys this Transform and all its children, removing them from the hierarchy.
 */
public void destroy()
```

## Example Usage
```java
GameObject player = new GameObject("Player");
player.transform.setLocalPosition(new Vector(0, 1, 0));
player.transform.setLocalRotation(new Vector(0, 0, 45));
player.transform.setLocalScale(new Vector(2, 2, 2));

Vector worldPos = player.transform.getWorldPosition();
```

## Best Practices
- Use `addChild` and `removeChild` to manage hierarchy
- Use `getWorldPosition` for rendering and physics
- Use `setLocalPosition` for relative movement
- Always set the GameObject reference for correct hierarchy management

## See Also
- [GameObject](GameObject.md)
- [IDGenerator](IDGenerator.md)
