# Core Package Documentation

## Overview
The Core package contains essential engine systems and utilities, including input, timing, window management, and camera control.

| Class | Purpose |
|-------|---------|
| [Camera](Camera.md) | Handles view and projection matrices |
| [Input](Input.md) | Keyboard and mouse input handling |
| [LayerManager](LayerManager.md) | Manages render layers |
| [Time](Time.md) | Time and delta time management |
| [Window](Window.md) | Window creation and management |

## Quick Links
- [Camera Documentation](Camera.md)
- [Input Documentation](Input.md)
- [LayerManager Documentation](LayerManager.md)
- [Time Documentation](Time.md)
- [Window Documentation](Window.md)

## Usage Example
```java
if (Input.isKeyPressed(KEY_W)) {
    // Move forward
}
float dt = Time.getDeltaTime();
```

## See Also
- [Math Package](../Math/README.md)
- [GameObject Package](../GameObject/README.md)
- [Components Package](../Components/README.md)

