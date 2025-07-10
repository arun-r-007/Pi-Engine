# GameObject Class Documentation

## Function Overview
| Visibility | Function | Description |
|------------|----------|-------------|
| public     | GameObject(String name) | Constructs a GameObject with a name and initializes its Transform. |
| public     | addChild(GameObject child) | Adds a child GameObject by parenting its Transform. |
| public     | addComponent(Component component) | Adds a component to this GameObject. |
| public     | getComponent(Class<T> type) | Gets the first component of the specified type. |
| public     | fixedUpdate() | Called at a fixed interval for physics/timed logic. |
| public     | update() | Called every frame for logic updates. |
| public     | render(Camera camera, int layerMask) | Called every frame for rendering. |
| public     | debugRender() | Called for debug rendering. |
| public     | UpdateLocation() | Updates the Location string recursively. |
| public static | Location(GameObject gb) | Returns the hierarchical path of a GameObject. |
| public static | findGameObject(String path, GameObject root) | Finds a GameObject by path (recursive). |
| public static | findGameObjectItrative(String path, GameObject root) | Finds a GameObject by path (iterative). |
| public     | printHierarchy() | Prints the hierarchy tree. |
| public     | reparentTo(GameObject newParent) | Reparents this GameObject. |
| public     | getLayerBit() | Gets the layer bitmask. |
| public     | setLayer(int newLayer) | Sets the layer bitmask. |
| public     | setLayerOnly(int newLayer) | Sets the layer for this object only. |
| public     | setLayerRecursively(int layerBit) | Sets the layer for this object and all children. |
| public     | setLayerByName(String layerName, boolean recursive) | Sets the layer by name (optionally recursive). |
| public     | toString() | Returns a string representation. |
| public     | getAllComponents() | Returns all components. |
| public     | removeComponent(Component cmp) | Removes a component. |
| public static | destroy(GameObject thisGb) | Destroys a GameObject and its children. |
| public     | getId(), setId(int) | Gets/Sets the unique ID. |
| public     | getName(), setName(String) | Gets/Sets the name. |
| public     | getTransform(), setTransform(Transform) | Gets/Sets the transform. |
| public     | getLayer(), setLayer(int) | Gets/Sets the layer. |
| public     | getComponents(), setComponents(List<Component>) | Gets/Sets the components list. |
| public     | getComponentCount() | Returns the number of components. |

## Overview
The `GameObject` class represents an entity in the scene graph. It can have components, children, and a transform for position, rotation, and scale. GameObjects are the core building blocks for all objects in the engine, similar to Unity's GameObject.

## Class Definition
```java
package org.PiEngine.GameObjects;
public class GameObject
```

## Properties
| Property | Type | Description |
|----------|------|-------------|
| Name | String | The name of the GameObject |
| transform | Transform | The Transform component for position, rotation, scale |
| id | int | Unique identifier for this GameObject |
| Location | String | Hierarchical path of this GameObject |
| layer | int | Layer bitmask for rendering and filtering |
| components | List<Component> | List of attached components |

## Methods
```java
/**
 * Constructs a GameObject with a given name and initializes its Transform.
 * @param name The name of the GameObject
 */
public GameObject(String name)

/**
 * Adds a child GameObject by parenting its Transform to this GameObject's Transform.
 * @param child The child GameObject to add
 */
public void addChild(GameObject child)

/**
 * Adds a component to this GameObject.
 * @param component The component instance to attach
 */
public void addComponent(Component component)

/**
 * Retrieves the first component of the specified type attached to this GameObject.
 * @param <T> The class type of the component
 * @param type The class object representing the desired component type
 * @return The component instance if found; otherwise, null
 */
public <T extends Component> T getComponent(Class<T> type)

/**
 * Called at a fixed interval (using Time.fixedDeltaTime).
 * Typically used for physics or time-consistent logic.
 */
public void fixedUpdate()

/**
 * Called once every frame. Updates all components and recursively updates all children.
 */
public void update()

/**
 * Called once every frame after update(). Responsible for rendering this GameObject and its children.
 * @param camera The camera to render with
 * @param layerMask The layer mask for rendering
 */
public void render(Camera camera, int layerMask)

/**
 * Called after render(), only in debug mode or when needed. Used to draw debug visuals like bounding boxes, axis lines, etc.
 */
public void debugRender()

/**
 * Recursively updates the Location string for this GameObject and all children.
 */
public void UpdateLocation()

/**
 * Returns the hierarchical path of the GameObject in the scene tree.
 * @param gb The GameObject
 * @return The path string
 */
public static String Location(GameObject gb)

/**
 * Finds a GameObject by its path, recursively searching the hierarchy.
 * @param path The path string
 * @param root The root GameObject to search from
 * @return The found GameObject, or null if not found
 */
public static GameObject findGameObject(String path, GameObject root)

/**
 * Finds a GameObject by its path using an iterative approach.
 * @param path The path string
 * @param root The root GameObject to search from
 * @return The found GameObject, or null if not found
 */
public static GameObject findGameObjectItrative(String path, GameObject root)

/**
 * Recursively prints the hierarchy of GameObjects starting from this one.
 */
public void printHierarchy()

/**
 * Reparents this GameObject to a new parent GameObject, preserving world transform.
 * @param newParent The GameObject to become the new parent
 */
public void reparentTo(GameObject newParent)

/**
 * Gets the layer bitmask.
 * @return The layer bitmask
 */
public int getLayerBit()

/**
 * Sets the layer bitmask.
 * @param newLayer The new layer bitmask
 */
public void setLayer(int newLayer)

/**
 * Sets the layer for this object only.
 * @param newLayer The new layer bitmask
 */
public void setLayerOnly(int newLayer)

/**
 * Sets the layer for this object and all its children recursively.
 * @param layerBit The new layer bitmask
 */
public void setLayerRecursively(int layerBit)

/**
 * Sets the layer from layer(bit) name (for convenience).
 * @param layerName The name of the layer
 * @param recursive Whether to set recursively
 */
public void setLayerByName(String layerName, boolean recursive)

/**
 * Returns a readable string representation of the GameObject.
 * @return String representation
 */
@Override
public String toString()

/**
 * Returns all the components of a GameObject.
 * @return List of components
 */
public List<Component> getAllComponents()

/**
 * Removes a component from this GameObject.
 * @param cmp The component to remove
 */
public void removeComponent(Component cmp)

/**
 * Destroys a GameObject and all its children, removing them from the hierarchy and clearing components.
 * @param thisGb The GameObject to destroy
 */
public static void destroy(GameObject thisGb)

/**
 * Gets/Sets the unique ID, name, transform, layer, and components.
 */
public int getId(); public void setId(int id);
public String getName(); public void setName(String name);
public Transform getTransform(); public void setTransform(Transform transform);
public int getLayer(); public void setLayer(int layer);
public List<Component> getComponents(); public void setComponents(List<Component> components);
public int getComponentCount();
```

## Example Usage
```java
GameObject player = new GameObject("Player");
player.addComponent(new PlayerController());
GameObject enemy = new GameObject("Enemy");
player.addChild(enemy);

player.update(); // Calls update on player and all children

// Find a GameObject by path
GameObject found = GameObject.findGameObject("/Player/Enemy", root);

// Set layer recursively
player.setLayerRecursively(2);
```

## Best Practices
- Use `addComponent` to attach logic/behavior to GameObjects
- Use `addChild` to build hierarchies (e.g., player with weapon as child)
- Use `findGameObject` for path-based lookups
- Use `setLayerRecursively` for group rendering/filtering

## See Also
- [Transform](Transform.md)
- [IDGenerator](IDGenerator.md)
