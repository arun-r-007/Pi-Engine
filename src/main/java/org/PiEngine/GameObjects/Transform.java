package org.PiEngine.GameObjects;

import org.PiEngine.Math.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Represents a transform in a hierarchical scene graph.
 * Stores position, rotation, and scale, and supports parent-child relationships.
 * Useful for calculating local and world space positions in 2D/3D space.
 */
public class Transform
{
    private Vector position;  // Local position relative to parent
    private Vector rotation;  // Local rotation (only Z used in 2D)
    private Vector scale;     // Local scale

    private Transform parent; // Reference to the parent transform
    private List<Transform> childrens; // List of child transforms

    private Matrix4 transformMatrix; // Cached world transformation matrix

    private GameObject gameObject;

    /**
     * Constructs a new Transform with default position (0,0,0), 
     * rotation (0,0,0), and scale (1,1,1).
     */
    public Transform()
    {
        position = new Vector(0, 0, 0);
        rotation = new Vector(0, 0, 0);
        scale = new Vector(1, 1, 1);
        childrens = new ArrayList<>();
        transformMatrix = Matrix4.identity();
    }

    /**
     * Adds a child Transform to this Transform.
     */
    public void addChild(Transform child)
    {
        childrens.add(child);
        child.parent = this;
    }

    /**
     * Removes a child Transform from this Transform.
     */
    public void removeChild(Transform child)
    {
        childrens.remove(child);
        child.parent = null;
    }

    /**
     * Returns the local transformation matrix built from position, rotation, and scale.
     */
    public Matrix4 getLocalMatrix()
    {
        Matrix4 translation = Matrix4.translate(position);
        Matrix4 rotationMatrix = Matrix4.rotate(rotation.z, new Vector(0, 0, 1)); 
        Matrix4 scaleMatrix = Matrix4.scale(scale);

        return Matrix4.multiply(translation, Matrix4.multiply(rotationMatrix, scaleMatrix));
    }

    /**
     * Returns the world transformation matrix by combining with parent transforms recursively.
     */
    public Matrix4 getWorldMatrix()
    {
        Matrix4 local = getLocalMatrix();
        if (parent != null)
        {
            return Matrix4.multiply(parent.getWorldMatrix(), local);
        }
        return local;
    }

    /**
     * Recursively updates this transform and its children's transformation matrices.
     */
    public void updateMatrix()
    {
        transformMatrix = getWorldMatrix();
        for (Transform child : childrens)
        {
            child.updateMatrix();
        }
    }

    /**
     * Gets the local position relative to the parent.
     */
    public Vector getLocalPosition() { return position; }

    /**
     * Gets the local rotation.
     */
    public Vector getLocalRotation() { return rotation; }

    /**
     * Gets the local scale.
     */
    public Vector getLocalScale()    { return scale; }

    /**
     * Gets the position in world space by extracting translation from the world matrix.
     */
    public Vector getWorldPosition()
    {
        return getWorldMatrix().getTranslation();
    }

    /**
     * Sets the local position.
     */
    public void setLocalPosition(Vector pos) { this.position = pos; }

    /**
     * Sets the local rotation.
     */
    public void setLocalRotation(Vector rot) { this.rotation = rot; }

    /**
     * Sets the local scale.
     */
    public void setLocalScale(Vector scale)  { this.scale = scale; }

    /**
     * Sets the position in world space.
     * Internally converts it to local space using the inverse of the parent's world matrix.
     */
    public void setWorldPosition(Vector worldPos)
    {
        if (parent == null)
        {
            this.position = worldPos;
        }
        else
        {
            Matrix4 inverseParent = Matrix4.invert(parent.getWorldMatrix());
            Matrix4 worldMat = Matrix4.translate(worldPos);
            Matrix4 localMat = Matrix4.multiply(inverseParent, worldMat);
            this.position = localMat.getTranslation();
        }
    }

    /**
     * Returns the cached world transformation matrix.
     */
    public Matrix4 getMatrix() { return transformMatrix; }

    /**
     * Returns the parent Transform.
     */
    public Transform getParent() { return parent; }

    /**
     * Returns the list of children Transforms.
     */
    public List<Transform> getChildren() { return childrens; }


    /**
     * Sets the GameObject that owns this Transform.
     */
    public void setGameObject(GameObject obj)
    {
        this.gameObject = obj;
    }

    /**
     * Returns the GameObject that owns this Transform.
     */
    public GameObject getGameObject()
    {
        return gameObject;
    }

    /**
     * Returns a readable string representation of the Transform.
     */
    @Override
    public String toString()
    {
        return String.format("Transform(Position: %s, Rotation: %s, Scale: %s)", 
            position, rotation, scale);
    }

}
