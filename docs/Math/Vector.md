# Vector Class Documentation

## Function Overview

| Visibility | Function | Description |
|------------|----------|-------------|
| public     | sqMagnitude | Returns the squared length of the vector. |
| public     | magnitude   | Returns the actual length of the vector. |
| public     | normal      | Returns a normalized copy of the vector. |
| public     | normalize   | Normalizes this vector in-place. |
| public     | add         | Adds another vector and returns the result. |
| public     | sub         | Subtracts another vector and returns the result. |
| public     | dot         | Returns the dot product with another vector. |
| public     | cross       | Returns the cross product with another vector. |
| public     | scale       | Multiplies this vector by a scalar. |
| public     | transform   | Transforms this vector by a 4x4 matrix. |
| public     | toFloatArray| Returns the float array representation. |
| public     | clone       | Creates a deep copy of this vector. |
| public     | equals      | Checks if another object is a vector and has the same components. |
| public static | lerp     | Linearly interpolates between two vectors. |
| public     | toString    | Returns a string representation of this vector. |
| public     | SetVector   | Sets this vector's components from another vector. |
| public     | setX/setY/setZ | Sets the X, Y, or Z component. |
| public     | getX/getY/getZ | Gets the X, Y, or Z component. |
| public static | Distance | Calculates the distance between two vectors. |

## Overview
The `Vector` class represents a 3D vector with x, y, z components. It provides comprehensive functionality for 3D vector mathematics, including basic arithmetic operations, transformations, and utility methods. This class is fundamental for handling positions, directions, normals, and any 3D spatial calculations in the engine.

## Class Definition
```java
package org.PiEngine.Math;
public class Vector
```

## Properties

| Property | Type | Description |
|----------|------|-------------|
| `x` | `float` | X component of the vector |
| `y` | `float` | Y component of the vector |
| `z` | `float` | Z component of the vector |

## Constructors

```java
/**
 * Default constructor: creates a zero vector (0,0,0)
 */
public Vector()

/**
 * Uniform constructor: all components set to the same value
 * @param value Value to set for all components
 */
public Vector(float value)

/**
 * Constructor with specific x, y, z values
 * @param x X component
 * @param y Y component
 * @param z Z component
 */
public Vector(float x, float y, float z)

/**
 * Copy constructor
 * @param copy Vector to copy
 */
public Vector(Vector copy)
```

## Methods

### Magnitude Operations
```java
/**
 * Returns the squared magnitude (length) of the vector
 * @return The squared length of the vector
 */
public float sqMagnitude()

/**
 * Returns the actual magnitude (length) of the vector
 * @return The length of the vector
 */
public float magnitude()

/**
 * Returns a new vector in the same direction but with a magnitude of 1
 * @return A normalized copy of this vector
 */
public Vector normal()

/**
 * Normalizes this vector in-place (sets magnitude to 1)
 */
public void normalize()
```

### Vector Arithmetic
```java
/**
 * Adds another vector to this one and returns the result
 * @param operand Vector to add
 * @return New vector containing the sum
 */
public Vector add(Vector operand)

/**
 * Subtracts another vector from this one and returns the result
 * @param operand Vector to subtract
 * @return New vector containing the difference
 */
public Vector sub(Vector operand)

/**
 * Returns the dot product (angle-based similarity) between two vectors
 * @param other Vector to dot with
 * @return Dot product result
 */
public float dot(Vector other)

/**
 * Returns the cross product (perpendicular vector) of this and another vector
 * @param other Vector to cross with
 * @return New vector perpendicular to both input vectors
 */
public Vector cross(Vector other)

/**
 * Multiplies this vector by a scalar and returns the result
 * @param scalar Value to multiply by
 * @return New scaled vector
 */
public Vector scale(float scalar)
```

### Transformation
```java
/**
 * Returns this vector transformed by a 4x4 matrix (world transform)
 * @param matrix Transform matrix
 * @return Transformed vector
 */
public Vector transform(Matrix4 matrix)
```

### Utility Methods
```java
/**
 * Returns the float array representation: [x, y, z]
 * @return Float array containing vector components
 */
public float[] toFloatArray()

/**
 * Creates a deep copy of this vector
 * @return New vector with same components
 */
@Override
public Vector clone()

/**
 * Checks if another object is a vector and has the same components
 * @param obj Object to compare with
 * @return true if vectors are equal
 */
@Override
public boolean equals(Object obj)

/**
 * Linearly interpolates between two vectors
 * @param a Start vector
 * @param b End vector
 * @param t Interpolation factor (0 to 1)
 * @return Interpolated vector
 */
public static Vector lerp(Vector a, Vector b, float t)

/**
 * Returns a string representation of this vector
 * @return String in format "Vector(x, y, z)"
 */
@Override
public String toString()
```

### Setters and Getters
```java
/**
 * Sets this vector's components from another vector
 * @param v Source vector
 */
public void SetVector(Vector v)

/**
 * Sets the X component
 * @param x New X value
 */
public void setX(float x)

/**
 * Sets the Y component
 * @param y New Y value
 */
public void setY(float y)

/**
 * Sets the Z component
 * @param z New Z value
 */
public void setZ(float z)

/**
 * Gets the X component
 * @return X value
 */
public float getX()

/**
 * Gets the Y component
 * @return Y value
 */
public float getY()

/**
 * Gets the Z component
 * @return Z value
 */
public float getZ()

/**
 * Calculates the distance between two vectors
 * @param a First vector
 * @param b Second vector
 * @return Distance between vectors
 */
public static float Distance(Vector a, Vector b)
```

## Common Use Cases

### Position Manipulation
```java
Vector position = new Vector(1, 2, 3);
Vector offset = new Vector(0, 1, 0);
position = position.add(offset); // Move up by 1
```

### Direction Calculations
```java
Vector direction = targetPos.sub(currentPos).normal();
float distance = targetPos.sub(currentPos).magnitude();
```

### Vector Math
```java
Vector right = new Vector(1, 0, 0);
Vector up = new Vector(0, 1, 0);
Vector forward = right.cross(up); // Creates forward vector (0, 0, 1)
```

### Transformations
```java
Matrix4 worldMatrix = Matrix4.identity();
Vector localPos = new Vector(1, 1, 1);
Vector worldPos = localPos.transform(worldMatrix);
```

## Best Practices

1. Use `sqMagnitude()` instead of `magnitude()` when comparing distances (more efficient)
2. Always normalize vectors used for directions
3. Use `lerp()` for smooth transitions between positions
4. Cache vector calculations that are used frequently
5. Use `clone()` when you need a copy that won't be modified by other code

## Performance Considerations

1. `magnitude()` involves a square root - use `sqMagnitude()` when possible
2. `normalize()` modifies the vector in place - more efficient than `normal()`
3. Matrix transformations are expensive - batch them when possible
4. Creating new vectors in tight loops can be costly - reuse vectors when possible
5. `Distance()` creates temporary vectors - cache the result if used frequently
