package org.PiEngine.Math;

public class Matrix4 
{
    public float[] elements = new float[16];

    public Matrix4() 
    {
        identity();
    }

    public static Matrix4 identity() 
    {
        Matrix4 result = new Matrix4();
        for (int i = 0; i < 16; i++) result.elements[i] = 0;
        result.elements[0 + 0 * 4] = 1.0f;
        result.elements[1 + 1 * 4] = 1.0f;
        result.elements[2 + 2 * 4] = 1.0f;
        result.elements[3 + 3 * 4] = 1.0f;
        return result;
    }

    public static Matrix4 multiply(Matrix4 a, Matrix4 b) 
    {
        Matrix4 result = new Matrix4();
        for (int row = 0; row < 4; row++) 
        {
            for (int col = 0; col < 4; col++) 
            {
                float sum = 0;
                for (int i = 0; i < 4; i++) 
                {
                    sum += a.elements[i + row * 4] * b.elements[col + i * 4];
                }
                result.elements[col + row * 4] = sum;
            }
        }
        return result;
    }

    public static Matrix4 translate(Vector vector) 
    {
        Matrix4 result = identity();
        result.elements[3 + 0 * 4] = (float) vector.x;
        result.elements[3 + 1 * 4] = (float) vector.y;
        result.elements[3 + 2 * 4] = (float) vector.z;
        return result;
    }

    public static Matrix4 scale(Vector vector) 
    {
        Matrix4 result = identity();
        result.elements[0 + 0 * 4] = (float) vector.x;
        result.elements[1 + 1 * 4] = (float) vector.y;
        result.elements[2 + 2 * 4] = (float) vector.z;
        return result;
    }

    public static Matrix4 rotate(float angleDeg, Vector axis) 
    {
        Matrix4 result = identity();

        float rad = (float) Math.toRadians(angleDeg);
        float cos = (float) Math.cos(rad);
        float sin = (float) Math.sin(rad);
        float omc = 1.0f - cos;

        float x = (float) axis.x;
        float y = (float) axis.y;
        float z = (float) axis.z;

        result.elements[0 + 0 * 4] = cos + x * x * omc;
        result.elements[0 + 1 * 4] = y * x * omc + z * sin;
        result.elements[0 + 2 * 4] = z * x * omc - y * sin;

        result.elements[1 + 0 * 4] = x * y * omc - z * sin;
        result.elements[1 + 1 * 4] = cos + y * y * omc;
        result.elements[1 + 2 * 4] = z * y * omc + x * sin;

        result.elements[2 + 0 * 4] = x * z * omc + y * sin;
        result.elements[2 + 1 * 4] = y * z * omc - x * sin;
        result.elements[2 + 2 * 4] = cos + z * z * omc;

        return result;
    }

    public static Matrix4 perspective(float fov, float aspect, float near, float far) 
    {
        Matrix4 result = new Matrix4();

        float tanFOV = (float) Math.tan(Math.toRadians(fov / 2.0f));
        float range = near - far;

        result.elements[0 + 0 * 4] = 1.0f / (aspect * tanFOV);
        result.elements[1 + 1 * 4] = 1.0f / (tanFOV);
        result.elements[2 + 2 * 4] = (-near - far) / range;
        result.elements[2 + 3 * 4] = 1.0f;
        result.elements[3 + 2 * 4] = 2 * far * near / range;

        return result;
    }

    public static Matrix4 orthographic(float left, float right, float bottom, float top, float near, float far) 
    {
        Matrix4 result = identity();

        result.elements[0 + 0 * 4] = 2f / (right - left);
        result.elements[1 + 1 * 4] = 2f / (top - bottom);
        result.elements[2 + 2 * 4] = -2f / (far - near);

        result.elements[3 + 0 * 4] = -(right + left) / (right - left);
        result.elements[3 + 1 * 4] = -(top + bottom) / (top - bottom);
        result.elements[3 + 2 * 4] = -(far + near) / (far - near);

        return result;
    }

    public float[] toArray() 
    {
        return elements;
    }

    @Override
    public String toString() 
    {
        StringBuilder b = new StringBuilder();
        for (int row = 0; row < 4; row++) 
        {
            b.append("[ ");
            for (int col = 0; col < 4; col++) 
            {
                b.append(elements[col + row * 4]).append(" ");
            }
            b.append("]\n");
        }
        return b.toString();
    }
}
