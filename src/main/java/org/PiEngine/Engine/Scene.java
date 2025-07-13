package org.PiEngine.Engine;

import org.PiEngine.Math.*;
import org.PiEngine.Core.*;
import org.PiEngine.GameObjects.*;


import java.io.Serializable;

import org.PiEngine.Main;
import org.PiEngine.Component.*;
import org.PiEngine.Editor.*;
import org.PiEngine.Render.*;
import org.PiEngine.Render.Passes.*;


public class Scene implements Serializable
{
    public String Name;
    private static Scene instance;

    private GameObject root;
    private Camera editorCamera;
    private GameObject GameCamera;
    
    private Renderer sceneRenderer;
    private Renderer gameRenderer;
    
    private SceneWindow editorSceneWindow;
    private Editor editor;
    private SceneWindow gameSceneWindow;

    private long windowHandle;

    Scene() {}

    /**
     * Returns the singleton instance of the Scene.
     * @return The Scene instance
     */
    public static Scene getInstance()
    {
        if (instance == null)
        {
            instance = new Scene();
            instance.Name = "Scene";
 
        }
        return instance;
    }

    /**
     * Initializes the scene, cameras, editor, and renderers.
     * @param window The window handle
     * @param width The window width
     * @param height The window height
     */
    public void init(long window, int width, int height)
    {
        this.windowHandle = window;

        // Setup Cameras
        editorCamera = new Camera((float) width / height, 0.01f, 100.0f);
        editorCamera.setPosition(new Vector(0, 0, 20.0f));
        editorCamera.setRotation(new Vector(0, 0, 0));
        editorCamera.setOrthographic(-16, 16, -9, 9, 1.0f, 100f);
        editorCamera.updateProjectionMatrix();
        editorCamera.updateViewMatrix();

        // Root GameObject
        root = new GameObject(Name);
        GameObject cameraObject = new GameObject("Main Camera");
        GameCamera = cameraObject;
        root.addChild(cameraObject);
        cameraObject.transform.setLocalPosition(new Vector(0, 0, 20));
        cameraObject.addComponent(new CameraComponent());
        
        // Setup Editor
        editor = Editor.getInstance();
        editor.addWindow(new DockingWindow());
        editor.addWindow(new LayerWindow());
        editor.addWindow(new HierarchyWindow());
        editor.addWindow(new PerfomanceWindow());
        editor.addWindow(new ConsoleWindow());
        editor.addWindow(new NavigationWindow());
        editor.queueAddWindow(new FileWindow());

        // Setup Renderers
        Shader DefaultShader = new Shader
        (
            Main.ResourceFolder + "Shaders/Camera/Default.vert",
            Main.ResourceFolder + "Shaders/Camera/Default.frag",
            null
        );

        Shader CRTShader = new Shader
        (
            Main.ResourceFolder + "Shaders/PostProcess/SCREEN.vert",
            Main.ResourceFolder + "Shaders/PostProcess/CRT.frag",
            null
        );

        Shader BloomShader = new Shader
        (
            Main.ResourceFolder + "Shaders/PostProcess/SCREEN.vert",
            Main.ResourceFolder + "Shaders/PostProcess/BLUR.frag",
            null
        );

        Shader FinalShader = new Shader
        (
            Main.ResourceFolder + "Shaders/PostProcess/SCREEN.vert",
            Main.ResourceFolder +"Shaders/PostProcess/FINAL.frag",
            null
        );

        // Scene Renderer
        sceneRenderer = new Renderer();
        GeometryPass sceneGP = new GeometryPass("SceneGeometry", DefaultShader, width, height);
        sceneRenderer.addPass(sceneGP);
        sceneRenderer.setFinalPass("SceneGeometry");

        // Game Renderer
        gameRenderer = new Renderer();
        GeometryPass gameGP = new GeometryPass("Geometry", DefaultShader, width, height);
        GeometryPass gameGP1 = new GeometryPass("Box", DefaultShader, width, height);
        gameGP1.setLayerMask(LayerManager.getLayerBit(LayerManager.getLayerName(30)));

        PostProcessingPass crtPP = new PostProcessingPass("CRT", CRTShader, width, height, 2);
        PostProcessingPass blurPP = new PostProcessingPass("BLUR", BloomShader, width, height, 1);
        PostProcessingPass finalPP = new PostProcessingPass("FINAL", FinalShader, width, height, 1);

        gameRenderer.addPass(gameGP);
        gameRenderer.addPass(gameGP1);
        gameRenderer.addPass(crtPP);
        gameRenderer.addPass(blurPP);
        gameRenderer.addPass(finalPP);

        gameRenderer.setFinalPass("FINAL");
        gameRenderer.connect("Geometry", "CRT", 0);
        gameRenderer.connect("Box", "BLUR", 0);
        // gameRenderer.connect("BLUR", "CRT", 1);
        gameRenderer.connect("CRT", "FINAL", 0);

        editor.addWindow(new RendererInspector(gameRenderer));
        editor.addWindow(new RenderGraphEditorWindow(gameRenderer));
        editor.addWindow(new InspectorWindow(false));


        // Scene Windows
        editorSceneWindow = new SceneWindow("Scene");
        editor.addWindow(editorSceneWindow);

        gameSceneWindow = new SceneWindow("Game");
        editor.addWindow(gameSceneWindow);

        Time.timeScale = 1.0f;
    }

