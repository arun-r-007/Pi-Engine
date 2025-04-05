package org.PiEngine.Math;

public class Vector 
{
    public float x;
    public float y;
    public float z;

    Vector()
    {
        x = 0; y = 0; z = 0;
    }

    public Vector(float i)
    {
        x = i; y = i; z = i;
    }

    Vector(float x, float y, float z)
    {
        this.x = x; this.y = y; this.z = z;
    }

    Vector(Vector copy)
    {
        this.x = copy.x; this.y = copy.y; this.z = copy.z;
    }

    public float sqmagnitude()
    {
        return  (x*x) + (y*y) + (z*z);
    }

    public float magnitude()
    {
        return (float)Math.sqrt(sqmagnitude());
    }

    public Vector normal() {
        float mag = magnitude();
        if (mag == 0) return new Vector(0, 0, 0);
        return new Vector(x / mag, y / mag, z / mag);
    }

    public void normalize() {
        float mag = magnitude();
        if (mag == 0) return;
        x /= mag;
        y /= mag;
        z /= mag;
    }

    public Vector add(Vector operand)
    {
        return new Vector(operand.x + x, operand.y + y, operand.z + z);
    }

    public Vector sub(Vector operand)
    {
        return new Vector(x - operand.x, y - operand.y, z - operand.z);
    }

    @Override
    public String toString() 
    {    
        return "Vector(" + x + ", " + y + ", " + z +")";
    }

    public float dot(Vector other) 
    {
        return this.x * other.x + this.y * other.y + this.z * other.z;
    }

    public Vector cross(Vector other) 
    {
        return new Vector
        (
            this.y * other.z - this.z * other.y,
            this.z * other.x - this.x * other.z,
            this.x * other.y - this.y * other.x
        );
    }

    public Vector scale(float scalar) 
    {
        return new Vector(this.x * scalar, this.y * scalar, this.z * scalar);
    }

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

    @Override
    public Vector clone() 
    {
        return new Vector(this);
    }

    public float[] toFloatArray() 
    {
        return new float[] { x, y, z };
    }
}