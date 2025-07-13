# Framebuffer Class

The `Framebuffer` class manages OpenGL framebuffer objects (FBOs) in Pi-Engine, providing functionality for off-screen rendering and post-processing effects. Each framebuffer includes a color texture attachment and a depth renderbuffer.

## Overview

The Framebuffer class handles the creation and management of OpenGL framebuffers, which are essential for techniques like render-to-texture, post-processing effects, and multi-pass rendering. It provides a complete interface for creating, binding, and managing framebuffer resources.

## Properties

| Name | Type | Description |
|------|------|-------------|
| `fboId` | `int` | OpenGL framebuffer object ID |
| `colorTexture` | `int` | Color attachment texture ID |
| `depthRenderbuffer` | `int` | Depth renderbuffer ID |
| `width` | `int` | Framebuffer width in pixels |
| `height` | `int` | Framebuffer height in pixels |

## Constructor

| Constructor | Parameters | Description |
|------------|------------|-------------|
| `Framebuffer` | `int width, int height` | Creates a new framebuffer with specified dimensions |

## Methods

### Framebuffer Management

| Method | Parameters | Return Type | Description |
|--------|------------|-------------|-------------|
| `bind` | | `void` | Binds the framebuffer for rendering |
| `unbind` | | `void` | Unbinds the framebuffer |
| `resize` | `int newWidth, int newHeight` | `void` | Resizes the framebuffer and its attachments |
| `dispose` | | `void` | Deletes all OpenGL resources |

### Getters

| Method | Return Type | Description |
|--------|-------------|-------------|
| `getTextureId` | `int` | Gets the color attachment texture ID |
| `getWidth` | `int` | Gets the framebuffer width |
| `getHeight` | `int` | Gets the framebuffer height |

### Private Methods

| Method | Return Type | Description |
|--------|-------------|-------------|
| `createFramebuffer` | `void` | Creates the OpenGL framebuffer and attachments |

## Attachments

### Color Attachment
- Format: RGBA8
- Filtering: GL_NEAREST
- Type: GL_UNSIGNED_BYTE
- Attachment Point: GL_COLOR_ATTACHMENT0

### Depth Attachment
- Format: GL_DEPTH_COMPONENT
- Type: Renderbuffer
- Attachment Point: GL_DEPTH_ATTACHMENT

## Usage Example

```java
// Create a framebuffer
Framebuffer fbo = new Framebuffer(1920, 1080);

// Render to framebuffer
fbo.bind();
// Clear the framebuffer
glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);

// Render your scene
shader.use();
shader.setUniformMat4("u_ProjectionMatrix", projectionMatrix);
mesh.render();

// Return to default framebuffer
fbo.unbind();

// Use the rendered result as a texture
glBindTexture(GL_TEXTURE_2D, fbo.getTextureId());
postProcessShader.use();
screenQuad.render();

// Handle window resize
fbo.resize(newWidth, newHeight);

// Clean up
fbo.dispose();
```

## Best Practices
- Always check framebuffer completeness after creation/resize
- Properly unbind after use to avoid state leaks
- Handle resizing appropriately for window resizes
- Dispose of framebuffers when no longer needed
- Use appropriate texture formats for your needs
- Consider using multiple render targets for advanced effects
- Clear both color and depth when beginning a new frame
- Manage framebuffer state carefully in multi-pass rendering

## See Also
- [RenderPass](RenderPass.md)
- [Renderer](Renderer.md)
- [Shader](Shader.md)
- [Texture](Texture.md)