    /**
     * Updates the editor camera and root game object.
     */
    public void update()
    {   
        editorCamera.updateViewMatrix();
        root.update();
    }

    /**
     * Renders the scene and game views.
     */
    public void render()
    {
        sceneRenderer.renderPipeline(editorCamera, root);
        editorSceneWindow.setFrameBuffer(sceneRenderer.getFinalFramebuffer());

        GameObject cameraObj = GameCamera;
        if (cameraObj != null)
        {
            CameraComponent gameCam = cameraObj.getComponent(CameraComponent.class);
            if (gameCam != null)
            {
                gameRenderer.renderPipeline(gameCam.getCamera(), root);
                gameSceneWindow.setFrameBuffer(gameRenderer.getFinalFramebuffer());
            }
        }
    }

    /**
     * Saves the scene to a JSON file.
     * @param Filename The file name to save to
     */
    public void Save(String Filename)
    {
        try 
        {
            SceneSerializerJSON.serialize(instance, Main.ResourceFolder + Filename);
        } 
        catch (Exception e) 
        {
            Console.error(e.toString());
        }
    }
    // {
    //     try 
    //     {
    //         SceneSerializerJSON.serialize(instance, Main.ResourceFolder+"Test.json");
    //     } 
    //     catch (Exception e) 
    //     {
    //         Console.error(e.toString());
    //     }
    // }

    /**
     * Loads the scene from a JSON file.
     * @param FileName The file name to load from
     */
    public void Load(String FileName)
    {
        InspectorWindow.inspectObject = null;
        try 
        {
            SceneDeserializerJSON.deserialize(Main.ResourceFolder+FileName);
            IDGenerator.resetIDCounter();
        } 
        catch (Exception e) 
        {
            e.printStackTrace();
        }
    }

    /**
     * Gets the root game object.
     * @return The root GameObject
     */
    public GameObject getRoot()
    {
        return root;
    }

    /**
     * Gets the editor camera.
     * @return The editor Camera
     */
    public Camera getEditorCamera()
    {
        return editorCamera;
    }

    /**
     * Gets the name of the scene.
     * @return The scene name
     */
    public String getName() {
        return Name;
    }

    /**
     * Sets the name of the scene.
     * @param name The new scene name
     */
    public void setName(String name) {
        Name = name;
    }

    /**
     * Sets the singleton instance of the Scene.
     * @param instance The Scene instance
     */
    public static void setInstance(Scene instance) {
        Scene.instance = instance;
    }

    /**
     * Sets the root game object.
     * @param root The root GameObject
     */
    public void setRoot(GameObject root) {
        this.root = root;
    }

    /**
     * Sets the editor camera.
     * @param editorCamera The editor Camera
     */
    public void setEditorCamera(Camera editorCamera) {
        this.editorCamera = editorCamera;
    }

    /**
     * Gets the game camera object.
     * @return The game camera GameObject
     */
    public GameObject getGameCamera() {
        return GameCamera;
    }

    /**
     * Sets the game camera object.
     * @param gameCamera The game camera GameObject
     */
    public void setGameCamera(GameObject gameCamera) {
        GameCamera = gameCamera;
    }

    /**
     * Gets the editor renderer.
     * @return The editor Renderer
     */
    public Renderer getSceneRenderer() {
        return sceneRenderer;
    }

    /**
     * Sets the editor renderer.
     * @param sceneRenderer The editor Renderer
     */
    public void setSceneRenderer(Renderer sceneRenderer) {
        this.sceneRenderer = sceneRenderer;
    }

    /**
     * Gets the game renderer.
     * @return The game Renderer
     */
    public Renderer getGameRenderer() {
        return gameRenderer;
    }

    /**
     * Sets the game renderer.
     * @param gameRenderer The game Renderer
     */
    public void setGameRenderer(Renderer gameRenderer) {
        this.gameRenderer = gameRenderer;
    }

    /**
     * Gets the editor instance.
     * @return The Editor instance
     */
    public Editor getEditor() {
        return editor;
    }

    /**
     * Sets the editor instance.
     * @param editor The Editor instance
     */
    public void setEditor(Editor editor) {
        this.editor = editor;
    }

    /**
     * Gets the editor scene window.
     * @return The editor SceneWindow
     */
    public SceneWindow getEditorSceneWindow() {
        return editorSceneWindow;
    }

    /**
     * Sets the editor scene window.
     * @param editorSceneWindow The editor SceneWindow
     */
    public void setEditorSceneWindow(SceneWindow editorSceneWindow) {
        this.editorSceneWindow = editorSceneWindow;
    }

    /**
     * Gets the game scene window.
     * @return The game SceneWindow
     */
    public SceneWindow getGameSceneWindow() {
        return gameSceneWindow;
    }

    /**
     * Sets the game scene window.
     * @param gameSceneWindow The game SceneWindow
     */
    public void setGameSceneWindow(SceneWindow gameSceneWindow) {
        this.gameSceneWindow = gameSceneWindow;
    }

    /**
     * Gets the window handle.
     * @return The window handle
     */
    public long getWindowHandle() {
        return windowHandle;
    }

    /**
     * Sets the window handle.
     * @param windowHandle The window handle
     */
    public void setWindowHandle(long windowHandle) {
        this.windowHandle = windowHandle;
    }
}

