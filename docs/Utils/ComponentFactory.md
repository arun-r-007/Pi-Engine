# ComponentFactory Class

The ComponentFactory is a utility class that handles dynamic component creation and registration in the Pi-Engine.

## Class Definition

```java
public class ComponentFactory
```

## Properties

| Property | Type | Description |
|----------|------|-------------|
| `componentConstructors` | `Map<String, Supplier<Component>>` | Static map of component constructors by name |

## Methods

### Component Registration

| Method | Parameters | Return Type | Description |
|--------|------------|-------------|-------------|
| `registerComponentsFromPackage(String basePackage)` | `String basePackage` | `void` | Scans a package for Component subclasses and registers them |
| `registerComponent(Class<? extends Component> compClass)` | `Class<? extends Component> compClass` | `void` | Registers a single component class |
| `register(String name, Supplier<Component> constructor)` | `String name, Supplier<Component> constructor` | `void` | Registers a component constructor with a name |

### Component Creation

| Method | Parameters | Return Type | Description |
|--------|------------|-------------|-------------|
| `create(String name)` | `String name` | `Component` | Creates a component instance by name |
| `createComponent(int typeId)` | `int typeId` | `Component` | (Reserved) Creates a component by type ID |

### Utility Methods

| Method | Parameters | Return Type | Description |
|--------|------------|-------------|-------------|
| `isRegistered(String name)` | `String name` | `boolean` | Checks if a component type is registered |
| `getRegisteredComponentNames()` | None | `Set<String>` | Gets all registered component names |
| `GetClass(String name)` | `String name` | `Class<? extends Component>` | Gets the class type of a component |
| `Clear()` | None | `void` | Clears all registered components |

## Usage Example

```java
// Register all components in a package
ComponentFactory.registerComponentsFromPackage("org.PiEngine.Component");

// Register a single component
ComponentFactory.registerComponent(MyComponent.class);

// Create a component by name
Component comp = ComponentFactory.create("MyComponent");

// Check registration
boolean exists = ComponentFactory.isRegistered("MyComponent");

// Get registered components
Set<String> components = ComponentFactory.getRegisteredComponentNames();

// Clear registrations
ComponentFactory.Clear();
```

## Registration Process

1. Component classes are discovered via reflection
2. Each class must:
   - Extend Component
   - Have a public no-arg constructor
3. Classes are registered by their simple name
4. Constructor suppliers are created and cached

## Best Practices

- Register components early in application startup
- Use clear, unique component names
- Handle registration failures gracefully
- Clear registrations when reloading scripts
- Check if components are registered before creation
- Use factory methods instead of direct instantiation

## See Also

- [Component](../Components/Component.md)
- [ScriptLoader](../Scripting/ScriptLoader.md)
- [GUID](GUID.md)
