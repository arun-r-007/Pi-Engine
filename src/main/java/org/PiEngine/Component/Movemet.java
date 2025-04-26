package org.PiEngine.Component;

import org.PiEngine.Core.*;
import org.PiEngine.Math.*;

import static org.lwjgl.glfw.GLFW.*;


public class Movemet extends Component
{
    public Float speed;

    @Override
    public void start()
    {
        speed = 10.0f;
    }

    @Override
    public void update() 
    {
        int x = (Input.isKeyDown(GLFW_KEY_D)?1:(Input.isKeyDown(GLFW_KEY_A)?-1:0));
        int y = (Input.isKeyDown(GLFW_KEY_W)?1:(Input.isKeyDown(GLFW_KEY_S)?-1:0));

        Vector input = new Vector(x, y, 0);
        input.normalize();
        transform.setWorldPosition(transform.getWorldPosition().add(input.scale(Time.deltaTime * speed)));
    }
}
