# SceneDeserializerJSON Class

The `SceneDeserializerJSON` class loads scenes and game objects from JSON files, reconstructing the hierarchy and component data. It uses Gson for parsing and supports deferred property setting for complex components.

## Overview

SceneDeserializerJSON provides static methods for loading a scene from a JSON file, rebuilding game objects, components, and transforms. It supports both recursive and deferred deserialization for flexible scene loading.

## Methods

| Method | Parameters | Return Type | Description |
|--------|------------|-------------|-------------|
| `deserialize` | `String filePath` | `Scene` | Loads a scene from a JSON file |

### Internal Methods

| Method | Parameters | Return Type | Description |
|--------|------------|-------------|-------------|
| `deserializeGameObject` | `JsonObject jsonObject` | `GameObject` | Loads a game object and its children from JSON |
| `parseVector` | `JsonObject obj` | `Vector` | Parses a vector from JSON |

## Usage Example

```java
// Load a scene from file
Scene scene = SceneDeserializerJSON.deserialize("MyScene.json");
```

## Best Practices
- Use deferred property setting for complex components
- Validate JSON structure before loading
- Clear deferred maps after loading
- Extend for custom deserialization needs

## See Also
- [Scene](Scene.md)
- [SceneSerializerJSON](SceneSerializerJSON.md)
- [GameObject](../GameObject/GameObject.md)
