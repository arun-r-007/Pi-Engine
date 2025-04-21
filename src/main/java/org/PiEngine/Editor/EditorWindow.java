package org.PiEngine.Editor;

import imgui.ImGui;
import imgui.type.ImBoolean;

public abstract class EditorWindow
{
    protected String name;
    protected boolean isOpen = true;

    protected float x = 100;
    protected float y = 100;
    protected float width = 400;
    protected float height = 300;
    protected boolean useCustomSizeAndPos = false;

    public EditorWindow(String name)
    {
        this.name = name;
    }

    public String getName()
    {
        return name;
    }

    public boolean isOpen()
    {
        return isOpen;
    }

    public void open()
    {
        isOpen = true;
    }

    public void close()
    {
        isOpen = false;
    }

    public void toggle()
    {
        isOpen = !isOpen;
    }

    public void setPosition(float x, float y)
    {
        this.x = x;
        this.y = y;
        useCustomSizeAndPos = true;
    }

    public void setSize(float width, float height)
    {
        this.width = width;
        this.height = height;
        useCustomSizeAndPos = true;
    }

    public void onCreate()
    {
    }

    public void setCustomTheme()
    {
    }

    public void onUpdate(float deltaTime)
    {
    }

    public void renderBase() {
        if (!isOpen)
            return;
    
        if (useCustomSizeAndPos) {
            ImGui.setNextWindowPos(x, y);
            ImGui.setNextWindowSize(width, height);
        }
    
        boolean windowVisible = ImGui.begin(name, new ImBoolean(isOpen));
        if (windowVisible) {
            onRender();
        }
        ImGui.end(); // Always call end, regardless of begin()'s return
    }
    

    public abstract void onRender();

    public void onDestroy()
    {
    }
}
