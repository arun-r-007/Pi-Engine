# GameObject System

**File:** `src/main/java/org/PiEngine/GameObjects/GameObject.java`

## Overview
The `GameObject` class is the fundamental building block of the Pi-Engine scene system. Each GameObject represents a discrete entity in the scene, with its own transformation, components, and hierarchical relationships.

## Key Features
- **Hierarchical Scene Graph:** Parent-child relationships with transformation inheritance
- **Component-Based Architecture:** Flexible attachment of behaviors and features
- **Layer System:** Selective rendering and filtering
- **Lifecycle Management:** Coordinated updates and cleanup
- **Debug Support:** Visual debugging and hierarchy inspection

## Properties
```java
public String Name;              // Identifier for the GameObject
public Transform transform;      // Position, rotation, and scale
private int id;                 // Unique internal identifier
private int layer;              // Rendering and collision layer
private List<Component> components;  // Attached behaviors
```

## Public Methods

### Construction and Setup
```java
public GameObject(String name)   // Create new GameObject
public void addComponent(Component component)  // Add behavior
public void removeComponent(Component cmp)     // Remove behavior
```

### Hierarchy Management
```java
public void addChild(GameObject child)        // Add child object
public void reparentTo(GameObject newParent)  // Change parent
```

### Component Access
```java
public <T extends Component> T getComponent(Class<T> type)  // Get component by type
public List<Component> getAllComponents()                   // Get all components
public int getComponentCount()                             // Get component count
```

### Lifecycle Methods
```java
public void update()       // Per-frame update
public void fixedUpdate()  // Physics update
public void render(Camera camera, int layerMask)  // Render call
public void debugRender()  // Debug visualization
public void destroy()      // Cleanup and removal
```

### Layer Management
```java
public void setLayer(int newLayer)
public void setLayerRecursively(int layerBit)
public void setLayerByName(String layerName, boolean recursive)
```

## Usage Example
```java
// Create main object
GameObject player = new GameObject("Player");

// Add components
player.addComponent(new MovementComponent());
player.addComponent(new RendererComponent());

// Set up hierarchy
GameObject weapon = new GameObject("Weapon");
player.addChild(weapon);

// Configure layers
player.setLayerByName("Characters", true);
```

## Scene Hierarchy Example
```
Root
└── Player
    ├── Movement (speed: 10.0)
    ├── Renderer (size: 2.5)
    └── Weapon
        └── Renderer
```

## Best Practices
1. **Component Management**
   - Use getComponent() sparingly, cache references when possible
   - Remove unused components to free resources

2. **Hierarchy Organization**
   - Keep hierarchies shallow for better performance
   - Use meaningful names for easy debugging

3. **Layer Usage**
   - Group similar objects in the same layer
   - Use recursive layer setting for related objects

4. **Lifecycle Handling**
   - Always call destroy() when removing objects
   - Use fixedUpdate() for physics calculations
