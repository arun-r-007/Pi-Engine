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
     * @param child The child Transform to add
     */
    public void addChild(Transform child)
    {
        childrens.add(child);
        child.parent = this;
    }

    /**
     * Removes a child Transform from this Transform.
     * @param child The child Transform to remove
     */
    public void removeChild(Transform child)
    {
        childrens.remove(child);
        child.parent = null;
        child.gameObject.Location = GameObject.Location(child.gameObject);
    }

    /**
     * Returns the parent Transform.
     * @return The parent Transform, or null if root
     */
    public Transform getParent()
    {
        return parent;
    }

    /**
     * Returns the list of child Transforms.
     * @return List of child Transforms
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
     * @return Local position vector
     */
    public Vector getLocalPosition()
    {
        return position;
    }

    /**
     * Returns the local rotation.
     * @return Local rotation vector
     */
    public Vector getLocalRotation()
    {
        return rotation;
    }

    /**
     * Returns the local scale.
     * @return Local scale vector
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
     * @param pos The new local position
     */
    public void setLocalPosition(Vector pos)
    {
        this.position = pos;
    }

    /**
     * Sets the local rotation.
     * @param rot The new local rotation
     */
    public void setLocalRotation(Vector rot)
    {
        this.rotation = new Vector(rot);
    }

    /**
     * Sets the local scale.
     * @param scale The new local scale
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
     * @return Local transformation Matrix4
     */
    public Matrix4 getLocalMatrix() 
    {
        // Normalize rotation values to range [-360, 360]
        
        rotation = wrapRotation(rotation);
        // Translation matrix
        Vector normalizedRotation = new Vector(
            wrapAngle(rotation.x),
            wrapAngle(rotation.y),
            wrapAngle(rotation.z)
        );
        Matrix4 translation = Matrix4.translate(position);
    
        // Rotation matrices for each axis
        Matrix4 rotationX = Matrix4.rotate(normalizedRotation.x, new Vector(1, 0, 0));
        Matrix4 rotationY = Matrix4.rotate(normalizedRotation.y, new Vector(0, 1, 0));
        Matrix4 rotationZ = Matrix4.rotate(normalizedRotation.z, new Vector(0, 0, 1));
    
        // Scale matrix
        Matrix4 scaleMatrix = Matrix4.scale(scale);
    
        // Combine translation, rotation, and scale
        Matrix4 rotationMatrix = Matrix4.multiply(rotationZ, Matrix4.multiply(rotationY, rotationX));
        return Matrix4.multiply(translation, Matrix4.multiply(rotationMatrix, scaleMatrix));
    }

    /**
     * Returns the world transformation matrix by combining with parent transforms recursively.
     * @return World transformation Matrix4
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
     * @return World position vector
     */
    public Vector getWorldPosition()
    {
        return getWorldMatrix().getTranslation();
    }

    /**
     * Gets the rotation in world space by recursively adding parent rotations.
     * @return World rotation vector
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
     * @return World scale vector
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
     * @param worldPos The new world position
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
     * @param worldRot The new world rotation
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
    }

    /**
     * Sets the world scale by converting it to local scale.
     * @param worldScale The new world scale
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
     * @return The cached world Matrix4
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
     * @param obj The GameObject to set
     */
    public void setGameObject(GameObject obj)
    {
        this.gameObject = obj;
    }

    /**
     * Returns the GameObject that owns this Transform.
     * @return The GameObject
     */
    public GameObject getGameObject()
    {
        return gameObject;
    }

    // ----------------------------
    // Debug and Utility
    // ----------------------------


    public static Vector wrapRotation(Vector rotation)
    {
        return new Vector(
            wrapAngle(rotation.x),
            wrapAngle(rotation.y),
            wrapAngle(rotation.z)
        );
    }

    private static float wrapAngle(float angle)
    {
        angle = angle % 360.0f;
        if (angle < 0)
        {
            angle += 360.0f;
        }
        return angle;
    }

    /**
     * Returns a readable string representation of the Transform.
     * @return String representation
     */
    @Override
    public String toString()
    {
        return String.format(
            "Transform(Position: %s, Rotation: %s, Scale: %s)",
            position, rotation, scale
        );
    }

    /**
     * Destroys this Transform and all its children, removing them from the hierarchy.
     */
    public void destroy()
    {
        position = null;              
        rotation = null;              
        scale = null;                 
        parent = null; 
        if(childrens != null) childrens.clear();
        childrens = null;    
        transformMatrix = null;     
        gameObject = null;       
    }

    // @Override
    // protected void finalize() throws Throwable
    // {
    //     System.out.println("Removed: " + this);
    // }
}
