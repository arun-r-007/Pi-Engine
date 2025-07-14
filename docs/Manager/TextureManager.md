# TextureManager Class

The TextureManager is a singleton class that handles loading, caching, and managing texture resources in the Pi-Engine.

## Class Definition

```java
public class TextureManager
```

## Properties

| Property | Type | Description |
|----------|------|-------------|
| `instance` | `TextureManager` | Singleton instance of TextureManager |
| `textures` | `Map<String, Texture>` | Cache of loaded textures by GUID |

## Methods

### Instance Management

| Method | Parameters | Return Type | Description |
|--------|------------|-------------|-------------|
| `get()` | None | `TextureManager` | Static method to get or create the singleton instance |

### Texture Management

| Method | Parameters | Return Type | Description |
|--------|------------|-------------|-------------|
| `load(Path filePath, String guid)` | `Path filePath, String guid` | `void` | Loads a texture from the given path and stores it with the provided GUID |
| `getByGUID(String guid)` | `String guid` | `Texture` | Retrieves a loaded texture by its GUID |

## Usage Example

```java
// Get TextureManager instance
TextureManager textureManager = TextureManager.get();

// Load a texture
textureManager.load(Paths.get("textures/sprite.png"), "sprite_guid");

// Retrieve a texture
Texture texture = textureManager.getByGUID("sprite_guid");
```

## Loading Process

1. Checks if the texture is already loaded using the GUID
2. If not loaded:
   - Uses TextureLoader to load the texture file
   - Sets OpenGL filtering parameters (GL_NEAREST by default)
   - Assigns the GUID to the texture
   - Caches the texture in both TextureManager and AssetManager

## Best Practices

- Always use GUIDs consistently across the application
- Reuse loaded textures instead of loading multiple copies
- Handle missing textures gracefully
- Clean up textures when no longer needed
- Use appropriate texture filtering based on your needs

## See Also

- [Texture](../Render/Texture.md)
- [AssetManager](AssetManager.md)
- [TextureLoader](../IO/TextureLoader.md)
