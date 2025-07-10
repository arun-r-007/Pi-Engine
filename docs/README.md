# Pi-Engine Documentation

Welcome to the Pi-Engine documentation! This comprehensive guide covers all aspects of the engine's architecture, systems, and usage.

## Quick Start
- [Technical Reference](technical_reference.md) - Complete technical overview
- [Component Property Block](../ComponentPropertyBlock_Documentation.md) - Editor component system

## Core Systems

### Game Architecture
- [GameObject System](GameObject.md) - Core game object architecture
- [Component System](Component.md) - Component-based behavior system
- [Scene Management](Scene.md) - Scene organization and lifecycle
- [Transform](Transform.md) - Hierarchical transform system

### Rendering
- [Rendering System](Renderer.md) - Advanced rendering pipeline
- [Camera System](Camera.md) - View and projection management
- [Texture Loading](TextureLoader.md) - Asset loading system

### Math Utilities
- [Math: Vector](Vector.md) - 3D mathematics
- [Math: Matrix4](Matrix4.md) - 4x4 matrix operations

### Editor
- [Editor System](Editor.md) - Editor architecture and windows
- [Property Inspector](../ComponentPropertyBlock_Documentation.md) - Component editing

### Tools and Utilities
- [Scripting System](ScriptLoader.md) - Runtime script loading

## Printing to the Engine Console
To print messages or debug information in the engine, use the `Console` class:
```java
import org.PiEngine.Engine.Console;

Console.log("Hello, Pi-Engine!");
Console.error("Something went wrong!");
Console.warn("This is a warning.");
```

## Advanced Topics

### Rendering Pipeline
Learn how to create custom render passes and effects:
```java
// Example: Adding a new post-process effect
public class BloomPass extends RenderPass {
    public BloomPass(int width, int height) {
        super("Bloom", new Shader("post/bloom.vert", "post/bloom.frag"), 
              width, height, 1);
    }
    // ... implementation
}
```

### Component Development
Create new components by extending the base Component class:
```java
public class CustomComponent extends Component {
    public void start() {
        // Initialization
    }
    
    public void update() {
        // Per-frame logic
    }
}
```

### Scene Setup
Build scenes programmatically or through the editor:
```java
GameObject root = new GameObject("Root");
GameObject player = new GameObject("Player");
player.addComponent(new MovementComponent());
root.addChild(player);
```

## Best Practices

### Performance
- Use layer masks for efficient rendering
- Cache component references
- Batch similar operations

### Architecture
- Keep hierarchies shallow
- Use components for behavior
- Leverage the event system

### Development
- Follow naming conventions
- Document public APIs
- Write unit tests

## Contributing
1. Read the technical reference
2. Follow coding standards
3. Test thoroughly
4. Submit pull requests

## Additional Resources
- Source Code: [GitHub Repository](https://github.com/yourusername/Pi-Engine)
- Issues: [GitHub Issues](https://github.com/yourusername/Pi-Engine/issues)
- Wiki: [GitHub Wiki](https://github.com/yourusername/Pi-Engine/wiki)

---

For detailed implementation examples and API references, see the individual documentation files linked above.
