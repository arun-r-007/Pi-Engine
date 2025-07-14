# GUID Class

The GUID class provides functionality for generating globally unique identifiers from file paths in the Pi-Engine.

## Class Definition

```java
public class GUID
```

## Methods

### GUID Generation

| Method | Parameters | Return Type | Description |
|--------|------------|-------------|-------------|
| `generateGUIDFromPath(String path)` | `String path` | `String` | Generates a 16-character GUID from a file path |

## Generation Process

1. Path is normalized using `Paths.get(path).normalize()`
2. SHA-256 hash is generated from the normalized path
3. Hash bytes are converted to a hexadecimal string
4. First 16 characters are used as the GUID

## Usage Example

```java
// Generate a GUID from a file path
String path = "assets/textures/sprite.png";
String guid = GUID.generateGUIDFromPath(path);
// Returns something like: "a1b2c3d4e5f6g7h8"
```

## Best Practices

- Always normalize paths before GUID generation
- Use consistent path separators
- Handle null returns from failed generations
- Cache GUIDs for frequently accessed paths
- Use relative paths for consistent GUIDs across systems

## Error Handling

- Returns null if SHA-256 algorithm is unavailable
- Logs stack trace for algorithm errors
- Guarantees consistent results for identical paths
- Handles path normalization differences

## See Also

- [GUIDProvider](GUIDProvider.md)
- [AssetManager](../Manager/AssetManager.md)
- [TextureManager](../Manager/TextureManager.md)
