package org.PiEngine.Component;

import org.PiEngine.Core.*;
import org.PiEngine.Math.*;

public class SpinComponent extends Component
{
    public float speed = 100.0f;

    @Override
    public void update()
    {
        Vector rotation = transform.getLocalRotation();
        rotation.z += speed * Time.deltaTime;
        transform.setLocalRotation(rotation);
        // System.out.println(transform.getWorldMatrix());
    }
}
