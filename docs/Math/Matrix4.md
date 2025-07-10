# Matrix4 Class Documentation

## Function Overview

| Visibility | Function | Description |
|------------|----------|-------------|
| public     | Matrix4()    | Constructor, creates an uninitialized 4x4 matrix. |
| public static | identity  | Creates and returns an identity matrix. |
| public static | multiply  | Multiplies two matrices and returns the result. |
| public     | multiply    | Instance version of matrix multiplication. |
| public     | copy        | Creates a deep copy of this matrix. |
| public     | transpose   | Returns a transposed version of this matrix. |
| public static | translate | Creates a translation matrix from a vector or components. |
| public static | scale     | Creates a scale matrix from a vector or components. |
| public static | rotate    | Creates a rotation matrix for a given angle and axis. |
| public static | perspective | Creates a perspective projection matrix. |
| public static | orthographic | Creates an orthographic projection matrix. |
| public     | multiply(Vector) | Multiplies this matrix with a vector. |
| public     | toArray     | Returns the underlying array of elements. |
| public     | getTranslation | Extracts the translation component from this matrix. |
| public     | toString    | Returns a readable string representation of the matrix. |
| public     | toFloatBuffer | Creates an OpenGL-compatible FloatBuffer. |
| public static | invert    | Calculates the inverse of a matrix. |

## Overview
The `Matrix4` class represents a 4x4 matrix used for 3D transformations including translation, rotation, scaling, and projections. It is stored in column-major order to match OpenGL expectations and provides comprehensive functionality for matrix operations and transformations.

## Class Definition
```java
package org.PiEngine.Math;
public class Matrix4
```

## Properties

| Property | Type | Description |
|----------|------|-------------|
| `elements` | `float[]` | 16-element array storing matrix values in column-major order |

## Static Factory Methods

```java
/**
 * Creates an identity matrix
 * @return New identity matrix
 */
public static Matrix4 identity()

/**
 * Creates an orthographic projection matrix
 * @param left Left plane
 * @param right Right plane
 * @param bottom Bottom plane
 * @param top Top plane
 * @param near Near plane
 * @param far Far plane
 * @return Orthographic projection matrix
 */
public static Matrix4 orthographic(float left, float right, float bottom, float top, float near, float far)

/**
 * Creates a perspective projection matrix
 * @param fov Field of view in degrees
 * @param aspect Aspect ratio (width/height)
 * @param near Near plane
 * @param far Far plane
 * @return Perspective projection matrix
 */
public static Matrix4 perspective(float fov, float aspect, float near, float far)

/**
 * Creates a translation matrix
 * @param vector Translation vector
 * @return Translation matrix
 */
public static Matrix4 translate(Vector vector)

/**
 * Creates a translation matrix
 * @param x X translation
 * @param y Y translation
 * @param z Z translation
 * @return Translation matrix
 */
public static Matrix4 translate(float x, float y, float z)

/**
 * Creates a scale matrix
 * @param vector Scale vector
 * @return Scale matrix
 */
public static Matrix4 scale(Vector vector)

/**
 * Creates a scale matrix
 * @param x X scale
 * @param y Y scale
 * @param z Z scale
 * @return Scale matrix
 */
public static Matrix4 scale(float x, float y, float z)

/**
 * Creates a rotation matrix
 * @param angleDeg Rotation angle in degrees
 * @param axis Rotation axis
 * @return Rotation matrix
 */
public static Matrix4 rotate(float angleDeg, Vector axis)

/**
 * Multiplies two matrices
 * @param a First matrix
 * @param b Second matrix
 * @return Result of a * b
 */
public static Matrix4 multiply(Matrix4 a, Matrix4 b)

/**
 * Creates an inverted matrix
 * @param m Matrix to invert
 * @return Inverted matrix
 */
public static Matrix4 invert(Matrix4 m)
```

## Instance Methods

### Matrix Operations
```java
/**
 * Creates a copy of this matrix
 * @return New matrix with same values
 */
public Matrix4 copy()

/**
 * Transposes this matrix
 * @return Transposed matrix (rows become columns)
 */
public Matrix4 transpose()

/**
 * Multiplies this matrix with a vector (assuming w=1)
 * @param v Vector to transform
 * @return Transformed vector
 */
public Vector multiply(Vector v)
```

### Data Access
```java
/**
 * Returns the underlying array of elements
 * @return Float array of matrix elements
 */
public float[] toArray()

/**
 * Creates a FloatBuffer containing the matrix elements
 * @return FloatBuffer in OpenGL-compatible format
 */
public FloatBuffer toFloatBuffer()

/**
 * Extracts the translation component
 * @return Vector containing translation
 */
public Vector getTranslation()

/**
 * Returns a string representation of the matrix
 * @return Formatted string of matrix elements
 */
@Override
public String toString()
```

## Common Use Cases

### Camera Transformations
```java
// Create perspective camera
float fov = 60.0f;
float aspect = width / height;
Matrix4 projection = Matrix4.perspective(fov, aspect, 0.1f, 1000.0f);

// Create view matrix
Vector position = new Vector(0, 5, -10);
Matrix4 view = Matrix4.translate(position.scale(-1));
```

### Object Transformations
```java
// Create world matrix
Matrix4 translation = Matrix4.translate(position);
Matrix4 rotation = Matrix4.rotate(angle, new Vector(0, 1, 0));
Matrix4 scale = Matrix4.scale(new Vector(2, 2, 2));

// Combined transform (scale, then rotate, then translate)
Matrix4 world = Matrix4.multiply(translation, Matrix4.multiply(rotation, scale));
```

### OpenGL Integration
```java
// Get matrix data for shader
float[] matrixData = worldMatrix.toArray();
FloatBuffer matrixBuffer = worldMatrix.toFloatBuffer();

// Upload to shader
glUniformMatrix4fv(worldMatrixLoc, false, matrixBuffer);
```

## Best Practices

1. Use the static factory methods for creating common matrices
2. Combine matrices using multiply() in the correct order (transformations are applied right-to-left)
3. Cache matrices that don't change frequently
4. Use toFloatBuffer() for OpenGL uniform uploads
5. Be careful with matrix multiplication order - it matters!

## Performance Considerations

1. Matrix multiplication is expensive - cache results when possible
2. Matrix inversion is very expensive - avoid if possible
3. Creating FloatBuffers is costly - reuse them when possible
4. Perspective and orthographic calculations are expensive - cache these matrices
5. Consider using a matrix stack for hierarchical transformations

## Mathematical Notes

1. Matrices are stored in column-major order (OpenGL standard)
2. Transformations are applied in scale → rotate → translate order
3. Matrix multiplication is not commutative (A * B ≠ B * A)
4. Translation is in the fourth column for column-major storage
5. Rotation matrices use right-hand rule for positive angles
