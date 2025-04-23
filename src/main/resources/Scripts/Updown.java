package Scripts;

import org.PiEngine.Core.*;
import org.PiEngine.Math.*;
import org.PiEngine.Component.*;

public class Updown extends Component
{
    public float speed;
    @Override
    public void update()
    {
        Vector pos = new Vector(gameObject.transform.getWorldPosition());
        pos.x += Time.deltaTime * speed;
        gameObject.transform.setWorldPosition(pos);
    }
}