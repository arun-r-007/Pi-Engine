# Renderer Class

The `Renderer` class is the core rendering system in Pi-Engine that manages the rendering pipeline. It handles multiple render passes and their connections, allowing for complex rendering effects through a flexible and modular architecture.

## Overview

The Renderer manages a collection of render passes and their connections, forming a rendering pipeline. It supports dynamic configuration of passes and their relationships, making it suitable for implementing various rendering techniques from basic forward rendering to advanced deferred rendering and post-processing effects.

## Properties

| Name | Type | Description |
|------|------|-------------|
| `passes` | `Map<String, RenderPass>` | Collection of all render passes in the pipeline |
| `connections` | `Map<String, Map<Integer, String>>` | Connections between render passes |
| `finalPassName` | `String` | Name of the final render pass in the pipeline |

## Methods

### Pipeline Management

| Method | Parameters | Return Type | Description |
|--------|------------|-------------|-------------|
| `addPass` | `RenderPass pass` | `void` | Adds a new render pass to the pipeline |
| `removePass` | `String passName` | `void` | Removes a render pass and its connections |
| `updatePassName` | `String oldName, String newName` | `void` | Updates the name of a render pass |
| `connect` | `String fromPassName, String toPassName, int inputIndex` | `void` | Connects two render passes |
| `disconnect` | `String toPassName, int inputIndex` | `void` | Disconnects an input from a render pass |
| `setFinalPass` | `String name` | `void` | Sets the final pass in the pipeline |

### Rendering

| Method | Parameters | Return Type | Description |
|--------|------------|-------------|-------------|
| `renderPipeline` | `Camera camera, GameObject scene` | `void` | Executes the entire rendering pipeline |
| `getFinalTexture` | | `int` | Gets the texture ID of the final pass output |
| `getFinalFramebuffer` | | `Framebuffer` | Gets the framebuffer of the final pass |

### Getters

| Method | Return Type | Description |
|--------|-------------|-------------|
| `getPasses` | `Map<String, RenderPass>` | Gets all render passes in the pipeline |
| `getConnections` | `Map<String, Map<Integer, String>>` | Gets all connections between passes |

## Usage Example

```java
// Create a new renderer
Renderer renderer = new Renderer();

// Add render passes
GeometryPass geometryPass = new GeometryPass();
PostProcessingPass postProcess = new PostProcessingPass();

renderer.addPass(geometryPass);
renderer.addPass(postProcess);

// Connect passes (geometry -> post-processing)
renderer.connect(geometryPass.getName(), postProcess.getName(), 0);

// Set the final pass
renderer.setFinalPass(postProcess.getName());

// Render a frame
renderer.renderPipeline(camera, scene);

// Get the final rendered texture
int finalTexture = renderer.getFinalTexture();
```

## Best Practices
- Add render passes in the correct order based on their dependencies
- Ensure all pass connections are properly set up before rendering
- Set the final pass to specify which pass's output should be displayed
- Clean up render passes when they are no longer needed

## See Also
- [RenderPass](RenderPass.md)
- [RenderPassFactory](RenderPassFactory.md)
- [GeometryPass](Passes/GeometryPass.md)
- [PostProcessingPass](Passes/PostProcessingPass.md)
- [Camera](../Core/Camera.md)
- [GameObject](../GameObject/GameObject.md)
