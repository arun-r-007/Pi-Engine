package org.PiEngine.GameObjects;


import org.PiEngine.Component.Component;
import org.PiEngine.Core.Camera;
import org.PiEngine.Core.LayerManager;
import org.PiEngine.Math.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;



public class GameObject
{
    /** The name of the GameObject */
    public String Name;
    /** The Transform component for position, rotation, scale */
    public Transform transform;
    /** Unique identifier for this GameObject */
    private int id;
    /** Hierarchical path of this GameObject */
    public String Location; 
    /** Layer bitmask for rendering and filtering */
    private int layer = LayerManager.getLayerBit("Layer0");

    /**
     * Constructs a GameObject with a given name and initializes its Transform.
     * @param name The name of the GameObject
     */
    public GameObject(String name)
    {
        this.Name = name;
        this.transform = new Transform();
        this.transform.setGameObject(this);
        id = IDGenerator.generateUniqueID();
        Location = Location(this);
    }

    /**
     * Adds a child GameObject by parenting its Transform to this GameObject's Transform.
     * @param child The child GameObject to add
     */
    public void addChild(GameObject child)
    {
        this.transform.addChild(child.transform);
        child.Location = Location(child);
        // System.out.println(child.Name + " " + child.Location);
    }

    /** Holds all components attached to this GameObject */
    private List<Component> components = new ArrayList<>();

    /**
     * Adds a component to this GameObject.
     * Sets the component's reference to this GameObject and its transform, adds it to the internal list, and calls its start method.
     * @param component The component instance to attach
     */
    public void addComponent(Component component)
    {
        component.gameObject = this;
        component.transform = this.transform;
        components.add(component);    
        component.safeStart();          
    }

