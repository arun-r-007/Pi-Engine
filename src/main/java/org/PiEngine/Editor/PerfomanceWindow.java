package org.PiEngine.Editor;

import imgui.ImGui;
import org.PiEngine.Core.*;
import imgui.ImVec2;

public class PerfomanceWindow extends EditorWindow
{

    public PerfomanceWindow()
    {
        super("Perfomance");
    }

    @Override
    public void onRender()
    {
        ImGui.begin("PiEngine Perfomance");
        ImGui.text("DeltaTime : " + String.format("%.10f", Time.unscaledDeltaTime));
        ImGui.text("FPS : " + 1/Time.unscaledDeltaTime);
        ImGui.text("Average FPS : " + Time.getAverageFPS());
        ImGui.text("Delta Time (graph)");
        ImGui.plotLines("", Time.getDeltaHistory(), Time.getHistorySize(), 0, null, 0.0f, 0.01f,new ImVec2(0f, 80f));
        ImGui.end();
    }

}
