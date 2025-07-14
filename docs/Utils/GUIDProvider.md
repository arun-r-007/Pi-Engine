# GUIDProvider Interface

The GUIDProvider interface defines the contract for objects that need GUID-based identification in the Pi-Engine.

## Interface Definition

```java
public interface GUIDProvider
```

## Methods

| Method | Parameters | Return Type | Description |
|--------|------------|-------------|-------------|
| `getGUID()` | None | `String` | Gets the object's GUID |
| `setGUID(String guid)` | `String guid` | `void` | Sets the object's GUID |

## Implementation Example

```java
public class Texture implements GUIDProvider {
    private String GUID;

    @Override
    public String getGUID() {
        return GUID;
    }

    @Override
    public void setGUID(String guid) {
        this.GUID = guid;
    }
}
```

## Use Cases

- Resource identification (textures, shaders, etc.)
- Asset management and caching
- Serialization and deserialization
- Cross-reference resolution
- Resource deduplication

## Best Practices

- Generate GUIDs using the GUID utility class
- Keep GUIDs consistent across sessions
- Never modify GUIDs after initial assignment
- Handle null GUIDs gracefully
- Use GUIDs for equality comparisons

## See Also

- [GUID](GUID.md)
- [AssetManager](../Manager/AssetManager.md)
- [Texture](../Render/Texture.md)
