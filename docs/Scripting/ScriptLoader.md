# ScriptLoader Class

The ScriptLoader is a singleton class that handles the dynamic loading and management of compiled script components in the Pi-Engine.

## Class Definition

```java
public class ScriptLoader
```

## Properties

| Property | Type | Description |
|----------|------|-------------|
| `instance` | `ScriptLoader` | Singleton instance of ScriptLoader |
| `rootDirectory` | `File` | Root directory for compiled scripts |
| `urlClassLoader` | `URLClassLoader` | ClassLoader for dynamically loading compiled scripts |

## Methods

### Instance Management

| Method | Parameters | Return Type | Description |
|--------|------------|-------------|-------------|
| `getInstance()` | None | `ScriptLoader` | Static method to get or create the singleton instance |
| `reset()` | None | `void` | Static method to reset the ScriptLoader state and clear loaded components |

### Script Loading

| Method | Parameters | Return Type | Description |
|--------|------------|-------------|-------------|
| `loadClass(String fullyQualifiedName)` | `String fullyQualifiedName` | `Class<?>` | Loads a class by its fully qualified name |
| `loadComponentScript(File scriptFile)` | `File scriptFile` | `void` | Loads a single component script from a file |
| `loadComponentFolder(String folderPath)` | `String folderPath` | `void` | Loads all component scripts from a folder |
| `loadSystemScripts(String folderPath)` | `String folderPath` | `void` | (Reserved) Loads system scripts from a folder |
| `loadBehaviorScripts(String folderPath)` | `String folderPath` | `void` | (Reserved) Loads behavior scripts from a folder |

### Resource Management

| Method | Parameters | Return Type | Description |
|--------|------------|-------------|-------------|
| `close()` | None | `void` | Closes the URLClassLoader and cleans up resources |

## Loading Process

1. Script files are located in the compiled scripts directory
2. Each script is loaded using the URLClassLoader
3. If the loaded class extends Component:
   - It is registered with the ComponentFactory
   - The component becomes available for use in the engine
4. Loading errors are logged to the Console

## Usage Example

```java
// Get ScriptLoader instance
ScriptLoader loader = ScriptLoader.getInstance();

// Load all components from the compiled folder
loader.loadComponentFolder("Compiled");

// Load a specific component
loader.loadComponentScript(new File("Compiled/Scripts/MyComponent.class"));

// Reset the ScriptLoader (e.g., for hot-reloading)
ScriptLoader.reset();
```

## Best Practices

- Handle script loading errors gracefully
- Use the ComponentFactory to create component instances
- Clean up resources when reloading scripts
- Follow proper package naming conventions
- Maintain script organization by type

## See Also

- [CompileScripts](CompileScripts.md)
- [Component](../Components/Component.md)
- [ComponentFactory](../Utils/ComponentFactory.md)
