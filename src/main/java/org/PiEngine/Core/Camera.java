package org.PiEngine.Core;

import org.PiEngine.Math.Vector;
import org.PiEngine.Render.Shader;
import org.PiEngine.Math.Matrix4;

import static org.lwjgl.opengl.GL30.*;

/**
 * The Camera class handles view and projection matrix generation
 * for a 3D scene using position, rotation, and projection parameters.
 * Supports both perspective and orthographic projections, and layer-based rendering.
 */
public class Camera
{
    /** Position of the camera in world space */
    private Vector position;
    /** Rotation of the camera (pitch, yaw, roll) */
    private Vector rotation;
    /** View matrix (world-to-camera transformation) */
    private Matrix4 viewMatrix;
    /** Projection matrix (camera-to-clip transformation) */
    private Matrix4 projectionMatrix;

    /** Field of view (in degrees) */
    private float fov;
    /** Aspect ratio (width / height) */
    private float aspectRatio;
    /** Near clipping plane */
    private float nearPlane;
    /** Far clipping plane */
    private float farPlane;

    private boolean isOrthographic = false;
    private float orthoLeft, orthoRight, orthoBottom, orthoTop;

    /**
     * Default constructor for Camera.
     */
    public Camera()
    {

    }
    
    /**
     * Constructs a new Camera with the specified projection parameters.
     * @param aspectRatio The aspect ratio (width/height)
     * @param nearPlane The near clipping plane
     * @param farPlane The far clipping plane
     */
    public Camera(float aspectRatio, float nearPlane, float farPlane)
    {
        this.position = new Vector(0, 0, 0);
        this.rotation = new Vector(0, 0, 0);
        this.aspectRatio = aspectRatio;
    }

    /**
     * Sets this camera to use orthographic projection.
     * @param left Left plane
     * @param right Right plane
     * @param bottom Bottom plane
     * @param top Top plane
     * @param near Near plane
     * @param far Far plane
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
     * @param fov Field of view in degrees
     * @param aspectRatio Aspect ratio (width/height)
     * @param near Near plane
     * @param far Far plane
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
     * @return The view matrix
     */
    public Matrix4 getViewMatrix()
    {
        return viewMatrix;
    }

    /**
     * Returns the projection matrix.
     * @return The projection matrix
     */
    public Matrix4 getProjectionMatrix()
    {
        return projectionMatrix;
    }

    /**
     * Returns the position of the camera.
     * @return The camera position
     */
    public Vector getPosition()
    {
        return position;
    }

    /**
     * Sets the camera position.
     * @param position The new camera position
     */
    public void setPosition(Vector position)
    {
        this.position = position;
        updateViewMatrix();
    }

    /**
     * Returns the rotation of the camera.
     * @return The camera rotation
     */
    public Vector getRotation()
    {
        return rotation;
    }

    /**
     * Sets the camera rotation.
     * @param rotation The new camera rotation
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

    /**
     * Applies this camera's projection and view matrix to the given shader.
     * @param shader The shader to apply camera matrices to
     */
    public void applyToShader(Shader shader)
    {

    }


    /** Add this field to filter what layers this camera will render */
    private int renderLayerMask = 0xFFFFFFFF; // Default: all layers visible

    /**
     * Returns the current render layer bitmask.
     * @return The render layer bitmask
     */
    public int getRenderLayerMask()
    {
        return renderLayerMask;
    }

    /**
     * Sets the render layer bitmask. Only GameObjects with matching layers will be rendered.
     * @param mask The new render layer bitmask
     */
    public void setRenderLayerMask(int mask)
    {
        this.renderLayerMask = mask;
    }

    /**
     * Enables rendering for the given layer (bitwise OR).
     * @param layerBit The layer bit to enable
     */
    public void addRenderLayer(int layerBit)
    {
        this.renderLayerMask |= layerBit;
    }

    /**
     * Disables rendering for the given layer (bitwise AND NOT).
     * @param layerBit The layer bit to disable
     */
    public void removeRenderLayer(int layerBit)
    {
        this.renderLayerMask &= ~layerBit;
    }

    /**
     * Checks whether this camera is set to render the given layer.
     * @param layerBit The layer bit to check
     * @return True if the camera renders the layer, false otherwise
     */
    public boolean canRenderLayer(int layerBit)
    {
        return (renderLayerMask & layerBit) != 0;
    }

}
