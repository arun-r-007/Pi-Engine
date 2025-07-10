# Editor System

**File:** `src/main/java/org/PiEngine/Editor/Editor.java`

## Overview
The `Editor` class manages the ImGui-based editor interface, including window and viewport management, and editor window plugins.

## Key Features
- Singleton pattern for global access
- Manages ImGui context and rendering
- Handles editor windows and multi-viewport support

## Main Methods
- `getInstance(long windowPtr, boolean enableMultiViewport)` — Returns the singleton editor instance
- Methods for adding/removing editor windows

## Usage
Central class for editor UI and window management.
