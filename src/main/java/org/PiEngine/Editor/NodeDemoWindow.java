package org.PiEngine.Editor;

import imgui.ImGui;
import imgui.type.ImInt;
import imgui.extension.imnodes.ImNodes;

public class NodeDemoWindow extends EditorWindow
{
  public NodeDemoWindow()
  {
    super("Node Editor Demo");
  }

  @Override
  public void onCreate()
  {
    ImNodes.createContext();   // once per window lifecycle
  }

  @Override
  public void onDestroy()
  {
    ImNodes.destroyContext();
  }

  @Override
  public void onRender()
  {
    // 1) Wrap your node editor in an ImGui window
    ImGui.begin(getName());
    ImNodes.beginNodeEditor();

      // draw a single node
      ImNodes.beginNode(1);
        ImNodes.beginNodeTitleBar();
          ImGui.text("Node 1");
        ImNodes.endNodeTitleBar();

        ImNodes.beginInputAttribute(2);
          ImGui.text("Input");
        ImNodes.endInputAttribute();

        ImNodes.beginOutputAttribute(3);
          ImGui.text("Output");
        ImNodes.endOutputAttribute();
      ImNodes.endNode();

      ImNodes.beginNode(4);
        ImNodes.beginNodeTitleBar();
          ImGui.text("Node 2");
        ImNodes.endNodeTitleBar();

        ImNodes.beginInputAttribute(5);
          ImGui.text("Input");
        ImNodes.endInputAttribute();

        ImNodes.beginOutputAttribute(6);
          ImGui.text("Output");
        ImNodes.endOutputAttribute();
      ImNodes.endNode();

    ImNodes.endNodeEditor();
    ImGui.end();

    // 2) Query link creation *after* the editor scope is closed
    ImInt start = new ImInt();
    ImInt end   = new ImInt();
    if (ImNodes.isLinkCreated(start, end))
    {
      System.out.printf("New link: outPin=%d → inPin=%d%n", start.get(), end.get());
    }
  }
}
