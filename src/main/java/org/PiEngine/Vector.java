package org.PiEngine;

class Vector 
{
    public double x;
    public double y;
    public double z;

    Vector()
    {
        x = 0; y = 0; z = 0;
    }

    Vector(double i)
    {
        x = i; y = i; z = i;
    }

    Vector(double x, double y, double z)
    {
        this.x = x; this.y = y; this.z = z;
    }

    Vector(Vector copy)
    {
        this.x = copy.x; this.y = copy.y; this.z = copy.z;
    }

    public double sqmagnitude()
    {
        double i = (x*x) + (y*y) + (z*z); 
        return i;
    }

    public double magnitude()
    {
        return Math.sqrt(sqmagnitude());
    }

    public Vector normal()
    {
        return new Vector(x/magnitude(), y/magnitude(), z/magnitude());
    }

    public void normalize()
    {
        Vector NormalVector = normal();
        x = NormalVector.x; y = NormalVector.y; z = NormalVector.z;
    }

    public Vector add(Vector operand)
    {
        return new Vector(operand.x + x, operand.y + y, operand.z + z);
    }

    public Vector sub(Vector operand)
    {
        return new Vector(operand.x - x, operand.y - y, operand.z - z);
    }

    @Override
    public String toString() 
    {    
        return "Vector(" + x + ", " + y + ", " + z +")";
    }
}