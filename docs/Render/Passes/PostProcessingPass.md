# PostProcessingPass Class

The `PostProcessingPass` class is a specialized render pass that handles post-processing effects in Pi-Engine's rendering pipeline. It inherits from `RenderPass` and uses a fullscreen triangle technique for efficient screen-space effects.

## Overview

PostProcessingPass applies screen-space effects to previously rendered content using fragment shaders. It efficiently renders a single fullscreen triangle that covers the entire viewport, making it perfect for implementing effects like color grading, bloom, blur, CRT effects, and other post-processing techniques.

## Class Details

### Inheritance
- Extends: `RenderPass`

## Properties

| Name | Type | Description |
|------|------|-------------|
| `fullscreenVAO` | `int` | Vertex Array Object for the fullscreen triangle |
| `fullscreenVBO` | `int` | Vertex Buffer Object for the fullscreen triangle |

## Constructors

| Constructor | Parameters | Description |
|------------|------------|-------------|
| `PostProcessingPass` | | Creates a default post-processing pass with CRT effect shader |
| `PostProcessingPass` | `String name, Shader shader, int width, int height, int itextures` | Creates a custom post-processing pass |

## Methods

### Rendering and Resources

| Method | Parameters | Return Type | Description |
|--------|------------|-------------|-------------|
| `render` | `Camera camera, GameObject scene` | `void` | Renders the post-processing effect |
| `dispose` | | `void` | Cleans up OpenGL resources |

### Private Methods

| Method | Return Type | Description |
|--------|-------------|-------------|
| `setupFullscreenTriangle` | `void` | Sets up the VAO/VBO for the fullscreen triangle |

## Default Configuration

### Shader Files
- Vertex Shader: `Shaders/PostProcess/SCREEN.vert`
- Fragment Shader: `Shaders/PostProcess/CRT.frag`
- Default Resolution: 800x600
- Input Textures: 2

## Fullscreen Triangle Technique

Uses a single oversized triangle instead of a quad for better performance:
```
(-1,-1)        (3,-1)
   |\           |
   | \          |
   |  \         |
   |   \        |
   |    \       |
   |     \      |
(-1,3)    \     |
```

## Usage Example

```java
// Create a custom post-processing pass
Shader bloomShader = new Shader(
    "screen.vert",
    "bloom.frag",
    null
);
PostProcessingPass bloomPass = new PostProcessingPass(
    "BloomEffect",
    bloomShader,
    1920,
    1080,
    2  // Number of input textures
);

// Add to renderer
renderer.addPass(bloomPass);

// Connect input textures
renderer.connect(geometryPass.getName(), bloomPass.getName(), 0);
renderer.connect(brightPass.getName(), bloomPass.getName(), 1);

// Clean up
bloomPass.dispose();
```

## Example Shaders

### Screen Vertex Shader
```glsl
#version 330 core
layout (location = 0) in vec2 a_Position;

out vec2 v_TexCoord;

void main() {
    gl_Position = vec4(a_Position, 0.0, 1.0);
    v_TexCoord = (a_Position + 1.0) * 0.5;
}
```

### Post-Processing Fragment Shader
```glsl
#version 330 core
in vec2 v_TexCoord;

uniform sampler2D u_Texture0;  // Main scene
uniform sampler2D u_Texture1;  // Additional data (e.g., bloom)
uniform float u_Time;

out vec4 FragColor;

void main() {
    vec4 color = texture(u_Texture0, v_TexCoord);
    vec4 bloom = texture(u_Texture1, v_TexCoord);
    
    // Apply post-processing effect
    FragColor = color + bloom;
}
```

## Best Practices
- Use appropriate shader uniforms for effect parameters
- Consider performance impact of complex effects
- Handle multiple input textures properly
- Clean up resources with dispose()
- Use efficient shader code for real-time effects
- Consider frame timing for animated effects
- Cache uniform locations for better performance

## See Also
- [RenderPass](../RenderPass.md)
- [GeometryPass](GeometryPass.md)
- [Shader](../Shader.md)
- [Framebuffer](../Framebuffer.md)
