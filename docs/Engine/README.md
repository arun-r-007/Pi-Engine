# Engine Package

The Engine package contains the core scene management, serialization, and utility classes for Pi-Engine. These classes handle scene setup, saving/loading, and logging, forming the backbone of the engine's runtime and editor integration.

## Components

| Class | Description |
|-------|-------------|
| [Scene](Scene.md) | Main scene manager, handles cameras, game objects, and rendering |
| [Console](Console.md) | Logging and message system for engine and editor |
| [SceneSerializerJSON](SceneSerializerJSON.md) | Serializes scenes and game objects to JSON |
| [SceneDeserializerJSON](SceneDeserializerJSON.md) | Loads scenes and game objects from JSON |

## Usage Example

```java
// Access the main scene
Scene scene = Scene.getInstance();
scene.init(windowHandle, width, height);
scene.update();
scene.render();

// Save and load scenes
scene.Save("MyScene.json");
scene.Load("MyScene.json");

// Log messages
Console.log("Engine started");
Console.error("Something went wrong!");
```

## Best Practices
- Use Scene.getInstance() for global scene access
- Always save scenes before making major changes
- Use Console for all logging and error reporting
- Validate scene data before serialization/deserialization

## See Also
- [Core Package](../Core/README.md)
- [GameObject Package](../GameObject/README.md)
- [Render Package](../Render/README.md)
