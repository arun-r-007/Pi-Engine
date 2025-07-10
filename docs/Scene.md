# Scene Management

**File:** `src/main/java/org/PiEngine/Engine/Scene.java`

## Overview
The `Scene` class manages all objects, cameras, and renderers in a scene. It is serializable and supports both editor and game runtime modes.

## Key Features
- Singleton pattern for global scene access
- Holds references to root object, cameras, renderers, and editor windows
- Scene switching and management

## Main Methods
- `getInstance()` — Returns the singleton scene instance
- Methods for managing scene objects and rendering

## Usage
Central class for scene lifecycle and object management.
