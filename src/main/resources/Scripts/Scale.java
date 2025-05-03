package Scripts;

import org.PiEngine.Core.*;
import org.PiEngine.Math.*;
import org.PiEngine.Render.Texture;
import org.PiEngine.Component.*;

public class Scale extends Component
{
    public Float Speed = 2.0f;
    public Texture temp;
    @Override
    public void update()
    {
        float t = Time.Time;
        t = t%5;
        transform.setLocalScale(Vector.lerp(transform.getLocalScale(), new Vector(t, t, t), Speed * Time.deltaTime));   
    }
}