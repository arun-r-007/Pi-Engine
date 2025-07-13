# Render Package

The Render package provides the core rendering functionality for Pi-Engine, implementing a modern rendering pipeline with support for multiple render passes, shaders, textures, and meshes.

## Components

| Class | Description |
|-------|-------------|
| [Renderer](Renderer.md) | Core rendering system that manages the rendering pipeline |
| [RenderPass](RenderPass.md) | Base class for render passes in the pipeline |
| [RenderPassFactory](RenderPassFactory.md) | Factory for creating and managing render passes |
| [Shader](Shader.md) | Shader program management and compilation |
| [Texture](Texture.md) | Texture loading and management |
| [Mesh](Mesh.md) | 3D mesh data structure and rendering |
| [Framebuffer](Framebuffer.md) | Framebuffer object for off-screen rendering |

### Render Passes
| Pass | Description |
|------|-------------|
| [GeometryPass](Passes/GeometryPass.md) | Main geometry rendering pass |
| [PostProcessingPass](Passes/PostProcessingPass.md) | Post-processing effects pass |

## Usage Example

```java
// Create renderer and setup render passes
Renderer renderer = new Renderer();
renderer.addPass(new GeometryPass());
renderer.addPass(new PostProcessingPass());

// Load and use a shader
Shader shader = new Shader("vertex.glsl", "fragment.glsl");
shader.bind();

// Load and use a texture
Texture texture = new Texture("texture.png");
texture.bind();

// Create and render a mesh
Mesh mesh = new Mesh(vertices, indices);
mesh.render();
```

## Best Practices
- Use RenderPassFactory to create and manage render passes
- Properly dispose of resources (shaders, textures, meshes, framebuffers)
- Use appropriate render passes for different rendering needs
- Follow the rendering pipeline order for correct visual results

## See Also
- [Core Package](../Core/README.md)
- [Components Package](../Components/README.md)
