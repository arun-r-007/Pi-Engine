# Components Package Documentation

## Overview
The Components package contains all attachable behaviors for GameObjects, including rendering, camera, and custom logic. Each component extends the base `Component` class and implements specific functionality.

| Component | Purpose |
|-----------|---------|
| [Component](Component.md) | Base class for all attachable behaviors |
| [CameraComponent](CameraComponent.md) | Attaches a Camera to a GameObject and synchronizes its transform |
| [RendererComponent](RendererComponent.md) | Handles mesh rendering, color, shader, and texture |

## Quick Links
- [Component Documentation](Component.md)
- [CameraComponent Documentation](CameraComponent.md)
- [RendererComponent Documentation](RendererComponent.md)

## Usage Example
```java
GameObject obj = new GameObject("Player");
obj.addComponent(new RendererComponent());
obj.addComponent(new CameraComponent());
```

## Best Practices
- Attach only one `CameraComponent` per camera GameObject
- Use `RendererComponent` for objects that need to be drawn
- Extend `Component` to create custom behaviors

## See Also
- [GameObject Package](../GameObject/README.md)
- [Math Package](../Math/README.md)
- [Core Package](../Core/README.md)

