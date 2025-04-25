package org.PiEngine.Component;


import org.PiEngine.GameObjects.*;
import org.PiEngine.Math.*;
import org.PiEngine.Core.*;

public class Follower extends Component
{
    public GameObject Target;
    public Float speed = 5.0f;
    public Float MinumDist = 1.25f;
    public Float zindex = 0.0f;


    @Override
    public void start()
    {
        Target = new GameObject(null);
    }

    @Override
    public void update() 
    {
        if (Target == null) return;
        if (Vector.Distance(Target.transform.getWorldPosition(), transform.getWorldPosition()) > MinumDist)
        {
            Vector newPos = Vector.lerp(transform.getWorldPosition(), Target.transform.getWorldPosition(), speed*Time.deltaTime);
            newPos.z = zindex;
            transform.setWorldPosition(newPos);
        }

    }

}
