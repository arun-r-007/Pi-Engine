package org.PiEngine.Math;

/**
 * Represents a 3D vector with x, y, z components.
 * Used for positions, directions, normals, and mathematical operations in 3D space.
 * This class provides comprehensive functionality for vector mathematics including
 * basic arithmetic, transformations, and utility methods.
 */
public class Vector
{
    /** The x, y, z components of the vector */
    public float x, y, z;

    /**
     * Default constructor: creates a zero vector (0,0,0)
     */
    public Vector()
    {
        this(0, 0, 0);
    }

    /**
     * Uniform constructor: all components set to the same value
     * @param value The value to set for all components
     */
    public Vector(float value)
    {
        this(value, value, value);
    }

    /**
     * Constructor with specific x, y, z values
     * @param x The x component
     * @param y The y component
     * @param z The z component
     */
    public Vector(float x, float y, float z)
    {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    /**
     * Copy constructor
     * @param copy The vector to copy
     */
    public Vector(Vector copy)
    {
        this(copy.x, copy.y, copy.z);
    }

    /**
     * Returns the squared magnitude (length) of the vector.
     * More efficient than magnitude() for comparisons.
     * @return The squared length of the vector
     */
    public float sqMagnitude()
    {
        return x * x + y * y + z * z;
    }

    /**
     * Returns the actual magnitude (length) of the vector
     * @return The length of the vector
     */
    public float magnitude()
    {
        return (float) Math.sqrt(sqMagnitude());
    }

    /**
     * Returns a new normalized vector (magnitude of 1) in the same direction.
     * Returns zero vector if magnitude is 0.
     * @return A new normalized copy of this vector
     */
    public Vector normal()
    {
        float mag = magnitude();
        if (mag == 0) return new Vector(0, 0, 0);
        return new Vector(x / mag, y / mag, z / mag);
    }

    /**
     * Normalizes this vector in-place (sets magnitude to 1).
     * Does nothing if magnitude is 0.
     */
    public void normalize()
    {
        float mag = magnitude();
        if (mag == 0) return;
        x /= mag;
        y /= mag;
        z /= mag;
    }

    /**
     * Adds another vector to this one
     * @param operand Vector to add
     * @return New vector containing the sum
     */
    public Vector add(Vector operand)
    {
        return new Vector(x + operand.x, y + operand.y, z + operand.z);
    }

    /**
     * Subtracts another vector from this one
     * @param operand Vector to subtract
     * @return New vector containing the difference
     */
    public Vector sub(Vector operand)
    {
        return new Vector(x - operand.x, y - operand.y, z - operand.z);
    }

    /**
     * Calculates the dot product with another vector.
     * The dot product represents the cosine of the angle between vectors multiplied by their magnitudes.
     * @param other Vector to calculate dot product with
     * @return Scalar dot product result
     */
    public float dot(Vector other)
    {
        return x * other.x + y * other.y + z * other.z;
    }

    /**
     * Calculates the cross product with another vector.
     * The cross product is a vector perpendicular to both input vectors.
     * @param other Vector to calculate cross product with
     * @return New vector perpendicular to this and other
     */
    public Vector cross(Vector other)
    {
        return new Vector(
            y * other.z - z * other.y,
            z * other.x - x * other.z,
            x * other.y - y * other.x
        );
    }

    /**
     * Multiplies the vector by a scalar value
     * @param scalar Value to multiply by
     * @return New scaled vector
     */
    public Vector scale(float scalar)
    {
        return new Vector(x * scalar, y * scalar, z * scalar);
    }

    /**
     * Transforms the vector by a 4x4 matrix
     * @param matrix Transform matrix to apply
     * @return Transformed vector
     */
    public Vector transform(Matrix4 matrix)
    {
        return matrix.multiply(this);
    }

    /**
     * Converts the vector to a float array
     * @return Array containing [x, y, z]
     */
    public float[] toFloatArray()
    {
        return new float[] { x, y, z };
    }

    /**
     * Creates a deep copy of this vector
     * @return New vector with same components
     */
    @Override
    public Vector clone()
    {
        return new Vector(this);
    }

    /**
     * Checks vector equality
     * @param obj Object to compare with
     * @return true if vectors are equal
     */
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

    /**
     * Linearly interpolates between two vectors
     * @param a Start vector
     * @param b End vector
     * @param t Interpolation factor (0 to 1)
     * @return Interpolated vector
     */
    public static Vector lerp(Vector a, Vector b, float t)
    {
        float x = MathF.lerp(a.x, b.x, t);
        float y = MathF.lerp(a.y, b.y, t);
        float z = MathF.lerp(a.z, b.z, t);
        return new Vector(x, y, z);
    }

    /**
     * Returns a string representation of the vector
     * @return String in format "Vector(x, y, z)"
     */
    @Override
    public String toString()
    {
        return "Vector(" + x + ", " + y + ", " + z + ")";
    }

    /**
     * Sets this vector's components from another vector
     * @param v Source vector
     */
    public void SetVector(Vector v)
    {
        x = v.x;
        y = v.y;
        z = v.z;
    }

    /**
     * Sets the X component
     * @param x New X value
     */
    public void setX(float x) 
    {
        this.x = x;
    }

    /**
     * Sets the Y component
     * @param y New Y value
     */
    public void setY(float y) 
    {
        this.y = y;
    }

    /**
     * Sets the Z component
     * @param z New Z value
     */
    public void setZ(float z) 
    {
        this.z = z;
    }

    /**
     * Gets the X component
     * @return X value
     */
    public float getX() 
    {
        return x;
    }

    /**
     * Gets the Y component
     * @return Y value
     */
    public float getY() 
    {
        return y;
    }

    /**
     * Gets the Z component
     * @return Z value
     */
    public float getZ() 
    {
        return z;
    }

    /**
     * Calculates the Euclidean distance between two vectors
     * @param a First vector
     * @param b Second vector
     * @return Distance between vectors, or -1 if either vector is null
     */
    public static float Distance(Vector a, Vector b)
    {
        float dist = -1.0f;
        if(a != null && b != null)
        {
            Vector disVector = a.sub(b);
            dist = disVector.magnitude();
        }
        return dist;
    }
}