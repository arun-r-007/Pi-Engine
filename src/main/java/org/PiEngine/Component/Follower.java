package org.PiEngine.Component;


import org.PiEngine.GameObjects.*;
import org.PiEngine.Math.*;
import org.PiEngine.Core.*;

public class Follower extends Component
{
    public GameObject Target;
    public float speed = 5.0f;

    @Override
    public void update() 
    {
        Vector newPos = Vector.lerp(transform.getLocalPosition(), Target.transform.getLocalPosition(), speed*Time.deltaTime);
        transform.setLocalPosition(newPos);

    }

}
