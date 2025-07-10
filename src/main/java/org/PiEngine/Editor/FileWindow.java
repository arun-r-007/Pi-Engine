package org.PiEngine.Editor;

import imgui.ImGui;
import imgui.flag.ImGuiTreeNodeFlags;
import imgui.type.ImBoolean;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.Comparator;

/**
 * Displays a file explorer for src/main/resources directory.
 */
public class FileWindow extends EditorWindow
{
    private File rootDirectory;
    private static int count = 0;
    private File rightClickedFile = null;

    public FileWindow()
    {
        super("File Explorer");
        id = count++;
        rootDirectory = new File("src/main/resources");
    }

    @Override
    public void onRender()
    {
        if (!isOpen || rootDirectory == null)
        {
            return;
        }

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
        else
        {
            ImGui.text("Directory not found: " + rootDirectory.getPath());
        }
        
        ImGui.end();
    }



    private void renderDirectoryRecursive(File directory)
    {
        File[] files = directory.listFiles();
        if (files == null) return;
    
        Arrays.sort(files, Comparator.comparing(File::getName));
    
        for (File file : files)
        {
            ImGui.pushID(file.getPath());
    
            int flags = ImGuiTreeNodeFlags.OpenOnArrow;
            if (file.isFile())
            {
                flags |= ImGuiTreeNodeFlags.Leaf;
            }
    
            boolean nodeOpen = ImGui.treeNodeEx(file.getName(), flags);
    
            // --- Context Menu Support ---
            if (ImGui.beginPopupContextItem(file.getPath()))
            {
                rightClickedFile = file;
                if (file.isFile())
                {
                    if (ImGui.menuItem("VS Code"))
                    {
                        openInVSCodeOrFallback(file.getAbsoluteFile());
                    }
                
                    if (ImGui.menuItem("Note-Pad"))
                    {
                        openInNotepad(file.getAbsoluteFile());
                    }
                
                    if (ImGui.menuItem("Delete"))
                    {
                        System.out.print("Delete" + file.getName());
                        // file.delete();
                    }

                }
                
                else if (file.isDirectory())
                {
                    if (ImGui.menuItem("Create New File"))
                    {
                        // createNewFile(file);
                    }
                    if (ImGui.menuItem("Delete Folder"))
                    {
                        // deleteDirectory(file);
                    }
                }
                ImGui.endPopup();
            }
    
            if (nodeOpen)
            {
                if (file.isDirectory())
                {
                    renderDirectoryRecursive(file); // Recursively render sub-directories
                }
                ImGui.treePop();  
            }
    
            ImGui.popID();
        }
    }
    
    

    private void openInNotepad(File file)
    {
        try
        {
            new ProcessBuilder("cmd", "/c", "notepad \"" + file.getAbsolutePath() + "\"").start();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    
    
    private void openInVSCodeOrFallback(File file)
    {
        try
        {
            Process process = new ProcessBuilder("cmd", "/c", "code \"" + file.getAbsolutePath() + "\"").start();
            // Wait briefly and check if VSCode failed (error code non-zero)
            Thread.sleep(500);
            try
            {
                if (process.exitValue() != 0)
                {
                    // VS Code failed, fallback to notepad
                    openInNotepad(file);
                }
            }
            catch (IllegalThreadStateException ignored)
            {
                // Process still running = VS Code opened fine
            }
        }
        catch (IOException | InterruptedException e)
        {
            e.printStackTrace();
            // fallback to notepad
            openInNotepad(file);
        }
    }

    
    
    @Override
    public void setCustomTheme()
    {
        // Optional: Style your file window later
    }
}
