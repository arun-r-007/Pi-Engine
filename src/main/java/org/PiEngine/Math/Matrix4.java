package org.PiEngine.Math;
import java.nio.FloatBuffer;
import org.lwjgl.BufferUtils;

/**
 * Represents a 4x4 matrix used for 3D transformations (translation, rotation, scale, projection, etc).
 * This class stores matrix data in column-major order to match OpenGL expectations and provides
 * comprehensive functionality for matrix operations and transformations in 3D space.
 */
public class Matrix4
{
    /** The matrix elements stored in column-major order */
    public float[] elements = new float[16];

    /**
     * Default constructor creates an uninitialized matrix.
     * Use identity() to create an identity matrix.
     */
    public Matrix4()
    {
        this.elements = new float[16];
    }

    /**
     * Creates and returns an identity matrix.
     * An identity matrix has 1's on the diagonal and 0's elsewhere.
     * @return New identity matrix
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
     * Matrix multiplication is not commutative: a * b != b * a
     * @param a First matrix (left operand)
     * @param b Second matrix (right operand)
     * @return Result of a * b
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

    /**
     * Instance version of matrix multiplication.
     * @param other Matrix to multiply with
     * @return Result of this * other
     */
    public Matrix4 multiply(Matrix4 other) 
    {
        return Matrix4.multiply(this, other);
    }

    /**
     * Creates a deep copy of this matrix.
     * @return New matrix with same values
     */
    public Matrix4 copy() 
    {
        Matrix4 result = new Matrix4();
        System.arraycopy(this.elements, 0, result.elements, 0, 16);
        return result;
    }

    /**
     * Creates a transposed version of this matrix.
     * Rows become columns and columns become rows.
     * @return New transposed matrix
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
     * Creates a translation matrix from a vector.
     * @param vector Translation amounts for x, y, z
     * @return Translation matrix
     */
    public static Matrix4 translate(Vector vector) 
    {
        Matrix4 result = identity();
        result.elements[3 + 0 * 4] = (float) vector.x;
        result.elements[3 + 1 * 4] = (float) vector.y;
        result.elements[3 + 2 * 4] = (float) vector.z;
        return result;
    }

    /**
     * Creates a translation matrix from components.
     * @param x X translation
     * @param y Y translation
     * @param z Z translation
     * @return Translation matrix
     */
    public static Matrix4 translate(float x, float y, float z) 
    {
        return translate(new Vector(x, y, z));
    }

    /**
     * Creates a scale matrix from a vector.
     * @param vector Scale factors for x, y, z
     * @return Scale matrix
     */
    public static Matrix4 scale(Vector vector) 
    {
        Matrix4 result = identity();
        result.elements[0 + 0 * 4] = (float) vector.x;
        result.elements[1 + 1 * 4] = (float) vector.y;
        result.elements[2 + 2 * 4] = (float) vector.z;
        return result;
    }

    /**
     * Creates a scale matrix from components.
     * @param x X scale factor
     * @param y Y scale factor
     * @param z Z scale factor
     * @return Scale matrix
     */
    public static Matrix4 scale(float x, float y, float z) 
    {
        return scale(new Vector(x, y, z));
    }

    /**
     * Creates a rotation matrix for a given angle and axis.
     * Uses Rodrigues' rotation formula.
     * @param angleDeg Rotation angle in degrees
     * @param axis Rotation axis (should be normalized)
     * @return Rotation matrix
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
     * @param fov Field of view in degrees
     * @param aspect Aspect ratio (width/height)
     * @param near Near clipping plane distance
     * @param far Far clipping plane distance
     * @return Perspective projection matrix
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
     * @param left Left plane coordinate
     * @param right Right plane coordinate
     * @param bottom Bottom plane coordinate
     * @param top Top plane coordinate
     * @param near Near plane distance
     * @param far Far plane distance
     * @return Orthographic projection matrix
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
     * @param v Vector to transform
     * @return Transformed vector
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
     * Returns the underlying array of elements.
     * Useful for OpenGL uniform uploads.
     * @return Array of matrix elements in column-major order
     */
    public float[] toArray() 
    {
        return elements;
    }

    /**
     * Extracts and returns the translation component from this matrix.
     * @return Vector representing the position in 3D space
     */
    public Vector getTranslation()
    {
        return new Vector
        (
            elements[3 + 0 * 4],
            elements[3 + 1 * 4],
            elements[3 + 2 * 4]
        );
    }

    /**
     * Returns a readable string representation of the matrix.
     * @return String showing matrix elements in row-major format
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
     * Creates an OpenGL-compatible FloatBuffer containing matrix elements.
     * @return FloatBuffer ready for use with OpenGL
     */
    public FloatBuffer toFloatBuffer()
    {
        FloatBuffer buffer = BufferUtils.createFloatBuffer(16);

        for (int col = 0; col < 4; col++)
        {
            for (int row = 0; row < 4; row++)
            {
                buffer.put(elements[row * 4 + col]);
            }
        }

        buffer.flip();
        return buffer;
    }

