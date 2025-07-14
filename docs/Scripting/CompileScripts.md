# CompileScripts Class

The CompileScripts class is a singleton that handles dynamic compilation of Java script files into usable components for the Pi-Engine.

## Class Definition

```java
public class CompileScripts
```

## Properties

| Property | Type | Description |
|----------|------|-------------|
| `instance` | `CompileScripts` | Singleton instance of CompileScripts |
| `scriptFolder` | `File` | Source folder containing Java script files |
| `outputFolder` | `File` | Output folder for compiled class files |
| `engineJar` | `File` | Path to the engine JAR for compilation dependencies |

## Methods

### Instance Management

| Method | Parameters | Return Type | Description |
|--------|------------|-------------|-------------|
| `getInstance(String scriptPath, String outputPath, String engineJarPath)` | `String scriptPath, String outputPath, String engineJarPath` | `CompileScripts` | Static method to get or create instance with paths |
| `getInstance()` | None | `CompileScripts` | Static method to get existing instance |

### Compilation Methods

| Method | Parameters | Return Type | Description |
|--------|------------|-------------|-------------|
| `compileScripts()` | None | `void` | Compiles all scripts in the script folder |
| `compileScript(File scriptFile)` | `File scriptFile` | `void` | Compiles a single script file |
| `compileSystems()` | None | `void` | (Reserved) Compiles system script files |

## Compilation Process

1. Java source files are located in the script folder
2. Each file is compiled using the Java compiler tool
3. Compiled classes are output to the specified folder
4. Compilation errors are logged for debugging
5. Compiled scripts become available for loading by ScriptLoader

## Usage Example

```java
// Initialize CompileScripts with paths
CompileScripts compiler = CompileScripts.getInstance(
    "Scripts",      // Source folder
    "Compiled",     // Output folder
    null           // Engine JAR path (optional)
);

// Compile a specific script
compiler.compileScript(new File("Scripts/MyComponent.java"));

// Compile all scripts
compiler.compileScripts();
```

## Error Handling

- Compilation errors are logged with source file information
- Invalid script files are skipped
- Runtime errors are caught and logged
- Failed compilations don't affect other scripts

## Best Practices

- Keep script source files organized
- Handle compilation errors gracefully
- Use proper Java package declarations
- Maintain clear separation of concerns
- Follow component scripting guidelines

## See Also

- [ScriptLoader](ScriptLoader.md)
- [Component](../Components/Component.md)
- [Console](../Engine/Console.md)
