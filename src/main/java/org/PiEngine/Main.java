package org.PiEngine;

import org.lwjgl.opengl.GL;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL30.*;

import imgui.ImGui;
import imgui.ImGuiIO;
import imgui.flag.ImGuiConfigFlags;
import imgui.gl3.ImGuiImplGl3;
import imgui.glfw.ImGuiImplGlfw;

import org.PiEngine.Math.*;
import org.PiEngine.Core.*;
import org.PiEngine.GameObjects.*;
import org.PiEngine.Component.*;
import org.PiEngine.Editor.*;
import org.PiEngine.Render.*;
import org.PiEngine.Render.Passes.*;
import org.PiEngine.Scripting.*;


public class Main
{
    public static void main(String[] args)
    {
        if (!glfwInit())
        {
            throw new IllegalStateException("Unable to initialize GLFW");
        }
        
        int width = 1600;
        int height = 900;
        long window = glfwCreateWindow(width, height, "Pi-Engine", 0, 0);
        if (window == 0)
        {
            throw new RuntimeException("Failed to create window");
        }

        Input.init(window);
        glfwMakeContextCurrent(window);
        GL.createCapabilities();

        glViewport(0, 0, width, height);

        // --- ImGui Init ---
        ImGui.createContext();
        ImGuiIO io = ImGui.getIO();
        io.addConfigFlags(ImGuiConfigFlags.DockingEnable);
        ImGuiImplGlfw imguiGlfw = new ImGuiImplGlfw();
        ImGuiImplGl3 imguiGl3 = new ImGuiImplGl3();
        imguiGlfw.init(window, true);
        imguiGl3.init("#version 330 core");

        // --- Camera Setup ---
        Camera Scenecamera = new Camera((float) width / height, 0.01f, 100.0f);
        Scenecamera.setPosition(new Vector(0, 0, 20.0f));
        Scenecamera.setRotation(new Vector(0, 0, 0));
        Scenecamera.setOrthographic( 8*-2, 8*2, -2 *4.5f, 2*4.5f, 1.0f, 100f);
        Scenecamera.updateProjectionMatrix();
        Scenecamera.updateViewMatrix();


        glEnable(GL_DEPTH_TEST);

        // --- GameObject Setup ---
        GameObject world = new GameObject("World");
        GameObject player = new GameObject("Player");
        GameObject enemy = new GameObject("Enemy");
        GameObject enemy1 = new GameObject("Enemy1");
        GameObject enemy2 = new GameObject("Enemy2");
        GameObject enemy3 = new GameObject("Enemy3");
        GameObject holder = new GameObject("Holder");
        GameObject childHolder = new GameObject("ChildHolder");
        GameObject cChildHolder = new GameObject("CChildHolder");
        GameObject Camera = new GameObject("Main Camera");


        player.transform.setLocalPosition(new Vector(0f, 0, 0));
        holder.transform.setLocalPosition(new Vector(4f, 0, 0));
        childHolder.transform.setLocalPosition(new Vector(5f, 0, 0));
        cChildHolder.transform.setLocalPosition(new Vector(5f, 0, 0));
        Camera.transform.setLocalPosition(new Vector(0, 0, 20));

        cChildHolder.setLayer(LayerManager.getLayerBit("Layer30"));

        world.addChild(player);
        world.addChild(enemy);
        world.addChild(enemy1);
        world.addChild(enemy2);
        world.addChild(enemy3);
        world.addChild(holder);
        holder.addChild(childHolder);
        childHolder.addChild(cChildHolder);
        player.addChild(Camera);


        // --- Components ---
        player.addComponent(new Movemet());
        enemy.addComponent(new Follower());
        enemy1.addComponent(new Follower());
        enemy2.addComponent(new Follower());
        enemy3.addComponent(new Follower());
        holder.addComponent(new SpinComponent());
        //childHolder.addComponent(new SpinComponent());
        Camera.addComponent(new CameraComponent());





        // Add RendererComponent to all objects
        player.addComponent(new RendererComponent());
        enemy.addComponent(new RendererComponent());
        enemy1.addComponent(new RendererComponent());
        enemy2.addComponent(new RendererComponent());
        enemy3.addComponent(new RendererComponent());
        holder.addComponent(new RendererComponent());
        childHolder.addComponent(new RendererComponent());
        cChildHolder.addComponent(new RendererComponent());
        


        // Set Layer
        player.setLayerByName("Layer1", false);
        enemy.setLayerByName("Layer1", false);
        enemy1.setLayerByName("Layer1", false);
        enemy2.setLayerByName("Layer1", false);
        enemy3.setLayerByName("Layer1", false);



        enemy.getComponent(Follower.class).Target = cChildHolder;
        enemy1.getComponent(Follower.class).Target = enemy;
        enemy2.getComponent(Follower.class).Target = enemy1;
        enemy3.getComponent(Follower.class).Target = enemy2;

        Time.timeScale = 1.0f;

        // --- Editor Setup ---
        
        Editor editor = Editor.getInstance(window, false);
        editor.init();

        editor.addWindow(new DockingWindow());
        editor.addWindow(new LayerWindow());
        editor.addWindow(new HierarchyWindow(world));
        editor.addWindow(new InspectorWindow(false));
        editor.addWindow(new PerfomanceWindow());
        editor.addWindow(new ConsoleWindow());
        editor.addWindow(new NavigationWindow());
        editor.addWindow(new FileWindow());




        // --- Renderer Setup ---
        Shader DefaultShader = new Shader
        (
            "src\\main\\resources\\Shaders\\Camera\\Default.vert",
            "src\\main\\resources\\Shaders\\Camera\\Default.frag",
            null
        );

        

        Shader CRTShader = new Shader
        (
            "src\\main\\resources\\Shaders\\PostProcess\\SCREEN.vert", 
            "src\\main\\resources\\Shaders\\PostProcess\\CRT.frag", 
            null    
        );

        Shader BloomShader = new Shader
        (
            "src\\main\\resources\\Shaders\\PostProcess\\SCREEN.vert", 
            "src\\main\\resources\\Shaders\\PostProcess\\BLUR.frag", 
            null    
        );

        Shader FinalShader = new Shader
        (
            "src\\main\\resources\\Shaders\\PostProcess\\SCREEN.vert", 
            "src\\main\\resources\\Shaders\\PostProcess\\FINAL.frag", 
            null    
        );
        
        Renderer SceneRenderer = new Renderer();
        GeometryPass GP = new GeometryPass("SceneGeomtry", DefaultShader, width, height);
        SceneRenderer.addPass(GP);
        SceneRenderer.setFinalPass("SceneGeomtry");


        Renderer GameRenderer = new Renderer();
        GeometryPass GameGP = new GeometryPass("GameGeomtry",DefaultShader, width, height);
        GeometryPass GameGP1 = new GeometryPass("GameGeomtry1",DefaultShader, width, height);

        GameGP1.setLayerMask(LayerManager.getLayerBit(LayerManager.getLayerName(30)));

        PostProcessingPass GamePP = new PostProcessingPass("CRT",CRTShader, width, height, 2);
        PostProcessingPass GamePP1 = new PostProcessingPass("BLUR",BloomShader, width, height, 1);
        PostProcessingPass finalPP = new PostProcessingPass("FINAL",FinalShader, width, height, 1);


        GameRenderer.addPass(GameGP); 
        GameRenderer.addPass(GameGP1); 

        GameRenderer.addPass(GamePP);
        GameRenderer.addPass(GamePP1);
        GameRenderer.addPass(finalPP);

        GameRenderer.setFinalPass("FINAL");
        GameRenderer.connect("GameGeomtry", "CRT", 0);
        GameRenderer.connect("GameGeomtry1", "BLUR", 0);
        GameRenderer.connect("BLUR", "CRT", 1);
        GameRenderer.connect("CRT", "FINAL", 0);

        editor.addWindow(new RendererInspector(GameRenderer));    


       // GameRenderer.connect("GameGeomtry", "CRT", 0);

       
        RenderGraphEditorWindow graphWindow = new RenderGraphEditorWindow(GameRenderer);
        editor.addWindow(graphWindow);
        SceneWindow sceneWindow = new SceneWindow("Scene");
        editor.addWindow(sceneWindow);

        SceneWindow sceneWindow1 = new SceneWindow("Game");
        editor.addWindow(sceneWindow1);

        // Drivers
        


        try 
        {
            //CompileScripts compiler = CompileScripts.getInstance("src\\main\\resources\\Scripts", "Compiled", null);
            //compiler.compileScripts();
        } 
        catch (Exception e) 
        {
            e.printStackTrace();
        }


        ScriptLoader loader = ScriptLoader.getInstance();
        loader.loadComponentScripts("Compiled/Scripts");

        // GL43.glEnable(GL43.GL_DEBUG_OUTPUT);
        // GL43.glDebugMessageCallback((source, type, id, severity, length, message, userParam) -> {
        //     System.err.println("GL DEBUG: " + org.lwjgl.opengl.GLDebugMessageCallback.getMessage(length, message));
        // }, 0);
        
        // --- Main Loop ---
        while (!glfwWindowShouldClose(window))
        {
            glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);

            Time.update();
            Input.update();
            

            float moveSpeed = 10f * Time.deltaTime;

            if (Input.isKeyDown(GLFW_KEY_UP)) Scenecamera.getPosition().y += moveSpeed;
            if (Input.isKeyDown(GLFW_KEY_DOWN)) Scenecamera.getPosition().y -= moveSpeed;
            if (Input.isKeyDown(GLFW_KEY_LEFT)) Scenecamera.getPosition().x -= moveSpeed;
            if (Input.isKeyDown(GLFW_KEY_RIGHT)) Scenecamera.getPosition().x += moveSpeed;

            // --- Game Logic ---
            world.update();
            Scenecamera.updateViewMatrix();

            
            SceneRenderer.renderPipeline(Scenecamera, world);
            sceneWindow.setFrameBuffer(SceneRenderer.getFinalFramebuffer());
            
            Framebuffer GameFB = null;
            CameraComponent GameCamear = Camera.getComponent(CameraComponent.class);
            if(GameCamear != null)
            {
                GameRenderer.renderPipeline(GameCamear.getCamera(), world);
                GameFB = GameRenderer.getFinalFramebuffer();        
            }
            sceneWindow1.setFrameBuffer(GameFB);
            
            // --- Editor Update ---
            editor.update(Time.deltaTime);

            glfwSwapBuffers(window);
            glfwPollEvents();
            
            // System.out.println(GameRenderer.getConnections());
            

            // System.out.println("input GP OP:" + GameGP.getOutputTexture());
            // System.out.println("input BLUR:" + GamePP.getInputTexture(0));
            // System.out.println("input CRT:" + GamePP1.getInputTexture(0));
            // System.out.println("input FINAL[0] :" + finalPP.getInputTexture(0));
            // System.out.println("input FINAL[1] :" + finalPP.getInputTexture(1));

        }

        // --- Cleanup ---
        ImGui.destroyContext();
        glfwTerminate();
    }
}
