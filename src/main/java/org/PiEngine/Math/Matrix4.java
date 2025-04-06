package org.PiEngine.Math;

import java.nio.FloatBuffer;

import org.lwjgl.BufferUtils;

/**
 * Represents a 4x4 matrix used for 3D transformations (translation, rotation, scale, projection, etc).
 * Stored in column-major order to match OpenGL expectations.
 */
public class Matrix4 
{
    public float[] elements = new float[16]; // 4x4 matrix stored in column-major order

    /** Constructor creates an identity matrix by default */
    public Matrix4()
    {
        this.elements = new float[16]; // Just allocate memory, don't call identity
    }


    /**
     * Creates and returns an identity matrix.
     * Diagonal is set to 1, rest is 0.
     */
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

    /**
     * Multiplies two matrices and returns the result.
     * Used for chaining transformations.
     */
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

    /** Instance version of matrix multiplication. Returns this * other. */
    public Matrix4 multiply(Matrix4 other) 
    {
        return Matrix4.multiply(this, other);
    }

    /** Copies this matrix into a new matrix instance. */
    public Matrix4 copy() 
    {
        Matrix4 result = new Matrix4();
        System.arraycopy(this.elements, 0, result.elements, 0, 16);
        return result;
    }

    /**
     * Transposes this matrix.
     * Converts rows to columns (useful for some OpenGL shader setups).
     */
    public Matrix4 transpose() 
    {
        Matrix4 result = new Matrix4();
        for (int row = 0; row < 4; row++) 
        {
            for (int col = 0; col < 4; col++) 
            {
                result.elements[col + row * 4] = this.elements[row + col * 4];
            }
        }
        return result;
    }

    /**
     * Creates a translation matrix based on a vector.
     */
    public static Matrix4 translate(Vector vector) 
    {
        Matrix4 result = identity();
        result.elements[3 + 0 * 4] = (float) vector.x;
        result.elements[3 + 1 * 4] = (float) vector.y;
        result.elements[3 + 2 * 4] = (float) vector.z;
        return result;
    }

    /** Overload: Translation from float components */
    public static Matrix4 translate(float x, float y, float z) 
    {
        return translate(new Vector(x, y, z));
    }

    /**
     * Creates a scale matrix from a vector.
     */
    public static Matrix4 scale(Vector vector) 
    {
        Matrix4 result = identity();
        result.elements[0 + 0 * 4] = (float) vector.x;
        result.elements[1 + 1 * 4] = (float) vector.y;
        result.elements[2 + 2 * 4] = (float) vector.z;
        return result;
    }

    /** Overload: Scale from float components */
    public static Matrix4 scale(float x, float y, float z) 
    {
        return scale(new Vector(x, y, z));
    }

    /**
     * Creates a rotation matrix for a given angle and axis (normalized assumed).
     * Uses Rodrigues' rotation formula.
     */
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

    /**
     * Creates a perspective projection matrix.
     * Used for 3D rendering with depth.
     */
    public static Matrix4 perspective(float fov, float aspect, float near, float far) 
    {
        Matrix4 result = new Matrix4();

        float tanFOV = (float) Math.tan(Math.toRadians(fov / 2.0f));
        float range = far - near;

        result.elements[0 + 0 * 4] = 1.0f / (aspect * tanFOV);
        result.elements[1 + 1 * 4] = 1.0f / tanFOV;
        result.elements[2 + 2 * 4] = -(far + near) / range;
        result.elements[2 + 3 * 4] = -1.0f;
        result.elements[3 + 2 * 4] = -(2 * far * near) / range;
        result.elements[3 + 3 * 4] = 0.0f;

        return result;
    }


    /**
     * Creates an orthographic projection matrix.
     * Used for 2D rendering or UI.
     */
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

    /**
     * Multiplies this matrix with a vector (assuming w=1).
     * Performs a perspective divide if w ≠ 1.
     */
    public Vector multiply(Vector v) 
    {
        float x = v.x, y = v.y, z = v.z, w = 1.0f;

        float nx = elements[0] * x + elements[4] * y + elements[8]  * z + elements[12] * w;
        float ny = elements[1] * x + elements[5] * y + elements[9]  * z + elements[13] * w;
        float nz = elements[2] * x + elements[6] * y + elements[10] * z + elements[14] * w;
        float nw = elements[3] * x + elements[7] * y + elements[11] * z + elements[15] * w;

        if (nw != 0.0f) {
            nx /= nw;
            ny /= nw;
            nz /= nw;
        }

        return new Vector(nx, ny, nz);
    }

    /**
     * Returns the underlying array of elements (useful for OpenGL uniform uploads).
     */
    public float[] toArray() 
    {
        return elements;
    }

    /**
     * Returns a readable string representation of the matrix.
     */
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

    /**
     * This buffer can then be passed to OpenGL (e.g., glLoadMatrixf).
     * @return FloatBuffer containing the matrix elements in OpenGL-compatible format.
     */
    public FloatBuffer toFloatBuffer()
    {
        FloatBuffer buffer = BufferUtils.createFloatBuffer(16); // Allocate space for 4x4 matrix

        // Fill buffer in column-major order (OpenGL expects this)
        for (int col = 0; col < 4; col++)
        {
            for (int row = 0; row < 4; row++)
            {
                buffer.put(elements[row * 4 + col]); // Accessing 1D array in column-major layout
            }
        }

        buffer.flip(); // Prepare buffer for reading by OpenGL
        return buffer;
    }
    

}
