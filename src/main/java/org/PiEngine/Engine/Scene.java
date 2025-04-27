
package org.PiEngine.Engine;

import org.PiEngine.Core.Camera;
import org.PiEngine.GameObjects.*;

public class Scene
{
    private String name;
    private int id;

    
    private boolean isLoaded;
    private boolean isActive;
    private boolean isPaused;

    
    private GameObject root;

    private Camera SceneCamera;

    public Scene(String name, int id)
    {
        this.name = name;
        this.id = id;
        this.root = new GameObject("Root"); // assuming your GameObject has a name constructor
        this.isLoaded = false;
        this.isActive = false;
        this.isPaused = false;
        // Camera Scenecamera = new Camera();
    }
}
