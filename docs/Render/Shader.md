# Shader Class

The `Shader` class manages OpenGL shader programs in Pi-Engine, providing functionality for loading, compiling, and using GLSL shaders. It supports vertex, fragment, and geometry shaders with uniform variable management.

## Overview

The Shader class handles the compilation and linking of GLSL shader programs and provides methods for setting various types of uniform variables. It supports all common shader types and provides error checking during shader compilation and linking.

## Properties

| Name | Type | Description |
|------|------|-------------|
| `programId` | `int` | OpenGL program ID for the shader |

## Constructor

| Constructor | Parameters | Description |
|------------|------------|-------------|
| `Shader` | `String vertexPath, String fragmentPath, String geometryPath` | Creates a shader program from vertex, fragment, and optional geometry shader files |

## Methods

### Shader Management

| Method | Parameters | Return Type | Description |
|--------|------------|-------------|-------------|
| `use` | | `void` | Activates the shader program |
| `stop` | | `void` | Deactivates the shader program |
| `delete` | | `void` | Deletes the shader program |
| `getId` | | `int` | Gets the shader program ID |

### Uniform Setters

| Method | Parameters | Return Type | Description |
|--------|------------|-------------|-------------|
| `setUniform1i` | `String name, int value` | `void` | Sets an integer uniform |
| `setUniform1f` | `String name, float value` | `void` | Sets a float uniform |
| `setUniformMat4` | `String name, Matrix4 mat, boolean transpose` | `void` | Sets a 4x4 matrix uniform |
| `setUniformVec2` | `String name, Vector vec2` | `void` | Sets a vec2 uniform |
| `setUniformVec3` | `String name, Vector vec3` | `void` | Sets a vec3 uniform |
| `setUniformVec4` | `String name, float x, float y, float z, float w` | `void` | Sets a vec4 uniform |
| `setUniformBool` | `String name, boolean value` | `void` | Sets a boolean uniform |

### Private Methods

| Method | Parameters | Return Type | Description |
|--------|------------|-------------|-------------|
| `compileShader` | `String source, int type` | `int` | Compiles a shader from source code |

## Usage Example

```java
// Create a shader program with vertex and fragment shaders
Shader shader = new Shader(
    "shaders/basic.vert",
    "shaders/basic.frag",
    null  // No geometry shader
);

// Use the shader and set uniforms
shader.use();
shader.setUniformMat4("u_ProjectionMatrix", projectionMatrix, false);
shader.setUniformVec3("u_LightPosition", lightPos);
shader.setUniform1f("u_Intensity", 1.0f);

// Render with the shader
// ... rendering code ...

// Stop using the shader
shader.stop();

// Clean up when done
shader.delete();
```

## Best Practices
- Always check for shader compilation and linking errors
- Use meaningful uniform names that match the GLSL shader code
- Clean up shader resources by calling delete() when no longer needed
- Cache uniform locations for better performance in render loops
- Use appropriate uniform types matching GLSL declarations
- Handle shader loading errors gracefully

## GLSL Shader Example

```glsl
// vertex.glsl
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

// fragment.glsl
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
- [RenderPass](RenderPass.md)
- [Renderer](Renderer.md)
- [Matrix4](../Math/Matrix4.md)
- [Vector](../Math/Vector.md)
