package org.PiEngine.Editor;

import org.PiEngine.Render.Framebuffer;
import imgui.ImGui;
import imgui.ImVec2;
import imgui.type.ImBoolean;

public class SceneWindow extends EditorWindow
{
    private Framebuffer frame;
    private int outputTex;
    public static int count = 0;

    /**
     * Constructs a new SceneWindow with the given name.
     * @param name The window name
     */
    public SceneWindow(String name)
    {
        super(name);
        id = count++;
    }

    /**
     * Constructs a new SceneWindow with the given name and framebuffer.
     * @param name The window name
     * @param fb The framebuffer to display
     */
    public SceneWindow(String name, Framebuffer fb)
    {
        super(name);
        id = count++;
        frame = fb;
    }

    /**
     * Sets the output texture ID for rendering.
     * @param o The texture ID
     */
    public void setid(int o)
    {
        outputTex = o;
    }

    /**
     * Sets the framebuffer to display.
     * @param fb The framebuffer
     */
    public void setFrameBuffer(Framebuffer fb)
    {
        frame = fb;
    }

    /**
     * Renders the scene window and its framebuffer output.
     */
    @Override
    public void onRender()
    {
        ImBoolean isOpen = new ImBoolean(true);
        if (!ImGui.begin(name + "##" + id, isOpen))
        {
            ImGui.end();
            return;
        }

        if (!isOpen.get())
        {
            Editor.get().queueRemoveWindow(this);
        }

        ImVec2 availSize = ImGui.getContentRegionAvail();
        float availWidth = availSize.x;
        float availHeight = availSize.y;

        int textureToRender = outputTex;
        float textureWidth = availWidth;
        float textureHeight = availHeight;

        if (frame != null)
        {
            // Use Framebuffer's texture and size
            textureToRender = frame.getTextureId();

            float fbWidth = frame.getWidth();
            float fbHeight = frame.getHeight();

            // Maintain aspect ratio
            float fbAspect = fbWidth / fbHeight;
            float windowAspect = availWidth / availHeight;

            if (windowAspect > fbAspect)
            {
                // Window is wider than framebuffer: match height
                textureHeight = availHeight;
                textureWidth = fbAspect * textureHeight;
            }
            else
            {
                // Window is taller than framebuffer: match width
                textureWidth = availWidth;
                textureHeight = textureWidth / fbAspect;
            }
        }

        // Center the image
        ImVec2 cursorPos = ImGui.getCursorPos();
        float offsetX = (availWidth - textureWidth) * 0.5f;
        float offsetY = (availHeight - textureHeight) * 0.5f;
        ImGui.setCursorPos(cursorPos.x + offsetX, cursorPos.y + offsetY);

        // Render the image
        ImGui.image(
            textureToRender,
            new ImVec2(textureWidth, textureHeight),
            new ImVec2(0, 1),
            new ImVec2(1, 0)
        );

        ImGui.end();
    }
}
