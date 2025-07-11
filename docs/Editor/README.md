# Editor Package Documentation

## Overview
The Editor package provides a modular, ImGui-based editor environment for PiEngine. It includes windows for console output, file browsing, hierarchy management, inspection, performance monitoring, and more. Each window is a class derived from `EditorWindow` and can be extended for custom tools.

## Quick Links
- [Editor System](Editor.md)
- [ConsoleWindow](ConsoleWindow.md)
- [DockingWindow](DockingWindow.md)
- [FileWindow](FileWindow.md)
- [HierarchyWindow](HierarchyWindow.md)
- [InspectorWindow](InspectorWindow.md)
- [LayerWindow](LayerWindow.md)
- [NavigationWindow](NavigationWindow.md)
- [PerfomanceWindow](PerfomanceWindow.md)
- [RendererInspector](RendererInspector.md)
- [RenderGraphEditorWindow](RenderGraphEditorWindow.md)
- [SceneWindow](SceneWindow.md)
- [ComponentPropertyBlock](ComponentPropertyBlock.md)

## Common Use Cases
- Inspect and edit GameObjects and components
- View and filter console output
- Browse and manage project files
- Monitor engine performance and memory usage
- Edit render passes and graph connections
- Manage layers and scene hierarchy

## Example Usage
```java
Editor editor = Editor.getInstance(windowPtr, true);
editor.init();
editor.addWindow(new ConsoleWindow());
editor.addWindow(new HierarchyWindow());
editor.update();
```

## Best Practices
- Use `EditorWindow` as a base for custom windows
- Queue window additions/removals for thread safety
- Customize the ImGui theme for a consistent look
- Keep window logic modular for easy extension

## See Also
- [Core Package](../Core/README.md)
- [GameObject Package](../GameObject/README.md)
- [Components Package](../Components/README.md)
