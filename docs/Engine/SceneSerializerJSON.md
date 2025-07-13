# SceneSerializerJSON Class

The `SceneSerializerJSON` class serializes scenes and game objects to JSON format for saving and exporting. It uses Gson for flexible, human-readable output and supports nested game object hierarchies and component data.

## Overview

SceneSerializerJSON provides static methods for converting the current scene and its game objects into a structured JSON file. It supports safe serialization of component properties and vector data, and can be extended for custom serialization needs.

## Methods

| Method | Parameters | Return Type | Description |
|--------|------------|-------------|-------------|
| `serialize` | `Scene scene, String filePath` | `void` | Serializes the scene to a JSON file |

### Internal Methods

| Method | Parameters | Return Type | Description |
|--------|------------|-------------|-------------|
| `serializeGameObject` | `GameObject gameObject, List<Object> outputList` | `void` | Serializes a game object and its children |
| `vectorToMap` | `Vector vector` | `Map<String, Float>` | Converts a vector to a map for JSON |
| `isSimpleType` | `Object value` | `boolean` | Checks if a value is a simple type for safe serialization |

## Usage Example

```java
// Save the current scene
Scene scene = Scene.getInstance();
SceneSerializerJSON.serialize(scene, "MyScene.json");
```

## Best Practices
- Use setPrettyPrinting for readable output (disable for compact files)
- Only serialize safe component properties
- Validate scene data before saving
- Extend for custom serialization needs

## See Also
- [Scene](Scene.md)
- [SceneDeserializerJSON](SceneDeserializerJSON.md)
- [GameObject](../GameObject/GameObject.md)
