# Transform

**File:** `src/main/java/org/PiEngine/GameObjects/Transform.java`

## Overview
The `Transform` class represents position, rotation, and scale in a hierarchical scene graph. It supports parent-child relationships and provides both local and world space calculations.

## Fields
```java
// Private
private Vector position;              // Local position
private Vector rotation;              // Local rotation
private Vector scale;                 // Local scale
private Transform parent;             // Parent transform
private List<Transform> childrens;    // Child transforms
private Matrix4 transformMatrix;      // Cached world matrix
private GameObject gameObject;        // Owning GameObject
```

## Constructors
```java
public Transform() // Default: position (0,0,0), rotation (0,0,0), scale (1,1,1)
```

## Public Methods

### Hierarchy Management
```java
public void addChild(Transform child)         // Add child
public void removeChild(Transform child)      // Remove child
public Transform getParent()                  // Get parent
public List<Transform> getChildren()          // Get children
```

### Local Transform
```java
public Vector getLocalPosition()              // Get local position
public Vector getLocalRotation()              // Get local rotation
public Vector getLocalScale()                 // Get local scale
public void setLocalPosition(Vector pos)      // Set local position
public void setLocalRotation(Vector rot)      // Set local rotation
public void setLocalScale(Vector scale)       // Set local scale
```

### World Transform
```java
public Matrix4 getLocalMatrix()               // Local transform matrix
public Matrix4 getWorldMatrix()               // World transform matrix
public Vector getWorldPosition()              // World position
public Vector getWorldRotation()              // World rotation
public Vector getWorldScale()                 // World scale
public void setWorldPosition(Vector pos)      // Set world position
public void setWorldRotation(Vector rot)      // Set world rotation
public void setWorldScale(Vector scale)       // Set world scale
```

### Matrix and Update
```java
public Matrix4 getMatrix()                    // Cached world matrix
public void updateMatrix()                    // Update this and children
```

### GameObject Reference
```java
public void setGameObject(GameObject obj)     // Set owning GameObject
public GameObject getGameObject()             // Get owning GameObject
```

### Utility
```java
public static Vector wrapRotation(Vector r)   // Normalize rotation
private static float wrapAngle(float a)       // Normalize angle
public String toString()                      // String representation
```

## Usage Example
```java
Transform t = new Transform();
t.setLocalPosition(new Vector(1,2,3));
t.setLocalRotation(new Vector(0,0,90));
t.setLocalScale(new Vector(2,2,2));

Transform child = new Transform();
t.addChild(child);
child.setLocalPosition(new Vector(0,1,0));

Vector worldPos = child.getWorldPosition();
```

## Best Practices
- Always use `updateMatrix()` after changing hierarchy or transforms
- Use world setters to position objects in global space
- Use local setters for relative movement
- Use `getWorldPosition()` for rendering and physics

## Notes
- All transforms are relative to their parent
- Supports both 2D and 3D (only Z used for 2D rotation)
