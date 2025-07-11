package Scripts;

import org.PiEngine.Core.*;
import org.PiEngine.Math.*;
import org.PiEngine.Component.*;

public class Spin3DComponent extends Component 
{
   public Float speedX = 100.0F;
   public Float speedY = 100.0F;
   public Float speedZ = 100.0F;

   public void update() 
   {
      Vector rotation = transform.getLocalRotation();

      rotation.x += speedX * Time.deltaTime;
      rotation.y += speedY * Time.deltaTime;
      rotation.z += speedZ * Time.deltaTime;

      this.transform.setLocalRotation(rotation);
   }
}
