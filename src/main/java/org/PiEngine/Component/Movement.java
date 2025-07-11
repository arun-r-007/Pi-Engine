package org.PiEngine.Component;

import org.PiEngine.Core.Input;
import org.PiEngine.Core.Time;
import org.PiEngine.Math.Vector;
import org.PiEngine.Render.Texture;

import static org.lwjgl.glfw.GLFW.*;

public class Movement extends Component
{
    public float speed = 10.0f;
    public float jumpForce = 15.0f;
    public float gravity = -30.0f;
    public float velocityY = 0.0f;

    public RendererComponent Renderer;

    public Texture Run1;
    public Texture Run2;
    public Texture Idle;
    public Texture Jump;
    public Texture Fall;

    public boolean isGrounded = false;

    public float animTimer = 0f;
    public float animSpeed = 0.15f;
    public boolean useRun1 = true;

    @Override
    public void update()
    {
        int xInput = (Input.isKeyDown(GLFW_KEY_D) || Input.isKeyDown(GLFW_KEY_RIGHT)) ? 1 :
                     (Input.isKeyDown(GLFW_KEY_A) || Input.isKeyDown(GLFW_KEY_LEFT)) ? -1 : 0;

        Vector move = new Vector(xInput, 0, 0).scale(speed * Time.deltaTime);
        Vector position = transform.getWorldPosition();

        // Floor check
        if (position.y <= 0.0f)
        {
            position.y = 0.0f;
            velocityY = 0.0f;
            isGrounded = true;

            if (isGrounded && (Input.isKeyDown(GLFW_KEY_W) || Input.isKeyDown(GLFW_KEY_UP)))
            {
                velocityY = jumpForce;
                isGrounded = false;
            }
        }
        else
        {
            velocityY += gravity * Time.deltaTime;
            isGrounded = false;
        }

        move.y = velocityY * Time.deltaTime;
        transform.setWorldPosition(position.add(move));

        
        if (Renderer != null)
        {
            
            if (xInput != 0)
            {
                Renderer.FlipX = xInput < 0;
            }

            
            if (!isGrounded)
            {
                Renderer.texture = velocityY > 0 ? Jump : Fall;
            }
            else if (xInput != 0)
            {
                animTimer += Time.deltaTime;
                if (animTimer >= animSpeed)
                {
                    animTimer = 0f;
                    useRun1 = !useRun1;
                }
                Renderer.texture = useRun1 ? Run1 : Run2;
            }
            else
            {
                Renderer.texture = Idle;
            }
        }
    }
}
