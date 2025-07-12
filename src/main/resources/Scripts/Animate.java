package Scripts;

import org.PiEngine.Core.Time;
import org.PiEngine.Render.Texture;
import org.PiEngine.Component.Component;
import org.PiEngine.Component.RendererComponent;

public class Animate extends Component
{
    public RendererComponent reference;
    public Texture frameOne;
    public Texture frameTwo;

    public float FRAME_DURATION = 0.5f; 

    @Override
    public void start()
    {
        reference = gameObject.getComponent(RendererComponent.class);
    }

    @Override
    public void update()
    {
        if (reference == null || frameOne == null || frameTwo == null)
            return;

        int frameIndex = (int) (Time.Time / FRAME_DURATION) % 2;
        reference.texture = (frameIndex == 0) ? frameOne : frameTwo;
    }
}
