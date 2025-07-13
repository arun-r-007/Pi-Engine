# RenderPass Class

The `RenderPass` class is an abstract base class that defines the structure and behavior of render passes in Pi-Engine's rendering pipeline. Each render pass represents a distinct stage in the rendering process, with its own shader, framebuffer, and input/output textures.

## Overview

A RenderPass handles a specific rendering task, such as geometry rendering, post-processing, or other visual effects. It manages input textures, shader uniforms, and framebuffer operations required for its rendering stage.

## Properties

| Name | Type | Description |
|------|------|-------------|
| `name` | `String` | Unique identifier for the render pass |
| `shader` | `Shader` | Shader program used by this pass |
| `framebuffer` | `Framebuffer` | Framebuffer for off-screen rendering |
| `inputTextures` | `int[]` | Array of input texture IDs |
| `inputCount` | `int` | Number of input textures required |
| `width` | `int` | Width of the render target |
| `height` | `int` | Height of the render target |
| `layerMask` | `int` | Bit mask for layer-based rendering (default: 0xFFFFFFFF) |

## Constructor

| Constructor | Parameters | Description |
|------------|------------|-------------|
| `RenderPass` | `String name, Shader shader, int width, int height, int inputCount` | Creates a new render pass with the specified parameters |

## Methods

### Input/Output Management

| Method | Parameters | Return Type | Description |
|--------|------------|-------------|-------------|
| `setInputTexture` | `int index, int textureId` | `void` | Sets an input texture at the specified index |
| `getInputTexture` | `int index` | `int` | Gets the input texture ID at the specified index |
| `clearInputTexture` | `int index` | `void` | Clears the input texture at the specified index |
| `getInputCount` | | `int` | Gets the number of input textures |
| `getOutputTexture` | | `int` | Gets the output texture ID |
| `getFramebuffer` | | `Framebuffer` | Gets the framebuffer object |

### Rendering Operations

| Method | Parameters | Return Type | Description |
|--------|------------|-------------|-------------|
| `bindAndPrepare` | | `void` | Sets up the render pass for rendering |
| `render` | `Camera camera, GameObject scene` | `void` | Abstract method to perform the actual rendering |
| `unbindFramebuffer` | | `void` | Unbinds the framebuffer after rendering |
| `resize` | `int width, int height` | `void` | Resizes the render target |

### Configuration

| Method | Parameters | Return Type | Description |
|--------|------------|-------------|-------------|
| `setLayerMask` | `int mask` | `void` | Sets the layer mask for filtering objects |
| `getLayerMask` | | `int` | Gets the current layer mask |
| `setName` | `String name` | `void` | Sets the render pass name |
| `getName` | | `String` | Gets the render pass name |

## Usage Example

```java
// Create a custom render pass
public class CustomPass extends RenderPass {
    public CustomPass(int width, int height) {
        super("CustomPass", 
              new Shader("custom.vert", "custom.frag"), 
              width, height, 1);
    }

    @Override
    public void render(Camera camera, GameObject scene) {
        bindAndPrepare();
        
        // Set custom uniforms
        shader.setUniformMat4("u_ViewProjection", camera.getViewProjectionMatrix());
        
        // Render objects
        // ...
        
        unbindFramebuffer();
    }
}

// Use the custom pass
CustomPass customPass = new CustomPass(1920, 1080);
customPass.setLayerMask(0x0F); // Only render specific layers
renderer.addPass(customPass);
```

## Best Practices
- Clear input textures after use to prevent texture binding issues
- Handle resizing properly to maintain render quality
- Use appropriate layer masks to control what objects are rendered
- Properly manage shader uniforms in the render method
- Clean up resources when the pass is no longer needed

## See Also
- [Renderer](Renderer.md)
- [Shader](Shader.md)
- [Framebuffer](Framebuffer.md)
- [Camera](../Core/Camera.md)
- [GameObject](../GameObject/GameObject.md)
