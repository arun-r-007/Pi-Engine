package org.PiEngine.Component;

import org.PiEngine.Core.*;
import org.PiEngine.Math.Vector;

/**
 * CameraComponent attaches a Camera to a GameObject and synchronizes its transform.
 */
public class CameraComponent extends Component
{

    public Float Near = 0.01f;
    public Float Far = 100.0f;

    private Camera camera;

    public CameraComponent() {
        this.camera = new Camera(1, Near, Far);
        //camera.setOrthographic( 8*-2, 8*2, -2 *4.5f, 2*4.5f, 1.0f, 100f);
        camera.setPerspective(70, (float)1280/720, Near, Far);
        camera.updateProjectionMatrix();
        camera.updateViewMatrix();
        //camera.setRenderLayerMask(LayerManager.getLayerBit(LayerManager.getLayerName(1)));


    }

    /**
     * Returns the Camera instance managed by this component.
     * @return The Camera instance
     */
    public Camera getCamera() {
        return camera;
    }

    /**
     * Called once when the component is first added to a GameObject.
     * Sets the camera position to the GameObject's world position.
     */
    @Override
    public void start() {
        camera.setPosition(gameObject.transform.getWorldPosition());
    }

    /**
     * Called every frame. Updates the camera's position and rotation to match the GameObject's transform.
     */
    @Override
    public void update() {
        Vector pos = new Vector(gameObject.transform.getWorldPosition());
        camera.setPosition(pos);
        Vector rot = new Vector(gameObject.transform.getWorldRotation());
        camera.setRotation(rot);
        // camera.updateViewMatrix();
    }
}
