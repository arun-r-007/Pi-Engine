# Math: Vector

**File:** `src/main/java/org/PiEngine/Math/Vector.java`

## Overview
The `Vector` class represents a 3D vector with x, y, z components. Used for positions, directions, normals, and mathematical operations in 3D space.

## Fields
```java
// Public
public float x, y, z; // The vector components
```

## Constructors
```java
public Vector()                  // Zero vector (0,0,0)
public Vector(float value)       // Uniform vector (value,value,value)
public Vector(float x, float y, float z) // Custom vector
public Vector(Vector copy)       // Copy constructor
```

## Public Methods
```java
// Magnitude and normalization
public float sqMagnitude()       // Squared length
public float magnitude()         // Actual length
public Vector normal()           // Returns normalized copy
public void normalize()          // Normalizes in-place

// Arithmetic
public Vector add(Vector v)      // Vector addition
public Vector sub(Vector v)      // Vector subtraction
public float dot(Vector v)       // Dot product
public Vector cross(Vector v)    // Cross product
public Vector scale(float s)     // Scalar multiplication

// Transformation
public Vector transform(Matrix4 m) // Transform by matrix

// Utility
public float[] toFloatArray()    // [x, y, z] array
public Vector clone()            // Deep copy
public boolean equals(Object o)  // Equality check
public static Vector lerp(Vector a, Vector b, float t) // Linear interpolation
public String toString()         // String representation

// Setters/Getters
public void SetVector(Vector v)
public void setX(float x)
public void setY(float y)
public void setZ(float z)
public float getX()
public float getY()
public float getZ()

// Static
public static float Distance(Vector a, Vector b) // Euclidean distance
```

## Usage Example
```java
Vector a = new Vector(1, 2, 3);
Vector b = new Vector(4, 5, 6);
Vector sum = a.add(b); // (5,7,9)
float len = a.magnitude();
Vector dir = a.normal();
float d = Vector.Distance(a, b);
```

## Best Practices
- Use `normal()` for safe normalization (returns new vector)
- Use `normalize()` to modify in-place
- Use `lerp()` for smooth interpolation
- Always check for zero magnitude before dividing

## Notes
- All fields are public for performance and editor access
- Use `toString()` for debug printing
