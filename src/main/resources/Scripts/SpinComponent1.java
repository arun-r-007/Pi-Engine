package org.PiEngine.Component;

import org.PiEngine.Core.*;
import org.PiEngine.Math.*;

public class SpinComponent1 extends Component
{
    public float speed = 100.0f;

    @Override
    public void update()
    {
        Vector rotation = gameObject.transform.getLocalRotation();
        rotation.z += speed * Time.deltaTime;
        gameObject.transform.setLocalRotation(rotation);
    }
}
