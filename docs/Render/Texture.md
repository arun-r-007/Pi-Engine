# Texture Class

The `Texture` class manages OpenGL textures in Pi-Engine, providing functionality for creating, binding, and managing 2D textures. It implements the `GUIDProvider` interface for unique identification.

## Overview

The Texture class handles the creation and management of 2D textures in OpenGL, supporting RGBA format with customizable filtering options and mipmap generation. It provides methods for binding/unbinding textures and managing texture resources.

## Properties

| Name | Type | Description |
|------|------|-------------|
| `textureID` | `int` | OpenGL texture ID |
| `width` | `int` | Width of the texture in pixels |
| `height` | `int` | Height of the texture in pixels |
| `minFilter` | `int` | Texture minification filter |
| `magFilter` | `int` | Texture magnification filter |
| `GUID` | `String` | Globally Unique Identifier for the texture |

## Constructor

| Constructor | Parameters | Description |
|------------|------------|-------------|
| `Texture` | `int[] imageData, int width, int height, int minFilter, int magFilter` | Creates a texture from raw RGBA pixel data with specified dimensions and filtering |

## Methods

### Texture Management

| Method | Parameters | Return Type | Description |
|--------|------------|-------------|-------------|
| `bind` | | `void` | Binds the texture for rendering |
| `unbind` | | `void` | Unbinds the texture |
| `dispose` | | `void` | Deletes the texture and frees GPU memory |

### Getters and Setters

| Method | Parameters | Return Type | Description |
|--------|------------|-------------|-------------|
| `getTextureID` | | `int` | Gets the OpenGL texture ID |
| `getWidth` | | `int` | Gets the texture width |
| `getHeight` | | `int` | Gets the texture height |
| `setGUID` | `String guid` | `void` | Sets the texture's GUID |
| `getGUID` | | `String` | Gets the texture's GUID |

### Private Methods

| Method | Parameters | Return Type | Description |
|--------|------------|-------------|-------------|
| `createTexture` | `int[] imageData` | `int` | Creates an OpenGL texture from pixel data |

## Usage Example

```java
// Create a texture with custom filtering
int[] pixelData = new int[width * height]; // RGBA pixel data
Texture texture = new Texture(
    pixelData,
    width,
    height,
    GL11.GL_LINEAR_MIPMAP_LINEAR, // min filter
    GL11.GL_LINEAR                // mag filter
);

// Set a unique identifier
texture.setGUID("texture_" + UUID.randomUUID().toString());

// Use the texture for rendering
texture.bind();
// ... rendering code ...
texture.unbind();

// Clean up when done
texture.dispose();
```

## OpenGL Settings

The texture is created with the following OpenGL parameters:
- Format: RGBA8
- Wrap Mode: GL_REPEAT for both S and T coordinates
- Mipmaps: Generated automatically
- Min/Mag Filter: Customizable through constructor

## Best Practices
- Always dispose of textures when no longer needed
- Use appropriate filter settings for your needs:
  - GL_NEAREST for pixel art
  - GL_LINEAR for smooth scaling
  - GL_LINEAR_MIPMAP_LINEAR for high-quality downscaling
- Keep track of texture dimensions for proper UV mapping
- Use GUIDs for texture asset management
- Batch texture operations for better performance
- Unbind textures after use to prevent state leaks

## See Also
- [Shader](Shader.md)
- [RenderPass](RenderPass.md)
- [Renderer](Renderer.md)
