package org.PiEngine.Component;

import org.PiEngine.Core.*;
import org.PiEngine.Math.*;

public class SpinComponent extends Component
{
    public float speed = 100.0f;
    public float dir = 1.0f;

    @Override
    public void update()
    {
        Vector rotation = transform.getLocalRotation();
        if(dir == 1.0) rotation.z += speed * Time.deltaTime;
        else rotation.y += speed * Time.deltaTime;
        transform.setLocalRotation(rotation);
        // System.out.println(transform.getWorldMatrix());
    }
}
