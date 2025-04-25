package org.PiEngine.Component;

import org.PiEngine.Core.*;
import org.PiEngine.Math.*;
import static org.lwjgl.glfw.GLFW.*;


public class Movemet extends Component
{
    public Float speed;
    public Boolean bools;
    public Vector vec;
    public Integer inte;
    public String str ;

    @Override
    public void start()
    {
        speed = 0.0f;
    }

    @Override
    public void update() 
    {
        
        int x = (Input.isKeyDown(GLFW_KEY_D)?1:(Input.isKeyDown(GLFW_KEY_A)?-1:0));
        int y = (Input.isKeyDown(GLFW_KEY_W)?1:(Input.isKeyDown(GLFW_KEY_S)?-1:0));

        Vector input = new Vector(x, y, 0);
        input.normalize();

        transform.setLocalPosition(transform.getLocalPosition().add(input.scale(Time.deltaTime * speed)));
    }
}
