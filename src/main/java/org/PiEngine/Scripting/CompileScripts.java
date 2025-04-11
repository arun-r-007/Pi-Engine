package org.PiEngine.Scripting;

import javax.tools.*;
import java.io.*;
import java.util.*;

public class CompileScripts 
{
    private static CompileScripts instance;

    private final File scriptFolder;
    private final File outputFolder;
    private final File engineJar;

    private CompileScripts(String scriptPath, String outputPath, String engineJarPath) 
    {
        this.scriptFolder = new File(scriptPath);
        this.outputFolder = new File(outputPath);
        this.engineJar = (engineJarPath != null) ? new File(engineJarPath) : null;

        if (!outputFolder.exists()) outputFolder.mkdirs();
    }

    public static CompileScripts getInstance(String scriptPath, String outputPath, String engineJarPath) 
    {
        if (instance == null) 
        {
            instance = new CompileScripts(scriptPath, outputPath, engineJarPath);
        }
        return instance;
    }

    public void compileScripts() throws Exception 
    {
        File[] javaFiles = scriptFolder.listFiles((f, name) -> name.endsWith(".java"));
        if (javaFiles == null || javaFiles.length == 0) return;

        JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
        if (compiler == null) throw new IllegalStateException("JDK required! JavaCompiler not available.");

        StandardJavaFileManager fileManager = compiler.getStandardFileManager(null, null, null);
        Iterable<? extends JavaFileObject> units = fileManager.getJavaFileObjects(javaFiles);

        List<String> options = new ArrayList<>(Arrays.asList("-d", outputFolder.getAbsolutePath()));
        if (engineJar != null) 
        {
            options.addAll(Arrays.asList("-classpath", engineJar.getAbsolutePath()));
        }

        boolean success = compiler.getTask(null, fileManager, null, options, null, units).call();
        fileManager.close();

        if (!success) throw new RuntimeException("Script compilation failed.");

        for (File file : javaFiles) 
        {
            System.out.println(file.getName() + " script is compiled");
        }
    }


    public void compileSystems() 
    {
        // Placeholder for compiling system classes later
    }
}
