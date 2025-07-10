# MathF Class Documentation

## Function Overview

| Visibility | Function | Description |
|------------|----------|-------------|
| static     | lerp     | Linearly interpolates between a and b by t. |
| static     | clamp    | Clamps a value between min and max. |
| static     | random   | Returns a random float between 0.0 and 1.0. |
| static     | randomRange | Returns a random float between min and max. |
| static     | sign     | Returns the sign of the value (-1, 0, or 1). |
| static     | moveTowards | Moves value towards target by maxDelta. |
| static     | degToRad | Converts degrees to radians. |
| static     | radToDeg | Converts radians to degrees. |

## Overview
The `MathF` class provides a collection of common mathematical utility functions. It includes methods for interpolation, clamping, random number generation, and various mathematical transformations. This class is essential for game development operations like smooth transitions, value constraints, and mathematical calculations.

## Class Definition
```java
package org.PiEngine.Math;
public class MathF
```

## Properties

| Property | Type | Description |
|----------|------|-------------|
| `random` | `Random` | Private static instance of Random class for generating random numbers |

## Methods

### Interpolation
```java
/**
 * Linearly interpolates between a and b by t.
 * @param a Start value
 * @param b End value
 * @param t Interpolation factor (0 to 1)
 * @return Interpolated value
 */
public static float lerp(float a, float b, float t)
```
Example:
```java
float start = 0;
float end = 10;
float halfway = MathF.lerp(start, end, 0.5f); // Returns 5
```

### Value Constraints
```java
/**
 * Clamps a value between min and max.
 * @param value The value to clamp
 * @param min Minimum allowed value
 * @param max Maximum allowed value
 * @return The clamped value
 */
public static float clamp(float value, float min, float max)
```
Example:
```java
float clamped = MathF.clamp(15, 0, 10); // Returns 10
```

### Random Generation
```java
/**
 * Returns a random float between 0.0 and 1.0.
 * @return Random float in [0,1]
 */
public static float random()

/**
 * Returns a random float between min and max.
 * @param min Minimum value (inclusive)
 * @param max Maximum value (inclusive)
 * @return Random float in [min,max]
 */
public static float randomRange(float min, float max)
```
Example:
```java
float rand = MathF.random(); // Returns [0,1]
float rangeRand = MathF.randomRange(-5, 5); // Returns [-5,5]
```

### Value Movement
```java
/**
 * Moves value towards target by maxDelta.
 * @param current Current value
 * @param target Target value
 * @param maxDelta Maximum change allowed
 * @return Moved value
 */
public static float moveTowards(float current, float target, float maxDelta)
```
Example:
```java
float current = 0;
float moved = MathF.moveTowards(current, 10, 2); // Returns 2
```

### Angle Conversion
```java
/**
 * Converts degrees to radians.
 * @param degrees Angle in degrees
 * @return Angle in radians
 */
public static float degToRad(float degrees)

/**
 * Converts radians to degrees.
 * @param radians Angle in radians
 * @return Angle in degrees
 */
public static float radToDeg(float radians)
```
Example:
```java
float rad = MathF.degToRad(180); // Returns π (≈3.14159)
float deg = MathF.radToDeg(Math.PI); // Returns 180
```

### Value Sign
```java
/**
 * Returns the sign of the value (-1, 0, or 1).
 * @param value Input value
 * @return -1 if negative, 0 if zero, 1 if positive
 */
public static int sign(float value)
```
Example:
```java
int sign = MathF.sign(-5); // Returns -1
```

## Common Use Cases

### Smooth Movement
Use `lerp` for smooth transitions between values:
```java
position = MathF.lerp(currentPos, targetPos, Time.deltaTime);
```

### Value Boundaries
Use `clamp` to keep values within limits:
```java
health = MathF.clamp(health, 0, maxHealth);
```

### Random Generation
Generate random positions or values:
```java
float randomX = MathF.randomRange(-10, 10);
float randomY = MathF.randomRange(-10, 10);
```

### Angle Handling
Convert between degrees and radians:
```java
float radians = MathF.degToRad(rotation);
float degrees = MathF.radToDeg(angleRad);
```

## Best Practices

1. Use `lerp` for smooth transitions and animations
2. Always clamp values that have logical limits
3. Use `moveTowards` for controlled value changes
4. Prefer `degToRad` and `radToDeg` over manual conversion
5. Use `sign` for direction-based logic

## Performance Considerations

1. `lerp` is unclamped - consider using `clamp` if t should be [0,1]
2. `random` is thread-safe but might be a bottleneck in heavy parallel usage
3. `moveTowards` includes bounds checking, making it safer but slightly slower than raw math
4. Angle conversions include floating-point multiplication, cache if used frequently
