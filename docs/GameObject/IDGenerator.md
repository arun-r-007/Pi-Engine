# IDGenerator Class Documentation

## Function Overview
| Visibility | Function | Description |
|------------|----------|-------------|
| public static | generateUniqueID() | Returns a unique integer ID (increments counter). |
| public static | resetIDCounter()   | Resets the ID counter to zero. |

## Overview
The `IDGenerator` class provides static methods for generating unique integer IDs for GameObjects or other entities. This is useful for assigning unique identifiers to objects in the engine.

## Class Definition
```java
package org.PiEngine.GameObjects;
public class IDGenerator
```

## Properties
| Property | Type | Description |
|----------|------|-------------|
| currentID | int  | The current ID counter (auto-incremented, static) |

## Methods
```java
/**
 * Generates and returns a unique integer ID.
 * @return Unique integer ID (auto-incremented)
 */
public static int generateUniqueID()

/**
 * Resets the ID counter to zero.
 */
public static void resetIDCounter()
```

## Example Usage
```java
int id1 = IDGenerator.generateUniqueID(); // 0
int id2 = IDGenerator.generateUniqueID(); // 1
IDGenerator.resetIDCounter();
int id3 = IDGenerator.generateUniqueID(); // 0
```

## Best Practices
- Use `generateUniqueID()` when you need a unique identifier for a new object
- Use `resetIDCounter()` only when you want to restart ID assignment (e.g., on scene reload)
