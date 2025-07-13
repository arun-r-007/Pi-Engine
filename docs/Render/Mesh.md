# Mesh Class

The `Mesh` class manages vertex data and rendering for 3D meshes in Pi-Engine. It handles OpenGL vertex buffer objects (VBOs) and vertex array objects (VAOs) for efficient rendering of geometry.

## Overview

The Mesh class provides functionality for creating and managing 3D meshes with position and UV coordinates. It supports dynamic vertex updates and efficient rendering using OpenGL vertex arrays.

## Properties

| Name | Type | Description |
|------|------|-------------|
| `vao` | `int` | Vertex Array Object ID |
| `vbo` | `int` | Vertex Buffer Object ID |
| `vertexCount` | `int` | Number of vertices in the mesh |
| `vertices` | `float[]` | Raw vertex data (positions and UVs) |

## Constructor

| Constructor | Parameters | Description |
|------------|------------|-------------|
| `Mesh` | `float[] vertices` | Creates a mesh from vertex data array (format: x,y,z,u,v per vertex) |

## Methods

### Mesh Management

| Method | Parameters | Return Type | Description |
|--------|------------|-------------|-------------|
| `updateVertices` | `float[] newVertices` | `void` | Updates the mesh's vertex data |
| `render` | | `void` | Renders the mesh using GL_TRIANGLES |
| `dispose` | | `void` | Deletes the VAO and VBO |

## Vertex Format

Each vertex in the mesh consists of 5 floating-point values:
1. Position X (attribute 0)
2. Position Y (attribute 0)
3. Position Z (attribute 0)
4. Texture U (attribute 1)
5. Texture V (attribute 1)

## Usage Example

```java
// Create vertex data for a quad
float[] vertices = {
    // Position (XYZ)    // UV
    -0.5f, -0.5f, 0.0f, 0.0f, 0.0f, // Bottom-left
     0.5f, -0.5f, 0.0f, 1.0f, 0.0f, // Bottom-right
     0.5f,  0.5f, 0.0f, 1.0f, 1.0f, // Top-right
    -0.5f, -0.5f, 0.0f, 0.0f, 0.0f, // Bottom-left
     0.5f,  0.5f, 0.0f, 1.0f, 1.0f, // Top-right
    -0.5f,  0.5f, 0.0f, 0.0f, 1.0f  // Top-left
};

// Create the mesh
Mesh quadMesh = new Mesh(vertices);

// Render the mesh
shader.use();
texture.bind();
quadMesh.render();

// Update vertices dynamically if needed
float[] newVertices = // ... new vertex data ...
quadMesh.updateVertices(newVertices);

// Clean up when done
quadMesh.dispose();
```

## Vertex Attributes

| Attribute | Index | Size | Type | Stride | Offset | Description |
|-----------|-------|------|------|---------|---------|-------------|
| Position | 0 | 3 | GL_FLOAT | 20 bytes | 0 bytes | XYZ position |
| UV | 1 | 2 | GL_FLOAT | 20 bytes | 12 bytes | Texture coordinates |

## Best Practices
- Dispose of meshes when no longer needed
- Use dynamic vertex updates sparingly
- Batch similar meshes for better performance
- Keep vertex data in a consistent format
- Consider using index buffers for complex meshes
- Cache meshes that are used frequently
- Update vertices only when necessary

## See Also
- [Shader](Shader.md)
- [Texture](Texture.md)
- [Renderer](Renderer.md)
- [RenderPass](RenderPass.md)
