package org.PiEngine.Component;

import org.PiEngine.Core.*;
import org.PiEngine.Core.*;
import org.PiEngine.Core.*;
import org.PiEngine.Math.*;
import org.PiEngine.Component.*;

public class MoveLeftRightComponent extends Component
{

    @Override
    public void update()
    {
        Vector pos = new Vector(gameObject.transform.getWorldPosition());
        pos.x += Time.deltaTime;
        gameObject.transform.setWorldPosition(pos);
    }

}
