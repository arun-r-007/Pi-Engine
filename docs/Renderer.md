# Rendering System

**File:** `src/main/java/org/PiEngine/Render/Renderer.java`

## Overview
The Pi-Engine's rendering system is built on a flexible multi-pass architecture that enables advanced visual effects through a configurable pipeline of render passes.

## Key Components

### RenderPass
```java
public abstract class RenderPass {
    protected String name;
    protected Shader shader;
    public Framebuffer framebuffer;
    protected int[] inputTextures;
    protected int inputCount;
    protected int width, height;
    protected int layerMask;
}
```

### Render Pipeline Structure
1. **Pass Management**
   ```java
   private final Map<String, RenderPass> passes;
   private final Map<String, Map<Integer, String>> connections;
   private String finalPassName;
   ```

2. **Pass Types**
   - **Camera Pass:** Renders 3D scene objects
   - **Post-Process Pass:** Applies effects to rendered images
   - **Composite Pass:** Combines multiple inputs
   - **Final Output Pass:** Presents to screen

## Creating Custom Render Passes

### 1. Basic Structure
```java
public class CustomPass extends RenderPass {
    public CustomPass(String name, int width, int height) {
        super(name, new Shader("custom.vert", "custom.frag"), 
              width, height, 1);
    }

    @Override
    public void render(GameObject root, Camera camera) {
        bindAndPrepare();
        
        // Custom rendering logic
        shader.setUniforms();
        renderScene();
        
        unbind();
    }
}
```

### 2. Shader Integration
```glsl
// Vertex Shader
#version 330 core
layout(location = 0) in vec3 position;
layout(location = 1) in vec2 texCoord;

out vec2 v_TexCoord;

void main() {
    gl_Position = vec4(position, 1.0);
    v_TexCoord = texCoord;
}

// Fragment Shader
#version 330 core
in vec2 v_TexCoord;
uniform sampler2D u_Texture0;

out vec4 FragColor;

void main() {
    vec4 color = texture(u_Texture0, v_TexCoord);
    FragColor = color;
}
```

## Pipeline Configuration

### 1. Setting Up Passes
```java
Renderer renderer = new Renderer();

// Add passes
renderer.addPass(new CameraPass("Main", width, height));
renderer.addPass(new BloomPass("Bloom", width, height));
renderer.addPass(new TonemapPass("Tonemap", width, height));

// Connect passes
renderer.connectPasses("Main", "Bloom", 0);
renderer.connectPasses("Bloom", "Tonemap", 0);
renderer.setFinalPass("Tonemap");
```

### 2. Layer-Based Rendering
```java
// Configure layer masks
mainPass.setLayerMask(LayerMask.VISIBLE);
shadowPass.setLayerMask(LayerMask.CAST_SHADOW);
```

## Example: Post-Processing Pass

### Bloom Effect
```java
public class BloomPass extends RenderPass {
    private Framebuffer brightPass;
    private Framebuffer blur1, blur2;
    
    public BloomPass(String name, int width, int height) {
        super(name, new Shader("post/bloom.vert", "post/bloom.frag"), 
              width, height, 1);
              
        brightPass = new Framebuffer(width, height);
        blur1 = new Framebuffer(width / 2, height / 2);
        blur2 = new Framebuffer(width / 2, height / 2);
    }
    
    @Override
    public void render(GameObject root, Camera camera) {
        // Extract bright areas
        brightPass.bind();
        renderBrightPass();
        
        // Blur horizontally
        blur1.bind();
        renderBlur(true);
        
        // Blur vertically
        blur2.bind();
        renderBlur(false);
        
        // Composite with scene
        framebuffer.bind();
        renderComposite();
    }
}
```

## Performance Optimization

### 1. Render Pass Optimization
- Use appropriate resolution for each pass
- Minimize texture reads/writes
- Batch similar operations

### 2. Layer-Based Culling
- Group objects by layer
- Skip unnecessary passes
- Use efficient layer masks

### 3. Resource Management
- Properly dispose of framebuffers
- Share shaders when possible
- Cache uniform locations

## Debug Features

### 1. Visual Debugging
```java
public void debugRender() {
    ImGui.begin("Render Passes");
    for (RenderPass pass : passes.values()) {
        ImGui.image(pass.getOutputTexture(), 200, 200);
        ImGui.text(pass.getName());
    }
    ImGui.end();
}
```

### 2. Performance Monitoring
- Frame time tracking
- Draw call counting
- Memory usage statistics
