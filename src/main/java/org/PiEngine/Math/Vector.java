package org.PiEngine.Math;

/**
 * Represents a 3D vector with x, y, z components.
 * Used for positions, directions, normals, etc.
 */
public class Vector
{
    public float x, y, z;

    /** Default constructor: creates a zero vector */
    public Vector()
    {
        this(0, 0, 0);
    }

    /** Uniform constructor: all components set to the same value */
    public Vector(float value)
    {
        this(value, value, value);
    }

    /** Constructor with specific x, y, z values */
    public Vector(float x, float y, float z)
    {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    /** Copy constructor */
    public Vector(Vector copy)
    {
        this(copy.x, copy.y, copy.z);
    }

    /** Returns the squared magnitude (length) of the vector */
    public float sqMagnitude()
    {
        return x * x + y * y + z * z;
    }

    /** Returns the actual magnitude (length) of the vector */
    public float magnitude()
    {
        return (float) Math.sqrt(sqMagnitude());
    }

    /** Returns a new vector in the same direction but with a magnitude of 1 */
    public Vector normal()
    {
        float mag = magnitude();
        if (mag == 0) return new Vector(0, 0, 0);
        return new Vector(x / mag, y / mag, z / mag);
    }

    /** Normalizes this vector in-place (sets magnitude to 1) */
    public void normalize()
    {
        float mag = magnitude();
        if (mag == 0) return;
        x /= mag;
        y /= mag;
        z /= mag;
    }

    /** Adds another vector to this one and returns the result */
    public Vector add(Vector operand)
    {
        return new Vector(x + operand.x, y + operand.y, z + operand.z);
    }

    /** Subtracts another vector from this one and returns the result */
    public Vector sub(Vector operand)
    {
        return new Vector(x - operand.x, y - operand.y, z - operand.z);
    }

    /** Returns the dot product (angle-based similarity) between two vectors */
    public float dot(Vector other)
    {
        return x * other.x + y * other.y + z * other.z;
    }

    /** Returns the cross product (perpendicular vector) of this and another vector */
    public Vector cross(Vector other)
    {
        return new Vector(
            y * other.z - z * other.y,
            z * other.x - x * other.z,
            x * other.y - y * other.x
        );
    }

    /** Multiplies this vector by a scalar and returns the result */
    public Vector scale(float scalar)
    {
        return new Vector(x * scalar, y * scalar, z * scalar);
    }

    /** Returns this vector transformed by a 4x4 matrix (world transform) */
    public Vector transform(Matrix4 matrix)
    {
        return matrix.multiply(this);
    }

    /** Returns the float array representation: [x, y, z] */
    public float[] toFloatArray()
    {
        return new float[] { x, y, z };
    }

    /** Clones this vector */
    @Override
    public Vector clone()
    {
        return new Vector(this);
    }

    /** Checks if another object is a vector and has the same components */
    @Override
    public boolean equals(Object obj)
    {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Vector other = (Vector) obj;
        return Float.compare(x, other.x) == 0 &&
               Float.compare(y, other.y) == 0 &&
               Float.compare(z, other.z) == 0;
    }

    /** Returns a string representation of this vector */
    @Override
    public String toString()
    {
        return "Vector(" + x + ", " + y + ", " + z + ")";
    }

    public void setX(float x) 
    {
        this.x = x;
    }

    public void setY(float y) 
    {
        this.y = y;
    }

    public void setZ(float z) 
    {
        this.z = z;
    }

    public float getX() 
    {
        return x;
    }

    public float getY() 
    {
        return y;
    }

    public float getZ() 
    {
        return z;
    }

}