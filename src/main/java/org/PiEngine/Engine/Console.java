package org.PiEngine.Engine;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.lwjgl.opengl.GL30.*;

public class Console
{
    public static List<String[]> messages = new ArrayList<>();
    private static final int MAX_MESSAGES = 500; // or whatever limit you want

    static
    {
        Console.log("OpenGL Vendor: " + glGetString(GL_VENDOR));
        Console.log("OpenGL Renderer: " + glGetString(GL_RENDERER));
        Console.log("OpenGL Version: " + glGetString(GL_VERSION));
        Console.log("GLSL Version: " + glGetString(GL_SHADING_LANGUAGE_VERSION));
    }

    private static String getCurrentTime()
    {
        SimpleDateFormat formatter = new SimpleDateFormat("HH:mm:ss");
        return formatter.format(new Date());
    }

    private static String getCallerInfo()
    {
        StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();

        if (stackTrace.length > 3)
        {
            StackTraceElement caller = stackTrace[3];
            String fileName = caller.getFileName();
            int lineNumber = caller.getLineNumber();
            return fileName + ":" + lineNumber;
        }
        return "Unknown";
    }

    private static void addMessage(String time, String message, String type, String caller)
    {
        if (messages.size() >= MAX_MESSAGES)
        {
            messages.remove(0); // Remove oldest message (queue behavior)
        }
        messages.add(new String[] { time, message, type, caller });
    }

    public static void log(String message)
    {
        addMessage(getCurrentTime(), message, "LOG", getCallerInfo());
    }

    public static void warning(String message)
    {
        addMessage(getCurrentTime(), message, "WARNING", getCallerInfo());
    }

    public static void error(String message)
    {
        addMessage(getCurrentTime(), message, "ERROR", getCallerInfo());
    }

    public static void errorClass(String message, String Component)
    {
        addMessage(getCurrentTime(), message, "ERROR", Component);
    }
}
