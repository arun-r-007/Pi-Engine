package org.PiEngine.Core;

import org.PiEngine.Math.Vector;
import org.PiEngine.Math.Matrix4;

import static org.lwjgl.opengl.GL11.*;

/**
 * The Camera class handles view and projection matrix generation
 * for a 3D scene using position, rotation, and projection parameters.
 */
public class Camera
{
    private Vector position;         // Position of the camera in world space
    private Vector rotation;         // Rotation of the camera (pitch, yaw, roll)
    private Matrix4 viewMatrix;      // View matrix (world-to-camera transformation)
    private Matrix4 projectionMatrix; // Projection matrix (camera-to-clip transformation)

    private float fov;               // Field of view (in degrees)
    private float aspectRatio;       // Aspect ratio (width / height)
    private float nearPlane;         // Near clipping plane
    private float farPlane;          // Far clipping plane

    private boolean isOrthographic = false;
    private float orthoLeft, orthoRight, orthoBottom, orthoTop;

    /**
     * Constructs a new Camera with the specified projection parameters.
     */
    public Camera(float aspectRatio, float nearPlane, float farPlane)
    {
        this.position = new Vector(0, 0, 0);
        this.rotation = new Vector(0, 0, 0);
        this.aspectRatio = aspectRatio;
    }

    /**
     * Sets this camera to use orthographic projection.
     */
    public void setOrthographic(float left, float right, float bottom, float top, float near, float far)
    {
        isOrthographic = true;
        orthoLeft = left;
        orthoRight = right;
        orthoBottom = bottom;
        orthoTop = top;
        nearPlane = near;
        farPlane = far;
        updateProjectionMatrix();
    }

    /**
     * Sets this camera to use perspective projection.
     */
    public void setPerspective(float fov, float aspectRatio, float near, float far)
    {
        isOrthographic = false;
        this.fov = fov;
        this.aspectRatio = aspectRatio;
        this.nearPlane = near;
        this.farPlane = far;
        updateProjectionMatrix();
    }

    /**
     * Updates the projection matrix based on current parameters.
     */
    public void updateProjectionMatrix()
    {
        if (isOrthographic)
        {
            this.projectionMatrix = Matrix4.orthographic(orthoLeft, orthoRight, orthoBottom, orthoTop, nearPlane, farPlane);
        }
        else
        {
            this.projectionMatrix = Matrix4.perspective(fov, aspectRatio, nearPlane, farPlane);
        }
    }

    /**
     * Updates the view matrix based on the camera's current position and rotation.
     */
    public void updateViewMatrix() 
    {
        Matrix4 rotationMatrix = Matrix4.identity()
            .multiply(Matrix4.rotate(-rotation.z, new Vector(0, 0, 1)))
            .multiply(Matrix4.rotate(-rotation.x, new Vector(1, 0, 0))) 
            .multiply(Matrix4.rotate(-rotation.y, new Vector(0, 1, 0)));
    
        Matrix4 translationMatrix = Matrix4.translate(-position.x, -position.y, -position.z);
    
        this.viewMatrix = rotationMatrix.multiply(translationMatrix);
    }
    
    
    /**
     * Returns the view matrix.
     */
    public Matrix4 getViewMatrix()
    {
        return viewMatrix;
    }

    /**
     * Returns the projection matrix.
     */
    public Matrix4 getProjectionMatrix()
    {
        return projectionMatrix;
    }

    /**
     * Returns the position of the camera.
     */
    public Vector getPosition()
    {
        return position;
    }

    /**
     * Sets the camera position.
     */
    public void setPosition(Vector position)
    {
        this.position = position;
        updateViewMatrix();
    }

    /**
     * Returns the rotation of the camera.
     */
    public Vector getRotation()
    {
        return rotation;
    }

    /**
     * Sets the camera rotation.
     */
    public void setRotation(Vector rotation)
    {
        this.rotation = rotation;
        updateViewMatrix();
    }

    /**
     * Applies this camera's projection and view matrix to the OpenGL pipeline.
     */
    public void applyToOpenGL()
    {
        // Projection matrix
        glMatrixMode(GL_PROJECTION);
        glLoadMatrixf(projectionMatrix.toFloatBuffer());
        
        // View matrix (aka modelview in legacy OpenGL)
        glMatrixMode(GL_MODELVIEW);
        glLoadMatrixf(viewMatrix.toFloatBuffer());
    }
}
