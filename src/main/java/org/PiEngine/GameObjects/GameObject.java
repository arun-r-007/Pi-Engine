package org.PiEngine.GameObjects;


import org.PiEngine.Math.*;
import static org.lwjgl.opengl.GL11.*;

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
    


    public void update()
    {

        debugDrawSquare(transform.getWorldPosition(), 1.0f);
        
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

    public void debugDrawSquare(Vector position, float size)
    {
        float half = size / 2.0f;
        float x = position.x;
        float y = position.y;

        glColor3f(1.0f, 0.0f, 0.0f); // Red color

        glBegin(GL_TRIANGLES);

        // Triangle 1
        glVertex2f(x - half, y - half);
        glVertex2f(x + half, y - half);
        glVertex2f(x + half, y + half);

        // Triangle 2
        glVertex2f(x - half, y - half);
        glVertex2f(x + half, y + half);
        glVertex2f(x - half, y + half);

        glEnd();

        //System.err.println(Name); // debug print
    }

    /**
     * Returns a readable string representation of the GameObject.
     */
    @Override
    public String toString()
    {
        return String.format("GameObject(Name: %s, %s)", Name, transform);
    }
}