    /**
     * Calculates the inverse of a matrix.
     * Returns identity matrix if matrix is not invertible.
     * @param m Matrix to invert
     * @return Inverted matrix
     */
    public static Matrix4 invert(Matrix4 m) 
    {
        float[] inv = new float[16];
        float[] mat = m.elements;

        inv[0] = mat[5]  * mat[10] * mat[15] - 
                mat[5]  * mat[11] * mat[14] - 
                mat[9]  * mat[6]  * mat[15] + 
                mat[9]  * mat[7]  * mat[14] +
                mat[13] * mat[6]  * mat[11] - 
                mat[13] * mat[7]  * mat[10];

        inv[4] = -mat[4] * mat[10] * mat[15] + 
                mat[4]  * mat[11] * mat[14] + 
                mat[8]  * mat[6]  * mat[15] - 
                mat[8]  * mat[7]  * mat[14] - 
                mat[12] * mat[6]  * mat[11] + 
                mat[12] * mat[7]  * mat[10];

        inv[8] = mat[4]  * mat[9]  * mat[15] - 
                mat[4]  * mat[11] * mat[13] - 
                mat[8]  * mat[5]  * mat[15] + 
                mat[8]  * mat[7]  * mat[13] + 
                mat[12] * mat[5]  * mat[11] - 
                mat[12] * mat[7]  * mat[9];

        inv[12] = -mat[4] * mat[9]  * mat[14] + 
                mat[4]  * mat[10] * mat[13] +
                mat[8]  * mat[5]  * mat[14] - 
                mat[8]  * mat[6]  * mat[13] - 
                mat[12] * mat[5]  * mat[10] + 
                mat[12] * mat[6]  * mat[9];

        inv[1] = -mat[1] * mat[10] * mat[15] + 
                mat[1]  * mat[11] * mat[14] + 
                mat[9]  * mat[2]  * mat[15] - 
                mat[9]  * mat[3]  * mat[14] - 
                mat[13] * mat[2]  * mat[11] + 
                mat[13] * mat[3]  * mat[10];

        inv[5] = mat[0]  * mat[10] * mat[15] - 
                mat[0]  * mat[11] * mat[14] - 
                mat[8]  * mat[2]  * mat[15] + 
                mat[8]  * mat[3]  * mat[14] + 
                mat[12] * mat[2]  * mat[11] - 
                mat[12] * mat[3]  * mat[10];

        inv[9] = -mat[0] * mat[9]  * mat[15] + 
                mat[0]  * mat[11] * mat[13] + 
                mat[8]  * mat[1]  * mat[15] - 
                mat[8]  * mat[3]  * mat[13] - 
                mat[12] * mat[1]  * mat[11] + 
                mat[12] * mat[3]  * mat[9];

        inv[13] = mat[0]  * mat[9]  * mat[14] - 
                mat[0]  * mat[10] * mat[13] - 
                mat[8]  * mat[1]  * mat[14] + 
                mat[8]  * mat[2]  * mat[13] + 
                mat[12] * mat[1]  * mat[10] - 
                mat[12] * mat[2]  * mat[9];

        inv[2] = mat[1]  * mat[6]  * mat[15] - 
                mat[1]  * mat[7]  * mat[14] - 
                mat[5]  * mat[2]  * mat[15] + 
                mat[5]  * mat[3]  * mat[14] + 
                mat[13] * mat[2]  * mat[7] - 
                mat[13] * mat[3]  * mat[6];

        inv[6] = -mat[0] * mat[6]  * mat[15] + 
                mat[0]  * mat[7]  * mat[14] + 
                mat[4]  * mat[2]  * mat[15] - 
                mat[4]  * mat[3]  * mat[14] - 
                mat[12] * mat[2]  * mat[7] + 
                mat[12] * mat[3]  * mat[6];

        inv[10] = mat[0]  * mat[5]  * mat[15] - 
                mat[0]  * mat[7]  * mat[13] - 
                mat[4]  * mat[1]  * mat[15] + 
                mat[4]  * mat[3]  * mat[13] + 
                mat[12] * mat[1]  * mat[7] - 
                mat[12] * mat[3]  * mat[5];

        inv[14] = -mat[0] * mat[5]  * mat[14] + 
                mat[0]  * mat[6]  * mat[13] + 
                mat[4]  * mat[1]  * mat[14] - 
                mat[4]  * mat[2]  * mat[13] - 
                mat[12] * mat[1]  * mat[6] + 
                mat[12] * mat[2]  * mat[5];

        inv[3] = -mat[1] * mat[6]  * mat[11] + 
                mat[1]  * mat[7]  * mat[10] + 
                mat[5]  * mat[2]  * mat[11] - 
                mat[5]  * mat[3]  * mat[10] - 
                mat[9]  * mat[2]  * mat[7] + 
                mat[9]  * mat[3]  * mat[6];

        inv[7] = mat[0]  * mat[6]  * mat[11] - 
                mat[0]  * mat[7]  * mat[10] - 
                mat[4]  * mat[2]  * mat[11] + 
                mat[4]  * mat[3]  * mat[10] + 
                mat[8]  * mat[2]  * mat[7] - 
                mat[8]  * mat[3]  * mat[6];

        inv[11] = -mat[0] * mat[5]  * mat[11] + 
                mat[0]  * mat[7]  * mat[9] + 
                mat[4]  * mat[1]  * mat[11] - 
                mat[4]  * mat[3]  * mat[9] - 
                mat[8]  * mat[1]  * mat[7] + 
                mat[8]  * mat[3]  * mat[5];

        inv[15] = mat[0]  * mat[5]  * mat[10] - 
                mat[0]  * mat[6]  * mat[9] - 
                mat[4]  * mat[1]  * mat[10] + 
                mat[4]  * mat[2]  * mat[9] + 
                mat[8]  * mat[1]  * mat[6] - 
                mat[8]  * mat[2]  * mat[5];

        float det = mat[0] * inv[0] + mat[1] * inv[4] + mat[2] * inv[8] + mat[3] * inv[12];
        if (det == 0) return identity();

        det = 1.0f / det;

        Matrix4 result = new Matrix4();
        for (int i = 0; i < 16; i++)
            result.elements[i] = inv[i] * det;

        return result;
    }
    
    
}
