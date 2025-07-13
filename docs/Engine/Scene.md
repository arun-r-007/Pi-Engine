# Scene Class

The `Scene` class is the central manager for all game objects, cameras, and rendering in Pi-Engine. It coordinates the editor and game cameras, manages the root game object hierarchy, and controls the rendering pipeline for both editor and game views.

## Overview

Scene acts as a singleton, providing global access to the current scene. It handles initialization, update, rendering, saving, and loading of the scene, and integrates with the editor and renderer subsystems.

## Properties

| Name | Type | Description |
|------|------|-------------|
| `Name` | `String` | The name of the scene |
| `root` | `GameObject` | The root game object of the scene |
| `editorCamera` | `Camera` | The camera used for editor view |
| `GameCamera` | `GameObject` | The main camera game object for gameplay |
| `sceneRenderer` | `Renderer` | Renderer for the editor view |
| `gameRenderer` | `Renderer` | Renderer for the game view |
| `editorSceneWindow` | `SceneWindow` | Editor scene window |
| `gameSceneWindow` | `SceneWindow` | Game scene window |
| `editor` | `Editor` | Editor instance |
| `windowHandle` | `long` | Native window handle |

## Methods

| Method | Parameters | Return Type | Description |
|--------|------------|-------------|-------------|
| `getInstance` | | `Scene` | Gets the singleton instance of the scene |
| `init` | `long window, int width, int height` | `void` | Initializes the scene, cameras, editor, and renderers |
| `update` | | `void` | Updates the editor camera and root game object |
| `render` | | `void` | Renders the scene and game views |
| `Save` | `String Filename` | `void` | Saves the scene to a JSON file |
| `Load` | `String FileName` | `void` | Loads the scene from a JSON file |
| `getRoot` | | `GameObject` | Gets the root game object |
| `getEditorCamera` | | `Camera` | Gets the editor camera |
| `getGameCamera` | | `GameObject` | Gets the game camera object |
| `getSceneRenderer` | | `Renderer` | Gets the editor renderer |
| `getGameRenderer` | | `Renderer` | Gets the game renderer |
| `getEditor` | | `Editor` | Gets the editor instance |
| `getEditorSceneWindow` | | `SceneWindow` | Gets the editor scene window |
| `getGameSceneWindow` | | `SceneWindow` | Gets the game scene window |
| `getWindowHandle` | | `long` | Gets the window handle |
| `setName` | `String name` | `void` | Sets the scene name |
| `setRoot` | `GameObject root` | `void` | Sets the root game object |
| `setEditorCamera` | `Camera editorCamera` | `void` | Sets the editor camera |
| `setGameCamera` | `GameObject gameCamera` | `void` | Sets the game camera object |
| `setSceneRenderer` | `Renderer sceneRenderer` | `void` | Sets the editor renderer |
| `setGameRenderer` | `Renderer gameRenderer` | `void` | Sets the game renderer |
| `setEditor` | `Editor editor` | `void` | Sets the editor instance |
| `setEditorSceneWindow` | `SceneWindow editorSceneWindow` | `void` | Sets the editor scene window |
| `setGameSceneWindow` | `SceneWindow gameSceneWindow` | `void` | Sets the game scene window |
| `setWindowHandle` | `long windowHandle` | `void` | Sets the window handle |
| `setInstance` | `Scene instance` | `void` | Sets the singleton instance |

## Usage Example

```java
// Initialize and use the scene
Scene scene = Scene.getInstance();
scene.init(windowHandle, 1920, 1080);
scene.update();
scene.render();
scene.Save("MyScene.json");
scene.Load("MyScene.json");
```

## Best Practices
- Use Scene.getInstance() for global access
- Always initialize the scene before rendering
- Save the scene before making major changes
- Use separate renderers for editor and game views
- Integrate with the editor for advanced workflows

## See Also
- [GameObject](../GameObject/GameObject.md)
- [Renderer](../Render/Renderer.md)
- [Editor](../Editor/Editor.md)
- [SceneSerializerJSON](SceneSerializerJSON.md)
- [SceneDeserializerJSON](SceneDeserializerJSON.md)
