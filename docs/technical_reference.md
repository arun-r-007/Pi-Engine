# Pi-Engine Technical Documentation

## Architecture Overview

### Core Systems
1. **GameObject System**
   - Hierarchical scene graph
   - Component-based architecture
   - Layer-based rendering system

2. **Rendering Pipeline**
   - Multi-pass rendering system
   - Customizable render passes
   - Framebuffer management
   - Post-processing support

3. **Component System**
   - Dynamic component attachment
   - Lifecycle management
   - Serialization support

## GameObject System Details

### Class: GameObject
**Location:** `src/main/java/org/PiEngine/GameObjects/GameObject.java`

#### Properties
```java
public String Name;              // GameObject identifier
public Transform transform;      // Spatial information
private int id;                 // Unique identifier
private int layer;              // Rendering layer
private List<Component> components;  // Attached components
```

#### Public Methods
```java
// Construction
public GameObject(String name)

// Hierarchy Management
public void addChild(GameObject child)
public void reparentTo(GameObject newParent)

// Component Management
public void addComponent(Component component)
public <T extends Component> T getComponent(Class<T> type)
public List<Component> getAllComponents()
public void removeComponent(Component cmp)

// Lifecycle Methods
public void update()
public void fixedUpdate()
public void render(Camera camera, int layerMask)
public void debugRender()
public void destroy()

// Layer Management
public void setLayer(int newLayer)
public void setLayerOnly(int newLayer)
public void setLayerRecursively(int layerBit)
public void setLayerByName(String layerName, boolean recursive)
```

## Rendering Pipeline

### Class: RenderPass
**Location:** `src/main/java/org/PiEngine/Render/RenderPass.java`

#### Overview
The rendering pipeline is built on a flexible pass system where each pass can process input textures and produce output to a framebuffer. This allows for complex effects and post-processing.

#### Properties
```java
protected String name;           // Pass identifier
protected Shader shader;         // GLSL shader program
public Framebuffer framebuffer; // Output buffer
protected int[] inputTextures;   // Input texture array
protected int inputCount;        // Number of input textures
protected int width, height;     // Render dimensions
protected int layerMask;        // Layer filtering
```

#### Creating Custom Render Passes

1. **Basic Structure:**
```java
public class CustomRenderPass extends RenderPass {
    public CustomRenderPass(String name, int width, int height) {
        super(name, new Shader("path/to/vert", "path/to/frag"), 
              width, height, inputCount);
    }

    @Override
    public void render(GameObject root, Camera camera) {
        bindAndPrepare();
        // Custom rendering logic
        unbind();
    }
}
```

2. **Input Management:**
```java
// Set input textures from previous passes
setInputTexture(0, previousPass.getOutputTexture());

// Access in shader
uniform sampler2D u_Texture0;
```

### Demo Scene Analysis (Test.json)

The demo scene demonstrates the hierarchical structure:

```
Root
└── Player
    ├── Movement Component (speed: 10.0)
    └── RendererComponent (size: 2.5)
└── Enemy
    ├── Follower Component (speed: 5.0, MinumDist: 1.25)
    └── RendererComponent (size: 2.5)
```

#### Render Pipeline Flow
1. **Scene Setup**
   - Load objects and components
   - Configure layer masks

2. **Render Process**
   - Camera culling based on layers
   - Component render calls
   - Post-processing passes

3. **Output Generation**
   - Final framebuffer presentation
   - Debug visualization (if enabled)

## Custom Rendering Pass Example

Here's a complete example of creating a custom post-processing effect:

```java
public class BlurPass extends RenderPass {
    public BlurPass(int width, int height) {
        super("Blur", new Shader("shaders/post/blur.vert", 
                                "shaders/post/blur.frag"), 
              width, height, 1);
    }

    @Override
    public void render(GameObject root, Camera camera) {
        bindAndPrepare();
        
        // Set custom uniforms
        shader.setUniform1f("u_BlurStrength", 1.5f);
        
        // Render full-screen quad
        renderQuad();
        
        unbind();
    }
}
```

### Usage in Pipeline
```java
Renderer renderer = new Renderer();
BlurPass blurPass = new BlurPass(1920, 1080);
renderer.addPass(blurPass);
renderer.connectPasses("MainPass", "BlurPass", 0);
```

## Performance Considerations
- Layer-based culling reduces draw calls
- Hierarchical updates optimize scene traversal
- Efficient component access through caching
