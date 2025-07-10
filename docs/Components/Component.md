# Component Class Documentation

## Function Overview
| Visibility | Function | Description |
|------------|----------|-------------|
| public     | start() | Called once when the component is first added to a GameObject. |
| public     | update() | Called every frame for logic updates. |
| public     | fixedUpdate() | Called at a fixed interval for physics/timed logic. |
| public     | onDestroy() | Called before the component is removed or the GameObject is destroyed. |
| public     | render(Camera camera) | Called every frame for rendering-related behavior. |
| public     | debugRender() | Called every frame after rendering for debug visuals. |
| public final | safeUpdate() | Runs update() safely, catching and logging exceptions. |
| public final | safeStart() | Runs start() safely, catching and logging exceptions. |
| public final | safeFixedUpdate() | Runs fixedUpdate() safely, catching and logging exceptions. |

## Overview
The `Component` class is the base for all attachable behaviors in the engine. Subclass it to create custom logic, rendering, or physics. Lifecycle methods are called by the engine automatically.

## Class Definition
```java
package org.PiEngine.Component;
public abstract class Component
```

## Properties
| Property | Type | Description |
|----------|------|-------------|
| gameObject | GameObject | The GameObject this component is attached to |
| transform | Transform | The Transform of the owning GameObject |
| id | int | Unique identifier for this component |

## Methods
```java
/**
 * Called once when the component is first added to a GameObject.
 */
public void start()

/**
 * Called every frame. Use for real-time logic like movement, input, etc.
 */
public void update()

/**
 * Called at a fixed interval, typically for physics calculations.
 */
public void fixedUpdate()

/**
 * Called before the component is removed or the GameObject is destroyed.
 */
public void onDestroy()

/**
 * Called every frame for rendering-related behavior.
 * @param camera The camera to use for rendering
 */
public void render(Camera camera)

/**
 * Called every frame after rendering, mainly for debugging.
 */
public void debugRender()

/**
 * Runs update() safely, catching and logging exceptions.
 */
public final void safeUpdate()

/**
 * Runs start() safely, catching and logging exceptions.
 */
public final void safeStart()

/**
 * Runs fixedUpdate() safely, catching and logging exceptions.
 */
public final void safeFixedUpdate()
```

## Example Usage
```java
public class MyComponent extends Component {
    @Override
    public void start() {
        // Initialization
    }
    @Override
    public void update() {
        // Per-frame logic
    }
}
```

## Best Practices
- Always call `super.start()` if you override lifecycle methods and need base logic.
- Use `gameObject` and `transform` for manipulating the owning object.

## See Also
- [GameObject](../GameObject/GameObject.md)
- [Transform](../GameObject/Transform.md)
