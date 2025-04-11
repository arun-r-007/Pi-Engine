package org.PiEngine.Component;


import org.PiEngine.GameObjects.*;
import org.PiEngine.Math.*;
import org.PiEngine.Core.*;

public class Follower extends Component
{
    public GameObject Target;
    public float speed = 5.0f;
    public float MinumDist = 1.25f;

    @Override
    public void update() 
    {
        if (Vector.Distance(Target.transform.getWorldPosition(), transform.getWorldPosition()) > MinumDist)
        {
            Vector newPos = Vector.lerp(transform.getLocalPosition(), Target.transform.getLocalPosition(), speed*Time.deltaTime);
            transform.setLocalPosition(newPos);
        }

    }

}
