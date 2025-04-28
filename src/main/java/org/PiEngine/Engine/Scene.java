
package org.PiEngine.Engine;

import org.PiEngine.Math.*;
import org.PiEngine.Core.*;
import org.PiEngine.GameObjects.*;


import java.io.Serializable;

import org.PiEngine.Component.*;
import org.PiEngine.Editor.*;
import org.PiEngine.Render.*;
import org.PiEngine.Render.Passes.*;
import org.PiEngine.Scripting.*;


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

    public static Scene getInstance()
    {
        if (instance == null)
        {
            instance = new Scene();
            instance.Name = "Scene";
 
        }
        return instance;
    }

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

        // GameObject player = new GameObject("Player");
        // GameObject enemy = new GameObject("Enemy");
        // GameObject enemy1 = new GameObject("Enemy1");
        // GameObject enemy2 = new GameObject("Enemy2");
        // GameObject enemy3 = new GameObject("Enemy3");
        // GameObject holder = new GameObject("Holder");
        // GameObject childHolder = new GameObject("ChildHolder");
        // GameObject cChildHolder = new GameObject("CChildHolder");
        // // Setup hierarchy
        // root.addChild(player);
        // root.addChild(enemy);
        // root.addChild(enemy1);
        // root.addChild(enemy2);
        // root.addChild(enemy3);
        // root.addChild(holder);
        // holder.addChild(childHolder);
        // childHolder.addChild(cChildHolder);
        
        // // Setup transforms
        // player.transform.setLocalPosition(new Vector(0f, 0, 0));
        // holder.transform.setLocalPosition(new Vector(4f, 0, 0));
        // childHolder.transform.setLocalPosition(new Vector(5f, 0, 0));
        // cChildHolder.transform.setLocalPosition(new Vector(5f, 0, 0));
        
        // cChildHolder.setLayer(LayerManager.getLayerBit("Layer30"));
        
        // // Add Components
        // player.addComponent(new Movemet());
        // enemy.addComponent(new Follower());
        // enemy1.addComponent(new Follower());
        // enemy2.addComponent(new Follower());
        // enemy3.addComponent(new Follower());
        // holder.addComponent(new SpinComponent());
        
        // player.addComponent(new RendererComponent());
        // enemy.addComponent(new RendererComponent());
        // enemy1.addComponent(new RendererComponent());
        // enemy2.addComponent(new RendererComponent());
        // enemy3.addComponent(new RendererComponent());
        // holder.addComponent(new RendererComponent());
        // childHolder.addComponent(new RendererComponent());
        // cChildHolder.addComponent(new RendererComponent());
        
        // player.setLayerByName("Layer1", false);
        // enemy.setLayerByName("Layer1", false);
        // enemy1.setLayerByName("Layer1", false);
        // enemy2.setLayerByName("Layer1", false);
        // enemy3.setLayerByName("Layer1", false);
        
        // enemy.getComponent(Follower.class).Target = cChildHolder;
        // enemy1.getComponent(Follower.class).Target = enemy;
        // enemy2.getComponent(Follower.class).Target = enemy1;
        // enemy3.getComponent(Follower.class).Target = enemy2;
        
        GameObject cameraObject = new GameObject("Main Camera");
        GameCamera = cameraObject;
        root.addChild(cameraObject);
        cameraObject.transform.setLocalPosition(new Vector(0, 0, 20));
        cameraObject.addComponent(new CameraComponent());
        
        // Setup Editor
        editor = Editor.getInstance(window, false);
        editor.init();
        editor.addWindow(new DockingWindow());
        editor.addWindow(new LayerWindow());
        editor.addWindow(new HierarchyWindow(root));
        editor.addWindow(new InspectorWindow(false));
        editor.addWindow(new PerfomanceWindow());
        editor.addWindow(new ConsoleWindow());
        editor.addWindow(new NavigationWindow());

        // Setup Renderers
        Shader DefaultShader = new Shader(
            "src\\main\\resources\\Shaders\\Camera\\Default.vert",
            "src\\main\\resources\\Shaders\\Camera\\Default.frag",
            null
        );

        Shader CRTShader = new Shader(
            "src\\main\\resources\\Shaders\\PostProcess\\SCREEN.vert",
            "src\\main\\resources\\Shaders\\PostProcess\\CRT.frag",
            null
        );

        Shader BloomShader = new Shader(
            "src\\main\\resources\\Shaders\\PostProcess\\SCREEN.vert",
            "src\\main\\resources\\Shaders\\PostProcess\\BLUR.frag",
            null
        );

        Shader FinalShader = new Shader(
            "src\\main\\resources\\Shaders\\PostProcess\\SCREEN.vert",
            "src\\main\\resources\\Shaders\\PostProcess\\FINAL.frag",
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
        gameRenderer.connect("BLUR", "CRT", 1);
        gameRenderer.connect("CRT", "FINAL", 0);

        editor.addWindow(new RendererInspector(gameRenderer));
        editor.addWindow(new RenderGraphEditorWindow(gameRenderer));

        // Scene Windows
        editorSceneWindow = new SceneWindow("Scene");
        editor.addWindow(editorSceneWindow);

        gameSceneWindow = new SceneWindow("Game");
        editor.addWindow(gameSceneWindow);

        // Scripts
        ScriptLoader.getInstance().loadComponentScripts("Compiled/Scripts");

        Time.timeScale = 1.0f;
    }

    public void update()
    {   
        editorCamera.updateViewMatrix();
        root.update();
    }

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

    public void Save()
    {
        try 
        {
            SceneSerializerJSON.serialize(instance, "src\\main\\resources\\Test.json");
        } 
        catch (Exception e) 
        {
            Console.error(e.toString());
        }
    }

    public void Load()
    {
        try 
        {
            SceneDeserializerJSON.deserialize("src\\main\\resources\\Test.json");
        } 
        catch (Exception e) 
        {
            e.printStackTrace();
        }
    }

    public GameObject getRoot()
    {
        return root;
    }

    public Camera getEditorCamera()
    {
        return editorCamera;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public static void setInstance(Scene instance) {
        Scene.instance = instance;
    }

    public void setRoot(GameObject root) {
        this.root = root;
    }

    public void setEditorCamera(Camera editorCamera) {
        this.editorCamera = editorCamera;
    }

    public GameObject getGameCamera() {
        return GameCamera;
    }

    public void setGameCamera(GameObject gameCamera) {
        GameCamera = gameCamera;
    }

    public Renderer getSceneRenderer() {
        return sceneRenderer;
    }

    public void setSceneRenderer(Renderer sceneRenderer) {
        this.sceneRenderer = sceneRenderer;
    }

    public Renderer getGameRenderer() {
        return gameRenderer;
    }

    public void setGameRenderer(Renderer gameRenderer) {
        this.gameRenderer = gameRenderer;
    }

    public Editor getEditor() {
        return editor;
    }

    public void setEditor(Editor editor) {
        this.editor = editor;
    }

    public SceneWindow getEditorSceneWindow() {
        return editorSceneWindow;
    }

    public void setEditorSceneWindow(SceneWindow editorSceneWindow) {
        this.editorSceneWindow = editorSceneWindow;
    }

    public SceneWindow getGameSceneWindow() {
        return gameSceneWindow;
    }

    public void setGameSceneWindow(SceneWindow gameSceneWindow) {
        this.gameSceneWindow = gameSceneWindow;
    }

    public long getWindowHandle() {
        return windowHandle;
    }

    public void setWindowHandle(long windowHandle) {
        this.windowHandle = windowHandle;
    }
}

