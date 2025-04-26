package org.PiEngine.Editor;

import org.PiEngine.Engine.*;

import imgui.ImGui;
import imgui.flag.ImGuiCol;
import imgui.flag.ImGuiComboFlags;

public class ConsoleWindow extends EditorWindow
{
    private static final String[] filters = { "All", "Warning", "Error" };
    private static int currentFilter = 0; // 0 = All

    public ConsoleWindow()
    {
        super("Console");
    }

    @Override
    public void onRender()
    {
        ImGui.begin(name);
        // Top Bar: Filter dropdown and Clear button
        ImGui.beginChild("ConsoleTopBar", 0, 30, false);
        ImGui.text("Filter: ");
        ImGui.sameLine();
        if (ImGui.beginCombo("##ConsoleFilter", filters[currentFilter], ImGuiComboFlags.None))
        {
            for (int i = 0; i < filters.length; i++)
            {
                boolean isSelected = (currentFilter == i);
                if (ImGui.selectable(filters[i], isSelected))
                {
                    currentFilter = i;
                }
                if (isSelected)
                {
                    ImGui.setItemDefaultFocus();
                }
            }
            ImGui.endCombo();
        }
        ImGui.sameLine();
        if (ImGui.button("Clear"))
        {
            Console.messages.clear();
        }
        ImGui.endChild();

        // Messages Area
        ImGui.beginChild("ConsoleMessages");

        for (String[] message : Console.messages)
        {
            String time = message[0];
            String text = message[1];
            String type = message[2];
            String caller = message[3];

            // Apply filter
            if (currentFilter == 1 && !type.equals("WARNING"))
                continue;
            if (currentFilter == 2 && !type.equals("ERROR"))
                continue;

            // Set color
            if (type.equals("ERROR"))
                ImGui.pushStyleColor(ImGuiCol.Text, 1.0f, 0.2f, 0.2f, 1.0f); // Red
            else if (type.equals("WARNING"))
                ImGui.pushStyleColor(ImGuiCol.Text, 1.0f, 1.0f, 0.0f, 1.0f); // Yellow
            else
                ImGui.pushStyleColor(ImGuiCol.Text, 1.0f, 1.0f, 1.0f, 1.0f); // White

            ImGui.textWrapped("[" + time + "] (" + caller + ") " + text);

            ImGui.popStyleColor();
        }

        ImGui.endChild();
        ImGui.end();
    }
}
