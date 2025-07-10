# GameObject Package Documentation

## Overview
The GameObject package provides the core classes for entity management in the engine. It includes:

| Class | Purpose |
|-------|---------|
| [GameObject](GameObject.md) | The main entity class, container for components |
| [IDGenerator](IDGenerator.md) | Utility for generating unique IDs for objects |
| [Transform](Transform.md) | Handles position, rotation, and scale |

## Quick Links
- [GameObject Documentation](GameObject.md)
- [IDGenerator Documentation](IDGenerator.md)
- [Transform Documentation](Transform.md)

## Usage Example
```java
GameObject obj = new GameObject();
Transform t = obj.getTransform();
obj.addComponent(new MyComponent());
```

## See Also
- [Component](../Components/Component.md)
- [Math Package](../Math/README.md)
- [Core Package](../Core/README.md)
