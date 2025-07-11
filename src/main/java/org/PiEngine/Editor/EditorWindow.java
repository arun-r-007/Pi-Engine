package org.PiEngine.Editor;

import imgui.ImGui;
import imgui.type.ImBoolean;

public abstract class EditorWindow
{
    protected String name;
    protected boolean isOpen = true;
    protected int id;

    protected float x = 100;
    protected float y = 100;
    protected float width = 400;
    protected float height = 300;
    protected boolean useCustomSizeAndPos = false;

    /**
     * Constructs a new EditorWindow with the given name.
     * @param name The window name
     */
    public EditorWindow(String name)
    {
        this.name = name;
    }

    /**
     * Returns the name of the window.
     * @return The window name
     */
    public String getName()
    {
        return name;
    }

    /**
     * Returns whether the window is currently open.
     * @return True if open, false otherwise
     */
    public boolean isOpen()
    {
        return isOpen;
    }

    /**
     * Opens the window.
     */
    public void open()
    {
        isOpen = true;
    }

    /**
     * Closes the window.
     */
    public void close()
    {
        isOpen = false;
    }

    /**
     * Toggles the window open/closed state.
     */
    public void toggle()
    {
        isOpen = !isOpen;
    }

    /**
     * Sets the window position.
     * @param x The x position
     * @param y The y position
     */
    public void setPosition(float x, float y)
    {
        this.x = x;
        this.y = y;
        useCustomSizeAndPos = true;
    }

    /**
     * Sets the window size.
     * @param width The window width
     * @param height The window height
     */
    public void setSize(float width, float height)
    {
        this.width = width;
        this.height = height;
        useCustomSizeAndPos = true;
    }

    /**
     * Called when the window is created. Override for setup logic.
     */
    public void onCreate()
    {
    }

    /**
     * Sets a custom theme for the window. Override to customize appearance.
     */
    public void setCustomTheme()
    {
    }

    /**
     * Called every frame to update window logic.
     */
    public void onUpdate()
    {
    }

    /**
     * Renders the window base and calls onRender().
     */
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
    

    /**
     * Called every frame to render window contents. Must be implemented by subclasses.
     */
    public abstract void onRender();

    /**
     * Called when the window is destroyed. Override for cleanup.
     */
    public void onDestroy()
    {
    }
}
