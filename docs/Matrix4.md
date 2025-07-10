# Math: Matrix4

**File:** `src/main/java/org/PiEngine/Math/Matrix4.java`

## Overview
The `Matrix4` class represents a 4x4 matrix for 3D transformations (translation, rotation, scale, projection). Used throughout the engine for spatial math and rendering.

## Fields
```java
// Public
public float[] elements; // 16 elements, column-major order
```

## Constructors
```java
public Matrix4() // Creates an uninitialized 4x4 matrix
```

## Static Methods
```java
public static Matrix4 identity() // Identity matrix
public static Matrix4 multiply(Matrix4 a, Matrix4 b) // Matrix multiplication
public static Matrix4 translate(Vector v) // Translation matrix
public static Matrix4 scale(Vector v) // Scale matrix
public static Matrix4 rotate(float angleDeg, Vector axis) // Rotation matrix
public static Matrix4 perspective(float fov, float aspect, float near, float far) // Perspective projection
public static Matrix4 orthographic(float left, float right, float bottom, float top, float near, float far) // Ortho projection
public static Matrix4 invert(Matrix4 m) // Inverse matrix
```

## Instance Methods
```java
public Matrix4 multiply(Matrix4 other) // Multiply with another matrix
public Matrix4 copy() // Deep copy
public Matrix4 transpose() // Transpose
public Vector multiply(Vector v) // Transform a vector
public float[] toArray() // Get elements
public Vector getTranslation() // Extract translation
public FloatBuffer toFloatBuffer() // For OpenGL
public String toString() // String representation
```

## Usage Example
```java
Matrix4 t = Matrix4.translate(new Vector(1,2,3));
Matrix4 r = Matrix4.rotate(90, new Vector(0,1,0));
Matrix4 s = Matrix4.scale(new Vector(2,2,2));
Matrix4 model = Matrix4.multiply(t, Matrix4.multiply(r, s));
Vector v = new Vector(1,0,0);
Vector transformed = model.multiply(v);
```

## Best Practices
- Use static methods for creating transformation matrices
- Use `toFloatBuffer()` for OpenGL uniform uploads
- Always use `invert()` for world-to-local transforms

## Notes
- All elements are public for performance
- Column-major order matches OpenGL
