# Math Package Documentation

## Overview
The Math package provides essential mathematical utilities for 3D graphics and game development. It includes three main classes that handle different aspects of mathematical operations:

| Class | Purpose |
|-------|----------|
| [MathF](MathF.md) | General mathematical utilities and operations |
| [Vector](Vector.md) | 3D vector mathematics and transformations |
| [Matrix4](Matrix4.md) | 4x4 matrix operations for 3D transformations |

## Quick Links
- [MathF Documentation](MathF.md)
- [Vector Documentation](Vector.md)
- [Matrix4 Documentation](Matrix4.md)

## Common Use Cases

### Transform Handling
```java
// Create world transform
Matrix4 translation = Matrix4.translate(new Vector(1, 2, 3));
Matrix4 rotation = Matrix4.rotate(45, new Vector(0, 1, 0));
Matrix4 scale = Matrix4.scale(new Vector(2, 2, 2));

// Combine transforms
Matrix4 worldMatrix = Matrix4.multiply(translation, 
                     Matrix4.multiply(rotation, scale));

// Transform a point
Vector point = new Vector(1, 0, 0);
Vector transformed = point.transform(worldMatrix);
```

### Vector Operations
```java
// Basic vector math
Vector a = new Vector(1, 0, 0);
Vector b = new Vector(0, 1, 0);
Vector sum = a.add(b);
Vector diff = a.sub(b);
float dot = a.dot(b);
Vector cross = a.cross(b);

// Normalization
Vector dir = b.sub(a).normal();
```

### Value Interpolation
```java
// Linear interpolation
float t = 0.5f;
float value = MathF.lerp(0, 100, t);
Vector pos = Vector.lerp(startPos, endPos, t);
```

## Best Practices

1. Use appropriate data types:
   - `Vector` for positions, directions, and normals
   - `Matrix4` for transforms and projections
   - `MathF` for general mathematical operations

2. Performance optimization:
   - Cache frequently used calculations
   - Use `sqMagnitude()` instead of `magnitude()` for comparisons
   - Minimize matrix operations in tight loops

3. Numerical stability:
   - Normalize vectors after transformations
   - Check for division by zero
   - Use `clamp()` for bounded values

4. Memory management:
   - Reuse vectors and matrices when possible
   - Use in-place operations when appropriate
   - Cache transform matrices that don't change often

## Integration with Engine

The Math package integrates with various engine systems:

1. **Transform Component**
   - Uses `Matrix4` for world transforms
   - Uses `Vector` for position, rotation, scale

2. **Camera System**
   - Uses `Matrix4` for view and projection matrices
   - Uses `Vector` for camera position and direction

3. **Physics System**
   - Uses `Vector` for velocities and forces
   - Uses `MathF` for interpolation and clamping

4. **Graphics Pipeline**
   - Uses `Matrix4` for MVP (Model-View-Projection) matrices
   - Uses `Vector` for vertices and normals

## Example Workflows

### Camera Setup
```java
// Create camera matrices
float fov = 60.0f;
float aspect = width / height;
Matrix4 projection = Matrix4.perspective(fov, aspect, 0.1f, 1000.0f);

Vector eye = new Vector(0, 5, -10);
Vector target = new Vector(0, 0, 0);
Vector up = new Vector(0, 1, 0);
Matrix4 view = createViewMatrix(eye, target, up);
```

### Object Movement
```java
// Smooth movement
Vector currentPos = object.getPosition();
Vector targetPos = new Vector(10, 0, 0);
float maxDelta = 0.1f;

// Update each frame
float x = MathF.moveTowards(currentPos.x, targetPos.x, maxDelta);
float y = MathF.moveTowards(currentPos.y, targetPos.y, maxDelta);
float z = MathF.moveTowards(currentPos.z, targetPos.z, maxDelta);
object.setPosition(new Vector(x, y, z));
```

## Further Reading
- [Transform Component Documentation](../Component.md)
- [Camera Documentation](../Camera.md)
- [Renderer Documentation](../Renderer.md)
