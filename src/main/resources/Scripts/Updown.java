package Scripts;

import org.PiEngine.Core.*;
import org.PiEngine.Math.*;
import org.PiEngine.Render.Texture;
import org.PiEngine.Component.*;

public class Updown extends Component
{
    public Float speed;
    public Texture Test;
    public CameraComponent ref;

    @Override
    public void update()
    {
        if (speed == null) return;
        Vector pos = new Vector(gameObject.transform.getWorldPosition());
        pos.x += Time.deltaTime * speed;
        gameObject.transform.setWorldPosition(pos);
    }
}