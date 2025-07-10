# Component System

**File:** `src/main/java/org/PiEngine/Component/Component.java`

## Overview
The `Component` class is the abstract base for all components that can be attached to a `GameObject`. Subclass this to create custom behaviors (e.g., physics, scripts, renderers). Lifecycle methods are called by the engine automatically.

## Key Features
- Abstract base for all attachable behaviors
- Holds references to its `GameObject` and `Transform`
- Unique ID for each component
- Lifecycle methods: `start()`, `update()`, `fixedUpdate()`, `onDestroy()`, `render()`, `debugRender()`
- Safe wrappers for lifecycle methods to catch and log exceptions
- Reflection-based property access for serialization/editor

## Fields
```java
// Public
public GameObject gameObject;   // The GameObject this component is attached to
public Transform transform;     // The Transform of the GameObject

// Private
private int id;                 // Unique identifier for the component
```

## Abstract and Overridable Methods
```java
// Called once when the component is first added to a GameObject
public void start() {}

// Called every frame for real-time logic
public void update() {}

// Called at a fixed interval (e.g., for physics)
public void fixedUpdate() {}

// Called before the component is removed or GameObject destroyed
public void onDestroy() {}

// Called every frame for rendering
public void render(Camera camera) {}

// Called every frame after rendering for debug visuals
public void debugRender() {}
```

## Public Methods
```java
// Safe wrappers (final, catch exceptions and log)
final public void safeStart()
final public void safeUpdate()
final public void safeFixedUpdate()
final public void safeRender(Camera cam)

// Property reflection for editor/serialization
public Map<String, Object> getProperties()
public void setComponentProperty(String propertyName, JsonElement propertyValue)

// Getters/Setters
public GameObject getGameObject()
public void setGameObject(GameObject gameObject)
public Transform getTransform()
public void setTransform(Transform transform)
public int getId()
public void setId(int id)
```

## Private Methods
```java
// Helper for error reporting
private String getLineNumber(Exception e)
```

## Usage Example
```java
// Custom movement component
public class MovementComponent extends Component {
    public float speed = 5.0f;
    
    @Override
    public void start() {
        // Initialization logic
    }
    
    @Override
    public void update() {
        // Move the GameObject every frame
        transform.position.x += speed * Time.deltaTime;
    }
}

// Attaching to a GameObject
GameObject player = new GameObject("Player");
player.addComponent(new MovementComponent());
```

## Best Practices
- Always use `safe*` methods for lifecycle calls to ensure errors are logged
- Use `getProperties()` and `setComponentProperty()` for editor/serialization integration
- Override only the methods you need for your custom component
- Use `onDestroy()` to clean up resources or listeners

## Notes
- All components must extend `Component`
- The engine automatically calls lifecycle methods
- Use public fields for properties you want to expose in the editor
