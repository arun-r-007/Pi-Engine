package org.PiEngine.Component;

import org.PiEngine.GameObjects.*;
import org.PiEngine.Render.*;

/**
 * Base class for all components that can be attached to a GameObject.
 * 
 * - Subclass this to create custom behaviors (e.g., physics, scripts, renderers).
 * - Lifecycle methods are called by the engine automatically.
 */
public abstract class Component
{
    /** Reference to the GameObject this component is attached to */
    public GameObject gameObject;
    public Transform transform;

    /**
     * Called once when the component is first added to a GameObject.
     * Override this for setup logic, such as initialization or references.
     */
    public void start() {}

    /**
     * Called every frame. Use for real-time logic like movement, input, etc.
     */
    public void update() {}

    /**
     * Called at a fixed interval, typically used for physics calculations or consistent simulations.
     * Uses Time.fixedDeltaTime under the hood.
     */
    public void fixedUpdate() {}

    /**
     * Called once before the component is removed or the GameObject is destroyed.
     * Use this to clean up or detach listeners/resources.
     */
    public void onDestroy() {}

    /**
     * Called every frame for rendering-related behavior.
     * Override if your component needs to draw visuals using OpenGL or other rendering logic.
     */
    public void render() {}

    /**
     * Called every frame after rendering, mainly for development tools or debugging purposes.
     * Example: Drawing wireframes, bounding boxes, or debug info.
     */
    public void debugRender() {}
}

