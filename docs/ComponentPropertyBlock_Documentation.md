# Pi-Engine: ComponentPropertyBlock Documentation

## Overview

The `ComponentPropertyBlock` class is part of the Pi-Engine Editor module. It is responsible for rendering UI controls for editing public fields of a given `Component` using ImGui. This class provides a dynamic way to expose and edit component properties in the editor, making it easier for developers and contributors to extend and maintain the engine's editor features.

---

## Class: ComponentPropertyBlock

**Location:** `src/main/java/org/PiEngine/Editor/ComponentPropertyBlock.java`

### Purpose
- Renders UI controls for editing public fields of a `Component`.
- Uses ImGui for the UI.
- Supports various field types (e.g., Float, Vector, GameObject, Integer, Boolean, String, Texture).

### Key Features
- **Dynamic Field Handling:** Uses reflection to iterate over public fields of a component and render appropriate UI controls.
- **Type Mapping:** Maintains a map (`fieldTypeMap`) from field types to their corresponding UI handler classes (e.g., `FloatField`, `VectorField`).
- **Custom Handlers:** For each supported type, a handler class is used to manage the UI and data synchronization.
- **Supplier/Consumer Pattern:** Uses Java's `Supplier` and `Consumer` to get and set field values dynamically.
- **Error Handling:** Skips internal or non-editable fields (like `gameObject` or `transform`) and displays error messages in the UI if access fails.

---

## Main Methods

### Constructor
```java
public ComponentPropertyBlock(String label)
```
- **label:** The label for the property block in the UI.

### drawComponentFields
```java
public void drawComponentFields(Component c)
```
- **c:** The component whose fields will be rendered and edited.
- **Functionality:**
  - Iterates over all public fields of the component.
  - For each field, checks if it is supported and not internal.
  - Instantiates the appropriate handler class for the field type.
  - Sets up data synchronization using `Supplier` and `Consumer`.
  - Invokes handler methods to render and update the field in the UI.
  - Handles errors gracefully and displays messages in the UI.

---

## Supported Field Types
- `Float`
- `Vector`
- `GameObject`
- `Integer`
- `Boolean`
- `String`
- `Texture`

You can extend support for more types by adding entries to the `fieldTypeMap`.

---

## How to Extend
- **Add a new field type:**
  1. Create a handler class (e.g., `ColorField`) with methods: `syncWith`, `set`, and `handle`/`draw`.
  2. Add a mapping in `fieldTypeMap`:
     ```java
     fieldTypeMap.put(Color.class, ColorField.class);
     ```
- **Customize UI:**
  - Modify handler classes to change how fields are displayed or edited.

---

## Error Handling
- If a field cannot be accessed or modified, an error message is shown in the ImGui UI.
- Internal fields like `gameObject` and `transform` are skipped.

---

## Contribution Guidelines
- Follow the existing structure for adding new field types and handlers.
- Ensure new handler classes implement the required methods (`syncWith`, `set`, `handle`/`draw`).
- Test changes in the editor to verify correct UI rendering and data synchronization.

---

## Example Usage
```java
ComponentPropertyBlock block = new ComponentPropertyBlock("My Component");
block.drawComponentFields(myComponent);
```

---

## References
- [ImGui Java Binding](https://github.com/SpaiR/imgui-java)
- Java Reflection API

---

For more details, see the source code in `ComponentPropertyBlock.java`.

---

*This document is intended to help new contributors understand the structure and functionality of the `ComponentPropertyBlock` class in Pi-Engine. For questions or further documentation, please refer to the codebase or open an issue on GitHub.*
