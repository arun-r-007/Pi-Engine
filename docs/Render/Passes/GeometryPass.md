# GeometryPass Class

The `GeometryPass` class is a specialized render pass that handles the main geometry rendering stage in Pi-Engine's rendering pipeline. It inherits from `RenderPass` and is responsible for rendering the scene's geometry with proper depth handling.

## Overview

GeometryPass represents the first stage in a typical rendering pipeline where the scene's geometry is rendered. It uses a default camera shader for standard rendering but can be configured with custom shaders for specialized effects.

## Class Details

### Inheritance
- Extends: `RenderPass`

## Constructors

| Constructor | Parameters | Description |
|------------|------------|-------------|
| `GeometryPass` | `String name, Shader shader, int width, int height` | Creates a geometry pass with custom parameters |
| `GeometryPass` | | Creates a default geometry pass with standard settings (1600x900, default camera shader) |

## Methods

### Rendering

| Method | Parameters | Return Type | Description |
|--------|------------|-------------|-------------|
| `render` | `Camera camera, GameObject scene` | `void` | Renders the scene geometry with depth handling |

## Default Configuration

### Shader Files
- Vertex Shader: `Shaders/Camera/Default.vert`
- Fragment Shader: `Shaders/Camera/Default.frag`
- Geometry Shader: None

### Dimensions
- Default Width: 1600 pixels
- Default Height: 900 pixels

### Input Textures
- Input Count: 0 (No input textures required)

## Rendering Process

1. Binds the framebuffer and prepares for rendering
2. Disables depth write (glDepthMask(false))
3. Renders the scene with layer masking
4. Re-enables depth write (glDepthMask(true))
5. Unbinds the framebuffer

## Usage Example

```java
// Create a custom geometry pass
Shader customShader = new Shader(
    "custom_vertex.glsl",
    "custom_fragment.glsl",
    null
);
GeometryPass geometryPass = new GeometryPass(
    "CustomGeometry",
    customShader,
    1920,
    1080
);

// Or use the default geometry pass
GeometryPass defaultPass = new GeometryPass();

// Add to renderer
renderer.addPass(geometryPass);

// Configure layer mask if needed
geometryPass.setLayerMask(0x0F);
```

## Best Practices
- Use appropriate shaders for your rendering needs
- Consider using layer masks to control what gets rendered
- Ensure proper depth testing configuration
- Handle resizing appropriately
- Manage shader uniforms properly in custom shaders
- Consider using multiple passes for complex effects

## Default Shader Example

```glsl
// Default.vert
#version 330 core
layout (location = 0) in vec3 a_Position;
layout (location = 1) in vec2 a_TexCoord;

uniform mat4 u_ProjectionMatrix;
uniform mat4 u_ViewMatrix;
uniform mat4 u_ModelMatrix;

out vec2 v_TexCoord;

void main() {
    gl_Position = u_ProjectionMatrix * u_ViewMatrix * u_ModelMatrix * vec4(a_Position, 1.0);
    v_TexCoord = a_TexCoord;
}

// Default.frag
#version 330 core
in vec2 v_TexCoord;

uniform sampler2D u_Texture;
uniform vec3 u_Color;

out vec4 FragColor;

void main() {
    FragColor = texture(u_Texture, v_TexCoord) * vec4(u_Color, 1.0);
}
```

## See Also
- [RenderPass](../RenderPass.md)
- [PostProcessingPass](PostProcessingPass.md)
- [Shader](../Shader.md)
- [Camera](../../Core/Camera.md)