    /**
     * Retrieves the first component of the specified type attached to this GameObject.
     * Uses Java generics to return the correct type. Returns null if not found.
     * @param <T>  The class type of the component
     * @param type The class object representing the desired component type
     * @return The component instance if found; otherwise, null
     */
    public <T extends Component> T getComponent(Class<T> type)
    {
        for (Component c : components)
        {
            if (type.isInstance(c)) 
            {
                return (T) c;       
            }
        }
        return null; 
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
            c.safeFixedUpdate();
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
     * Called once every frame. Updates all components and recursively updates all children.
     */
    public void update()
    {
        // Update all components attached to this GameObject
        for (Component c : components)
        {
            c.updateFields();
            c.safeUpdate();
            
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
     * Called once every frame after update(). Responsible for rendering this GameObject and its children.
     * @param camera The camera to render with
     * @param layerMask The layer mask for rendering
     */
    public void render(Camera camera, int layerMask)
    {
        // Render all components attached to this GameObject
        if(camera.canRenderLayer(layer))
        {
            for (Component c : components)
            {
                if((layer & layerMask) > 0)
                {
                    c.safeRender(camera);
                }
            }
        }

        // Recursively render all child GameObjects
        for (Transform childTransform : transform.getChildren())
        {
            GameObject child = childTransform.getGameObject();
            if (child != null)
            {
                child.render(camera, layerMask);
            }
        }
    }

    /**
     * Called after render(), only in debug mode or when needed. Used to draw debug visuals like bounding boxes, axis lines, etc.
     */
    public void debugRender()
    {
        //debugDrawSquare(transform.getWorldPosition(), 0.4f);

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
     * Recursively updates the Location string for this GameObject and all children.
     */
    public void UpdateLocation()
    {
        for (Transform childTransform : transform.getChildren())
        {
            GameObject child = childTransform.getGameObject();
            if (child != null)
            {
                child.UpdateLocation();
                child.Location = GameObject.Location(child);
            }
        }
    }

    /**
     * Returns the hierarchical path of the GameObject in the scene tree.
     * @param gb The GameObject
     * @return The path string
     */
    public static String Location(GameObject gb)
    {
        if(gb.transform.getParent() == null) return "/" + gb.Name; 
        return Location(gb.transform.getParent().getGameObject()) + "/" + gb.Name; 
    }

    /**
     * Finds a GameObject by its path, recursively searching the hierarchy.
     * @param path The path string
     * @param root The root GameObject to search from
     * @return The found GameObject, or null if not found
     */
    public static GameObject findGameObject(String path, GameObject root)
    {
        // Trim leading slash and split path
        if (path.startsWith("/"))
        {
            path = path.substring(1);
        }

        String[] parts = path.split("/", 2); // Split into current part and the rest
        String currentName = parts[0];

        // Check current level name
        if (!root.Name.equals(currentName))
        {
            return null;
        }

        // Base case: if there's no more path, return current object
        if (parts.length == 1)
        {
            return root;
        }

        // Recurse into children
        for (Transform childTransform : root.transform.getChildren())
        {
            GameObject child = childTransform.getGameObject();
            GameObject result = findGameObject(parts[1], child);
            if (result != null)
            {
                return result;
            }
        }

        return null; // No match found
    }

    /**
     * Finds a GameObject by its path using an iterative approach.
     * @param path The path string
     * @param root The root GameObject to search from
     * @return The found GameObject, or null if not found
     */
    public static GameObject findGameObjectItrative(String path, GameObject root)
    {
        String[] parts = path.startsWith("/") ? path.substring(1).split("/") : path.split("/");

        // Check if the path starts from this root
        if (!root.Name.equals(parts[0]))
        {
            return null; // Root name doesn't match
        }

        GameObject current = root;

        // Start from second part
        for (int i = 1; i < parts.length; i++)
        {
            String part = parts[i];
            boolean found = false;

            for (Transform childTransform : current.transform.getChildren())
            {
                GameObject child = childTransform.getGameObject();
                if (child.Name.equals(part))
                {
                    current = child;
                    found = true;
                    break;
                }
            }

            if (!found)
            {
                return null; // Path segment not found
            }
        }

        return current;
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
        System.out.println(prefix + (isTail ? "└── " : "├── ") + Name + " "+ LayerManager.getLayerNameFromBitmask(layer)); //+ "(" + this + ")");

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
     * Reparents this GameObject to a new parent GameObject, preserving world transform.
     * @param newParent The GameObject to become the new parent
     */
    public void reparentTo(GameObject newParent)
    {
        Transform cParent = newParent.transform;
        while (cParent != null) 
        {
            if(cParent == transform)
            {
                reparentToChild();
                break;
            }
            cParent = cParent.getParent();  
        }
        // Remove from current parent if it exists
        Transform currentParent = this.transform.getParent();
        Vector gpos = transform.getWorldPosition();
        Vector grot = transform.getWorldRotation();
        Vector gscl = transform.getWorldScale();

        if (currentParent != null)
        {
            currentParent.removeChild(transform);
        }

        // Add to new parent's children
        newParent.addChild(this);
        this.transform.setWorldPosition(gpos);
        this.transform.setWorldRotation(grot);
        this.transform.setWorldScale(gscl);
        this.Location = Location(this);
    }

    private void reparentToChild()
    {
        List<GameObject> repatentObjects = new ArrayList();
        GameObject Parent = transform.getParent().getGameObject();
        for (Transform gb : transform.getChildren())
        {
            repatentObjects.add(gb.getGameObject());
        }
        for (GameObject gameObject : repatentObjects) 
        {
            gameObject.reparentTo(Parent);
        }
    }

    /**
     * Gets the layer bitmask.
     * @return The layer bitmask
     */
    public int getLayerBit()
    {
        return layer;
    }

    /**
     * Sets the layer bitmask.
     * @param newLayer The new layer bitmask
     */
    public void setLayer(int newLayer)
    {
        this.layer = newLayer;
    }

    /**
     * Sets the layer for this object only.
     * @param newLayer The new layer bitmask
     */
    public void setLayerOnly(int newLayer)
    {
        this.layer = newLayer;
    }

    /**
     * Sets the layer for this object and all its children recursively.
     * @param layerBit The new layer bitmask
     */
    public void setLayerRecursively(int layerBit)
    {
        this.layer = layerBit;
        for (Transform childTransform : transform.getChildren())
        {
            childTransform.getGameObject().setLayerRecursively(layerBit);
        }
    }

    /**
     * Sets the layer from layer(bit) name (for convenience).
     * @param layerName The name of the layer
     * @param recursive Whether to set recursively
     */
    public void setLayerByName(String layerName, boolean recursive)
    {
        int bit = LayerManager.getLayerBit(layerName);
        if (recursive)
        {
            setLayerRecursively(bit);
        }
        else
        {
            setLayerOnly(bit);
        }
    }

    /**
     * Returns a readable string representation of the GameObject.
     * @return String representation
     */
    @Override
    public String toString()
    {
        return String.format("GameObject(Name: %s)", Name);
    }

    /**
     * Returns all the components of a GameObject.
     * @return List of components
     */
    public List<Component> getAllComponents() {
        return new ArrayList<>(components);
    }    

    /**
     * Removes a component from this GameObject.
     * @param cmp The component to remove
     */
    public void removeComponent(Component cmp)
    {
        components.remove(cmp);
    }

    /**
     * Destroys a GameObject and all its children, removing them from the hierarchy and clearing components.
     * @param thisGb The GameObject to destroy
     */
    public static void destroy(GameObject thisGb)
    {
        Collection<Transform> children = thisGb.transform.getChildren();
        if (children != null)
        {
            for (Transform childTransform : new ArrayList<>(children))
            {
                GameObject child = childTransform.getGameObject();
                if (child != null)
                {
                    destroy(child);
                }
            }
        }
        Transform parent = thisGb.transform.getParent();
        if (parent != null)
        {
            parent.removeChild(thisGb.transform);
        }
        thisGb.components.clear();
        thisGb.transform.destroy();
        if(children != null) children.clear();
        children = null; 
    }

    /**
     * Gets the unique ID of this GameObject.
     * @return The unique ID
     */
    public int getId() {
        return id;
    }

    /**
     * Gets the name of this GameObject.
     * @return The name
     */
    public String getName() {
        return Name;
    }

    /**
     * Sets the name of this GameObject.
     * @param name The new name
     */
    public void setName(String name) {
        Name = name;
    }

    /**
     * Gets the Transform of this GameObject.
     * @return The Transform
     */
    public Transform getTransform() {
        return transform;
    }

    /**
     * Sets the Transform of this GameObject.
     * @param transform The new Transform
     */
    public void setTransform(Transform transform) {
        this.transform = transform;
    }

    /**
     * Sets the unique ID of this GameObject.
     * @param id The new ID
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Gets the layer of this GameObject.
     * @return The layer
     */
    public int getLayer() {
        return layer;
    }

    /**
     * Gets the list of components attached to this GameObject.
     * @return List of components
     */
    public List<Component> getComponents() {
        return components;
    }

    /**
     * Sets the list of components attached to this GameObject.
     * @param components The new list of components
     */
    public void setComponents(List<Component> components) {
        this.components = components;
    }

    /**
     * Gets the number of components attached to this GameObject.
     * @return The number of components
     */
    public int getComponentCount()
    {
        return components.size();
    }

}