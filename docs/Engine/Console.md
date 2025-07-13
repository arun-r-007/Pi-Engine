# Console Class

The `Console` class provides logging and message management for Pi-Engine. It supports logging, warnings, and errors, and tracks messages with timestamps and caller information for debugging and editor display.

## Overview

Console is a static utility class that collects messages for display in the editor and for debugging. It automatically logs OpenGL information on startup and supports message queueing with a configurable limit.

## Properties

| Name | Type | Description |
|------|------|-------------|
| `messages` | `List<String[]>` | List of logged messages (time, message, type, caller) |
| `MAX_MESSAGES` | `int` | Maximum number of messages to keep |

## Methods

| Method | Parameters | Return Type | Description |
|--------|------------|-------------|-------------|
| `log` | `String message` | `void` | Logs a standard message |
| `warning` | `String message` | `void` | Logs a warning message |
| `error` | `String message` | `void` | Logs an error message |
| `errorClass` | `String message, String Component` | `void` | Logs an error with a specific component name |

## Internal Methods

| Method | Parameters | Return Type | Description |
|--------|------------|-------------|-------------|
| `getCurrentTime` | | `String` | Gets the current time as a string |
| `getCallerInfo` | | `String` | Gets the file and line number of the caller |
| `addMessage` | `String time, String message, String type, String caller` | `void` | Adds a message to the queue |

## Usage Example

```java
Console.log("Engine started");
Console.warning("Low memory warning");
Console.error("Failed to load asset");
Console.errorClass("Component failed to initialize", "RendererComponent");
```

## Best Practices
- Use Console for all engine/editor logging
- Check caller info for debugging
- Limit message queue size for performance
- Display messages in the editor for user feedback

## See Also
- [Scene](Scene.md)
- [Editor](../Editor/Editor.md)
