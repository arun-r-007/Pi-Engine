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
    private Vector position;              // Local position relative to parent
    private Vector rotation;              // Local rotation (only Z used in 2D)
    private Vector scale;                 // Local scale

    private Transform parent;             // Reference to the parent transform
    private List<Transform> childrens;    // List of child transforms
    private Matrix4 transformMatrix;      // Cached world transformation matrix

    private GameObject gameObject;        // Reference to owning GameObject

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

    // ----------------------------
    // Hierarchy Management
    // ----------------------------

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
     * Returns the parent Transform.
     */
    public Transform getParent()
    {
        return parent;
    }

    /**
     * Returns the list of child Transforms.
     */
    public List<Transform> getChildren()
    {
        return childrens;
    }

    // ----------------------------
    // Local Transform Getters
    // ----------------------------

    /**
     * Returns the local position relative to the parent.
     */
    public Vector getLocalPosition()
    {
        return position;
    }

    /**
     * Returns the local rotation.
     */
    public Vector getLocalRotation()
    {
        return rotation;
    }

    /**
     * Returns the local scale.
     */
    public Vector getLocalScale()
    {
        return scale;
    }

    // ----------------------------
    // Local Transform Setters
    // ----------------------------

    /**
     * Sets the local position.
     */
    public void setLocalPosition(Vector pos)
    {
        this.position = pos;
    }

    /**
     * Sets the local rotation.
     */
    public void setLocalRotation(Vector rot)
    {
        this.rotation = new Vector(rot);
        this.rotation.z = ((this.rotation.z % 360) + 360) % 360;
    }

    /**
     * Sets the local scale.
     */
    public void setLocalScale(Vector scale)
    {
        this.scale = scale;
    }

    // ----------------------------
    // World Transform Getters
    // ----------------------------

    /**
     * Returns the transformation matrix built from position, rotation, and scale.
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
     * Gets the position in world space.
     */
    public Vector getWorldPosition()
    {
        return getWorldMatrix().getTranslation();
    }

    /**
     * Gets the rotation in world space by recursively adding parent rotations.
     */
    public Vector getWorldRotation()
    {
        if (parent != null)
        {
            return parent.getWorldRotation().add(this.rotation);
        }
        return new Vector(rotation);
    }

    /**
     * Gets the scale in world space by accumulating parent scales.
     */
    public Vector getWorldScale()
    {
        if (parent == null)
        {
            return new Vector(scale);
        }

        Vector parentScale = parent.getWorldScale();
        return new Vector(
            parentScale.x * scale.x,
            parentScale.y * scale.y,
            parentScale.z * scale.z
        );
    }

    // ----------------------------
    // World Transform Setters
    // ----------------------------

    /**
     * Sets the position in world space by converting it to local space.
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
     * Sets the world rotation by converting it to local rotation.
     * Only Z rotation is considered in 2D.
     */
    public void setWorldRotation(Vector worldRot)
    {
        if (parent == null)
        {
            this.rotation = new Vector(worldRot);
        }
        else
        {
            Vector parentWorldRot = parent.getWorldRotation();
            this.rotation = worldRot.sub(parentWorldRot);
        }
        this.rotation.z = ((this.rotation.z % 360) + 360) % 360;
    }

    /**
     * Sets the world scale by converting it to local scale.
     */
    public void setWorldScale(Vector worldScale)
    {
        if (parent == null)
        {
            this.scale = new Vector(worldScale);
        }
        else
        {
            Vector parentScale = parent.getWorldScale();
            this.scale = new Vector(
                worldScale.x / parentScale.x,
                worldScale.y / parentScale.y,
                worldScale.z / parentScale.z
            );
        }
    }

    // ----------------------------
    // Matrix and Update
    // ----------------------------

    /**
     * Returns the cached world transformation matrix.
     */
    public Matrix4 getMatrix()
    {
        return transformMatrix;
    }

    /**
     * Recursively updates this transform and all child transforms.
     */
    public void updateMatrix()
    {
        transformMatrix = getWorldMatrix();
        for (Transform child : childrens)
        {
            child.updateMatrix();
        }
    }

    // ----------------------------
    // GameObject Reference
    // ----------------------------

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

    // ----------------------------
    // Debug and Utility
    // ----------------------------

    /**
     * Returns a readable string representation of the Transform.
     */
    @Override
    public String toString()
    {
        return String.format(
            "Transform(Position: %s, Rotation: %s, Scale: %s)",
            position, rotation, scale
        );
    }
}
