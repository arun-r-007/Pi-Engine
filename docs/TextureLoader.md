# Texture Loading

**File:** `src/main/java/org/PiEngine/IO/TextureLoader.java`

## Overview
The `TextureLoader` class loads image files into textures using the STB library and OpenGL.

## Key Features
- Loads images from disk
- Handles flipping and channel conversion
- Returns a `Texture` object for use in rendering

## Main Methods
- `loadTexture(String path, int minFilter, int magFilter)` — Loads a texture from file

## Usage
Use this class to load textures for materials and sprites.
