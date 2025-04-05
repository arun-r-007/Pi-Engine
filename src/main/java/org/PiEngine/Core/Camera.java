package org.PiEngine.Core;
import org.PiEngine.Math.Vector;
import org.PiEngine.Math.Matrix4;


public class Camera 
{
    private Vector position;
    private Vector rotation;
    private Matrix4 viewMatrix;
    private Matrix4 projectionMatrix;

    private float fov;
    private float aspectRatio;
    private float nearPlane;
    private float farPlane;

}

