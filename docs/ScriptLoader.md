# Scripting System

**File:** `src/main/java/org/PiEngine/Scripting/ScriptLoader.java`

## Overview
The `ScriptLoader` class loads and manages compiled script classes for runtime execution.

## Key Features
- Singleton pattern for global access
- Loads compiled scripts from a directory
- Uses a custom class loader

## Main Methods
- `getInstance()` — Returns the singleton script loader
- Methods for loading and instantiating script components

## Usage
Use this class to dynamically load and run user scripts in the engine.
