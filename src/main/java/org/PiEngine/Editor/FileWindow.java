package org.PiEngine.Editor;

import imgui.ImGui;
import imgui.flag.ImGuiTreeNodeFlags;
import imgui.type.ImBoolean;

import java.io.File;
import java.util.Arrays;
import java.util.Comparator;

/**
 * Displays a file explorer for src/main/resources directory.
 */
public class FileWindow extends EditorWindow
{

    private File rootDirectory;
    private static int count = 0;

    public FileWindow() 
    {
        super("File Explorer");
        id = count++;
        rootDirectory = new File("src/main/resources");
    }


    
    @Override
    public void onUpdate() 
    {
    
    }



    @Override
    public void onRender()
    {
        if (!isOpen || rootDirectory == null) return;

        ImBoolean open = new ImBoolean(true);
        if (!ImGui.begin(name + "##" + id, open))
        {
            ImGui.end();
            return;
        }

        if (!open.get())
        {
            Editor.get().queueRemoveWindow(this);
        }

        if (rootDirectory.exists() && rootDirectory.isDirectory())
        {
            renderDirectoryRecursive(rootDirectory);
        }
        else {
            ImGui.text("Directory not found: " + rootDirectory.getPath());
        }

        ImGui.end();
    }



    private void renderDirectoryRecursive(File directory)
    {
        if (directory == null || !directory.exists()) return;
    
        File[] files = directory.listFiles();
        if (files == null) return;
    
        // Sort folders first, then files
        Arrays.sort(files, Comparator
            .comparing(File::isFile)
            .thenComparing(File::getName, String.CASE_INSENSITIVE_ORDER));
    
        for (File file : files)
        {
            ImGui.pushID(file.getPath());
    
            int flags = ImGuiTreeNodeFlags.OpenOnArrow;
            if (file.isFile())
            {
                flags |= ImGuiTreeNodeFlags.Leaf;
            }
    
            boolean nodeOpen = ImGui.treeNodeEx(file.getName(), flags);
    
            if (ImGui.isItemClicked())
            {
                System.out.println("Selected file: " + file.getAbsolutePath());
            }
    
            if (nodeOpen)
            {
                if (file.isDirectory())
                {
                    renderDirectoryRecursive(file);
                }
                ImGui.treePop();
            }
    
            ImGui.popID();
        }
    }
    


    @Override
    public void setCustomTheme()
    {

    }
}
