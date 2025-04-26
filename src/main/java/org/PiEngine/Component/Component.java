package org.PiEngine.Component;

import org.PiEngine.Core.Camera;
import org.PiEngine.GameObjects.*;
import org.PiEngine.Engine.Console;

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
    public void render(Camera camera) {}

    /**
     * Called every frame after rendering, mainly for development tools or debugging purposes.
     * Example: Drawing wireframes, bounding boxes, or debug info.
     */
    public void debugRender() {}


    // A helper method to get the line number from the exception
    private String getLineNumber(Exception e) {
        StackTraceElement[] stackTrace = e.getStackTrace();
        if (stackTrace.length > 0) {
            return "Line: " + stackTrace[0].getLineNumber();
        }
        return "Unknown Line";
    }

    final public void safeUpdate() 
    {
        try 
        {
            update(); 
        } 
        catch (Exception e) 
        {
            String errorMessage = "Exception in update: " + e.getMessage() + " (" + getLineNumber(e) + ")";
            Console.errorClass(errorMessage, this.getClass().getSimpleName()+".java");
        }
    }

    final public void safeStart() 
    {
        try 
        {
            start(); 
        } 
        catch (Exception e) 
        {
            String errorMessage = "Exception in start: " + e.getMessage() + " (" + getLineNumber(e) + ")";
            Console.errorClass(errorMessage, this.getClass().getSimpleName()+".java");
        }
    }

    final public void safeFixedUpdate() 
    {
        try 
        {
            fixedUpdate(); 
        } 
        catch (Exception e) 
        {
            String errorMessage = "Exception in fixedUpdate: " + e.getMessage() + " (" + getLineNumber(e) + ")";
            Console.errorClass(errorMessage, this.getClass().getSimpleName()+".java");
        }
    }

    final public void safeRender(Camera cam) 
    {
        try 
        {
            render(cam); 
        } 
        catch (Exception e) 
        {
            String errorMessage = "Exception in render: " + e.getMessage() + " (" + getLineNumber(e) + ")";
            Console.errorClass(errorMessage, this.getClass().getSimpleName()+".java");
        }
    }
}
