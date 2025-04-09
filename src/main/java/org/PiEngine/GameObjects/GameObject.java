package org.PiEngine.GameObjects;


import org.PiEngine.Component.Component;
import org.PiEngine.Math.*;

import static org.lwjgl.opengl.GL32.*; 

import java.util.ArrayList;
import java.util.List;

public class GameObject
{
    public String Name;
    public Transform transform;

    /**
     * Constructs a GameObject with a given name and initializes its Transform.
     */
    public GameObject(String name)
    {
        this.Name = name;
        this.transform = new Transform();
        this.transform.setGameObject(this); // ← Important line
    }


    /**
     * Adds a child GameObject by parenting its Transform to this GameObject's Transform.
     */
    public void addChild(GameObject child)
    {
        this.transform.addChild(child.transform);
    }

    // Holds all components attached to this GameObject
    private List<Component> components = new ArrayList<>();

    /**
     * Adds a component to this GameObject.
     * 
     * - Sets the component's reference to this GameObject.
     * - Adds it to the internal component list.
     * - Calls the component's start() method for any initialization logic.
     *
     * @param component The component instance to attach.
     */
    public void addComponent(Component component)
    {
        component.gameObject = this;  // Link the component to this GameObject
        component.transform = this.transform;
        components.add(component);    // Store it in the list
        component.start();            // Trigger its startup behavior
    }

    /**
     * Retrieves the first component of the specified type attached to this GameObject.
     * 
     * - Uses Java generics to return the correct type.
     * - Returns null if no component of the requested type is found.
     *
     * @param <T>  The class type of the component.
     * @param type The class object representing the desired component type.
     * @return The component instance if found; otherwise, null.
     */
    @SuppressWarnings("unchecked")
    public <T extends Component> T getComponent(Class<T> type)
    {
        for (Component c : components)
        {
            if (type.isInstance(c)) // Check if the component matches the requested type
            {
                return (T) c;       // Safe cast and return
            }
        }
        return null; // No matching component found
    }

    /**
     * Called at a fixed interval (using Time.fixedDeltaTime).
     * Typically used for physics or time-consistent logic.
     */
    public void fixedUpdate()
    {
        // Update all components attached to this GameObject
        for (Component c : components)
        {
            c.fixedUpdate();
        }

        // Recursively update all child GameObjects
        for (Transform childTransform : transform.getChildren())
        {
            GameObject child = childTransform.getGameObject();
            if (child != null)
            {
                child.fixedUpdate();
            }
        }
    }


    /**
     * Called once every frame.
     * Updates all components and recursively updates all children.
     */
    public void update()
    {
        // Update all components attached to this GameObject
        for (Component c : components)
        {
            c.update();
        }

        // Recursively update all child GameObjects
        for (Transform childTransform : transform.getChildren())
        {
            GameObject child = childTransform.getGameObject();
            if (child != null)
            {
                child.update();
            }
        }
    }

    /**
     * Called once every frame after update().
     * Responsible for rendering this GameObject and its children.
     */
    public void render()
    {
        // Render all components attached to this GameObject
        for (Component c : components)
        {
            c.render();
        }

        // Recursively render all child GameObjects
        for (Transform childTransform : transform.getChildren())
        {
            GameObject child = childTransform.getGameObject();
            if (child != null)
            {
                child.render();
            }
        }
    }

    /**
     * Called after render(), only in debug mode or when needed.
     * Used to draw debug visuals like bounding boxes, axis lines, etc.
     */
    public void debugRender()
    {
        debugDrawSquare(transform.getWorldPosition(), 0.4f);

        // Debug render all components
        for (Component c : components)
        {
            c.debugRender();
        }

        // Recursively debug render all children
        for (Transform childTransform : transform.getChildren())
        {
            GameObject child = childTransform.getGameObject();
            if (child != null)
            {
                child.debugRender();
            }
        }
    }



    /**
     * Recursively prints the hierarchy of GameObjects starting from this one.
     */
    public void printHierarchy()
    {
        printHierarchy("", true);
    }

    private void printHierarchy(String prefix, boolean isTail)
    {
        System.out.println(prefix + (isTail ? "└── " : "├── ") + Name + " Position:"+transform.getWorldPosition()); //+ "(" + this + ")");

        List<Transform> children = transform.getChildren();
        for (int i = 0; i < children.size(); i++)
        {
            Transform childTransform = children.get(i);
            GameObject childGameObject = childTransform.getGameObject(); // We'll add this reference below
            if (childGameObject != null)
            {
                boolean last = (i == children.size() - 1);
                childGameObject.printHierarchy(prefix + (isTail ? "    " : "│   "), last);
            }
        }
    }

    /**
     * 
     * @param position Position of the Object
     * @param size Size of the Plane
     */

    public void debugDrawSquare(Vector position, float size)
    {
        float half = size / 2.0f;
        float x = position.x;
        float y = position.y;
        float z = position.z;

        //glColor3f(1.0f, 0.0f, 0.0f); // Red color

        glBegin(GL_TRIANGLES);

        // Triangle 1
        glVertex3f(x - half, y - half, z);
        glVertex3f(x + half, y - half, z);
        glVertex3f(x + half, y + half, z);

        // Triangle 2
        glVertex3f(x - half, y - half, z);
        glVertex3f(x + half, y + half, z);
        glVertex3f(x - half, y + half, z);

        glEnd();
    }


    /**
     * Reparents this GameObject to a new parent GameObject.
     * 
     * This will remove this GameObject from its current parent's children list
     * and add it to the specified new parent's children list.
     *
     * @param newParent The GameObject to become the new parent of this GameObject.
     */
    public void reparentTo(GameObject newParent)
    {
        // Remove from current parent if it exists
        Transform currentParent = this.transform.getParent();
        Vector gpos = transform.getWorldPosition();
        Vector grot = transform.getWorldRotation();
        Vector gscl = transform.getWorldScale();

        if (currentParent != null)
        {
            currentParent.getChildren().remove(this.transform);
        }

        // Add to new parent's children
        newParent.addChild(this);
        this.transform.setWorldPosition(gpos);
        this.transform.setWorldRotation(grot);
        this.transform.setWorldScale(gscl);

    }



    /**
     * Returns a readable string representation of the GameObject.
     */
    @Override
    public String toString()
    {
        return String.format("GameObject(Name: %s, %s)", Name, transform);
    }

     /**
     * @return return all the components of a Gameobject 
     */
    public List<Component> getAllComponents() {
        return new ArrayList<>(components);
    }    
}