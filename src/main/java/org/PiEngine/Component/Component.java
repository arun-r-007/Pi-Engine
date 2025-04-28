package org.PiEngine.Component;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

import org.PiEngine.Core.Camera;
import org.PiEngine.GameObjects.*;
import org.PiEngine.Math.Vector;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

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
    private int id;

    public Component()
    {
        id = IDGenerator.generateUniqueID();
    }

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

    public Map<String, Object> getProperties() {
        Map<String, Object> properties = new HashMap<>();
        
        
        Field[] fields = this.getClass().getDeclaredFields();
        
        
        for (Field field : fields) {
            field.setAccessible(true); 
            try {
                
                properties.put(field.getName(), field.get(this));
            } catch (IllegalAccessException e) {
                e.printStackTrace(); 
            }
        }
        // System.out.println(properties);
        return properties;
    }


    public void setComponentProperty(String propertyName, JsonElement propertyValue) {
        try {
            
            Field field = this.getClass().getDeclaredField(propertyName);
            field.setAccessible(true); 

            // Handle different property types
            if (field.getType() == String.class) {
                field.set(this, propertyValue.getAsString());
            } else if (field.getType() == Integer.class) {
                field.set(this, propertyValue.getAsInt());
            } else if (field.getType() == Float.class) {
                field.set(this, propertyValue.getAsFloat());
            } else if (field.getType() == Boolean.class) {
                field.set(this, propertyValue.getAsBoolean());
            } else if (field.getType() == Vector.class) {
                JsonObject vectorObject = propertyValue.getAsJsonObject();
                float x = vectorObject.get("x").getAsFloat();
                float y = vectorObject.get("y").getAsFloat();
                float z = vectorObject.get("z").getAsFloat();
                field.set(this, new Vector(x, y, z));  
            } else {
                System.out.println("Unsupported field type: " + field.getType());
            }
        } catch (NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();  
        }
    }

    public GameObject getGameObject() {
        return gameObject;
    }

    public void setGameObject(GameObject gameObject) {
        this.gameObject = gameObject;
    }

    public Transform getTransform() {
        return transform;
    }

    public void setTransform(Transform transform) {
        this.transform = transform;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
