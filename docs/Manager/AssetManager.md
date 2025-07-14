# AssetManager Class

The AssetManager is an abstract base class that handles loading, managing, and providing access to various game assets like textures, shaders, fonts, and scripts.

## Class Definition

```java
public abstract class AssetManager implements Runnable
```

## Properties

| Property | Type | Description |
|----------|------|-------------|
| `BASE_PATH` | `Path` | Base directory path for asset loading, normalized from `Main.ResourceFolder` |
| `resources` | `Map<String, Object>` | Static map storing all loaded resources by GUID |
| `generalAssetQueue` | `LinkedBlockingDeque<QueuedAsset>` | Queue for managing asset loading requests |

## Methods

### Asset Management

| Method | Parameters | Return Type | Description |
|--------|------------|-------------|-------------|
| `run()` | None | `void` | Implements Runnable interface. Walks through BASE_PATH and queues assets for loading |
| `processAssetQueue()` | None | `void` | Static method to process queued assets and load them based on type |
| `get(String guid)` | `String guid` | `Object` | Static method to retrieve an asset by its GUID |
| `put(String guid, Object asset)` | `String guid, Object asset` | `void` | Static method to store an asset with its GUID |

### Utility Methods

| Method | Parameters | Return Type | Description |
|--------|------------|-------------|-------------|
| `getExtension(String filename)` | `String filename` | `String` | Gets the file extension from a filename |

## Asset Types

The AssetManager supports the following asset types through the `AssetType` enum:

| Type | Description | File Extensions |
|------|-------------|-----------------|
| `TEXTURE` | Texture files | .png |
| `SHADER` | Shader programs | .frag, .vert, .glsl |
| `FONT` | Font files | .ttf |
| `SCENE` | Scene files | .j |
| `JAVA` | Java source files | .java |
| `CLASS` | Compiled Java classes | .class |
| `UNKNOWN` | Unsupported file types | * |

## Usage Example

```java
// Starting the asset manager in a separate thread
Thread assetThread = new Thread(() -> {
    AssetManager assetManager = new AssetManager() {};
    assetManager.run();
});
assetThread.start();

// Processing queued assets in the main loop
AssetManager.processAssetQueue();

// Retrieving an asset by GUID
Object asset = AssetManager.get(guid);

// Storing a new asset
AssetManager.put(guid, asset);
```

## Best Practices

- Always process the asset queue in the main game loop
- Use GUIDs consistently for asset identification
- Handle loading failures gracefully
- Clean up resources when no longer needed
- Use appropriate asset type handlers for each file type

## See Also

- [TextureManager](TextureManager.md)
- [ScriptLoader](../Scripting/ScriptLoader.md)
- [GUID](../Utils/GUID.md)
