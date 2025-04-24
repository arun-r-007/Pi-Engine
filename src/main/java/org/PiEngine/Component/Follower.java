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
    public void start()
    {
    }

    @Override
    public void update() 
    {
        if (Target == null) return;
        if (Vector.Distance(Target.transform.getWorldPosition(), transform.getWorldPosition()) > MinumDist)
        {
            Vector newPos = Vector.lerp(transform.getWorldPosition(), Target.transform.getWorldPosition(), speed*Time.deltaTime);
            transform.setWorldPosition(newPos);
        }

    }

}
