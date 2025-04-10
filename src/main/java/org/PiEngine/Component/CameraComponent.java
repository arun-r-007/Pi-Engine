package org.PiEngine.Component;

import org.PiEngine.Core.*;
import org.PiEngine.Math.Vector;

public class CameraComponent extends Component
{

    public float Near = 0.01f;
    public float Far = 100.0f;

    private Camera camera;

    public CameraComponent() {
        this.camera = new Camera(1, Near, Far);
        camera.setOrthographic( 8*-2, 8*2, -2 *4.5f, 2*4.5f, 1.0f, 100f);
        camera.updateProjectionMatrix();
        camera.updateViewMatrix();
        camera.setRenderLayerMask(LayerManager.getLayerBit(LayerManager.getLayerName(1)));


    }

    public Camera getCamera() {
        return camera;
    }

    @Override
    public void start() {
        camera.setPosition(gameObject.transform.getWorldPosition());
    }

    @Override
    public void update() {
        Vector pos = new Vector(gameObject.transform.getWorldPosition());
        camera.setPosition(pos);
        camera.updateViewMatrix();
    }
}
