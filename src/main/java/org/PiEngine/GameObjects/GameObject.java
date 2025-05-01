package org.PiEngine.GameObjects;


import org.PiEngine.Component.Component;
import org.PiEngine.Core.Camera;
import org.PiEngine.Core.LayerManager;
import org.PiEngine.Math.*;

import java.util.ArrayList;
import java.util.List;

public class GameObject
{
    public String Name;
    public Transform transform;
    private int id;

    private int layer = LayerManager.getLayerBit("Layer0");

    /**
     * Constructs a GameObject with a given name and initializes its Transform.
     */
    public GameObject(String name)
    {
        this.Name = name;
        this.transform = new Transform();
        this.transform.setGameObject(this);
        id = IDGenerator.generateUniqueID();
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
        component.safeStart();            // Trigger its startup behavior
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
     * Called once every frame.
     * Updates all components and recursively updates all children.
     */
    public void update()
    {
        // Update all components attached to this GameObject
        for (Component c : components)
        {
            
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
     * Called once every frame after update().
     * Responsible for rendering this GameObject and its children.
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
     * Called after render(), only in debug mode or when needed.
     * Used to draw debug visuals like bounding boxes, axis lines, etc.
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

    public static String Location(GameObject gb)
    {
        if(gb.transform.getParent() == null) return "/" + gb.Name; 
        return Location(gb.transform.getParent().getGameObject()) + "/" + gb.Name; 
    }

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
     * Reparents this GameObject to a new parent GameObject.
     * 
     * This will remove this GameObject from its current parent's children list
     * and add it to the specified new parent's children list.
     *
     * @param newParent The GameObject to become the new parent of this GameObject.
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


    public int getLayerBit()
    {
        return layer;
    }

    public void setLayer(int newLayer)
    {
        this.layer = newLayer;
    }

    /**
     * Sets the layer(bit) for this object only.
     */
    public void setLayerOnly(int newLayer)
    {
        this.layer = newLayer;
    }

    /**
     * Sets the layer(bit) for this object and all its children recursively.
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
     */
    @Override
    public String toString()
    {
        return String.format("GameObject(Name: %s)", Name);
    }

     /**
     * @return return all the components of a Gameobject 
     */
    public List<Component> getAllComponents() {
        return new ArrayList<>(components);
    }    

    public void removeComponent(Component cmp)
    {
        components.remove(cmp);
    }

    public void destroy()
    {
        for (Transform childTransform : new ArrayList<>(transform.getChildren()))
        {
            GameObject child = childTransform.getGameObject();
            if (child != null)
            {
                child.destroy(); 
            }
        }

        
        transform.getChildren().clear();
        components.clear();

        Transform parent = transform.getParent();
        if (parent != null)
        {
            parent.getChildren().remove(transform);
        }

        transform.setGameObject(null);
    }

    @Override
    protected void finalize() throws Throwable
    {
        // System.out.println("Removed: " + this.Name);
    }

    public int getId() {
        return id;
    }


    public String getName() {
        return Name;
    }


    public void setName(String name) {
        Name = name;
    }


    public Transform getTransform() {
        return transform;
    }


    public void setTransform(Transform transform) {
        this.transform = transform;
    }


    public void setId(int id) {
        this.id = id;
    }


    public int getLayer() {
        return layer;
    }


    public List<Component> getComponents() {
        return components;
    }


    public void setComponents(List<Component> components) {
        this.components = components;
    }

    public int getComponentCount()
    {
        return components.size();
    }

}