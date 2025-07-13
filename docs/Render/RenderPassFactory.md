# RenderPassFactory Class

The `RenderPassFactory` class is a factory pattern implementation that manages the creation and registration of render passes in Pi-Engine. It uses reflection to automatically discover and register render pass classes, making it easy to extend the rendering pipeline with new effects.

## Overview

RenderPassFactory provides a centralized system for managing render pass types, allowing dynamic creation of render passes by name and automatic discovery of render pass implementations through package scanning.

## Properties

| Name | Type | Description |
|------|------|-------------|
| `passConstructors` | `Map<String, Supplier<RenderPass>>` | Static map of registered render pass constructors |

## Static Initialization
- Automatically scans and registers render passes from the `org.PiEngine.Render.Passes` package
- Uses reflection to discover `RenderPass` subclasses

## Methods

### Registration

| Method | Parameters | Return Type | Description |
|--------|------------|-------------|-------------|
| `registerRenderPassesFromPackage` | `String basePackage` | `void` | Scans and registers render passes from a package |
| `registerRenderPass` | `Class<? extends RenderPass> passClass` | `void` | Manually registers a render pass class |
| `register` | `String name, Supplier<RenderPass> constructor` | `void` | Registers a render pass constructor |

### Factory Operations

| Method | Parameters | Return Type | Description |
|--------|------------|-------------|-------------|
| `create` | `String name` | `RenderPass` | Creates a new instance of a registered render pass |
| `isRegistered` | `String name` | `boolean` | Checks if a render pass is registered |
| `getRegisteredRenderPassNames` | | `Set<String>` | Gets all registered render pass names |

## Usage Example

```java
// Register a custom render pass
public class CustomPass extends RenderPass {
    public CustomPass() {
        super("CustomPass", customShader, width, height, inputCount);
    }
}

// Manual registration
RenderPassFactory.registerRenderPass(CustomPass.class);

// Register with custom constructor
RenderPassFactory.register("CustomPass", () -> new CustomPass());

// Scan package for render passes
RenderPassFactory.registerRenderPassesFromPackage("com.game.renders");

// Create instances
RenderPass geometryPass = RenderPassFactory.create("GeometryPass");
RenderPass postProcess = RenderPassFactory.create("PostProcessingPass");

// Check registration
if (RenderPassFactory.isRegistered("BloomPass")) {
    RenderPass bloomPass = RenderPassFactory.create("BloomPass");
}

// Get all available passes
Set<String> availablePasses = RenderPassFactory.getRegisteredRenderPassNames();
for (String passName : availablePasses) {
    System.out.println("Available pass: " + passName);
}
```

## Creating Custom Render Passes

1. Create a new class extending RenderPass:
```java
public class CustomPass extends RenderPass {
    public CustomPass() {
        super("CustomPass", new Shader(...), width, height, inputCount);
    }

    @Override
    public void render(Camera camera, GameObject scene) {
        // Implementation
    }
}
```

2. Ensure it has a public no-args constructor for automatic registration

3. Place it in a scanned package or register manually

## Best Practices
- Use meaningful names for render passes
- Implement proper constructors for automatic registration
- Handle constructor errors gracefully
- Document custom render pass requirements
- Use package scanning for organized code structure
- Test render pass registration and creation
- Clean up resources properly in render passes
- Follow naming conventions for consistency

## See Also
- [RenderPass](RenderPass.md)
- [GeometryPass](Passes/GeometryPass.md)
- [PostProcessingPass](Passes/PostProcessingPass.md)
- [Renderer](Renderer.md)
