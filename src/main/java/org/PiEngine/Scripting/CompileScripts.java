package org.PiEngine.Scripting;

import javax.tools.*;
import java.io.*;
import java.util.*;

import org.PiEngine.Engine.Console;

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

    public static CompileScripts getInstance()
    {
        if (instance == null)
        {
            throw new IllegalStateException("CompileScripts has not been initialized yet.");
        }
        return instance;
    }

    public void compileScripts() throws Exception
    {
        File[] javaFiles = scriptFolder.listFiles((f, name) -> name.endsWith(".java"));
        if (javaFiles == null || javaFiles.length == 0) return;

        JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
        if (compiler == null) throw new IllegalStateException("JDK required! JavaCompiler not available.");

        DiagnosticCollector<JavaFileObject> diagnostics = new DiagnosticCollector<>();
        StandardJavaFileManager fileManager = compiler.getStandardFileManager(diagnostics, null, null);

        Iterable<? extends JavaFileObject> units = fileManager.getJavaFileObjects(javaFiles);

        List<String> options = new ArrayList<>(Arrays.asList("-d", outputFolder.getAbsolutePath()));
        if (engineJar != null)
        {
            options.addAll(Arrays.asList("-classpath", engineJar.getAbsolutePath()));
        }

        boolean success = compiler.getTask(null, fileManager, diagnostics, options, null, units).call();
        fileManager.close();

        if (!success)
        {
            for (Diagnostic<? extends JavaFileObject> diagnostic : diagnostics.getDiagnostics())
            {
                String errorMsg = diagnostic.getSource() != null
                        ? diagnostic.getSource().getName() + ":" + diagnostic.getLineNumber() + " - " + diagnostic.getMessage(null)
                        : "Unknown source - " + diagnostic.getMessage(null);

                Console.error(errorMsg);
            }
        }

        for (File file : javaFiles)
        {
            if (success)
                Console.log(file.getName() + ": script compiled successfully.");
            else
                Console.warning(file.getName() + ": script compilation had errors.");
        }
    }

    public void compileScript(File scriptFile) throws Exception
    {
        if (scriptFile == null || !scriptFile.exists() || !scriptFile.getName().endsWith(".java"))
        {
            Console.error("Invalid script file: " + scriptFile);
            return;
        }

        JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
        if (compiler == null) throw new IllegalStateException("JDK required! JavaCompiler not available.");

        DiagnosticCollector<JavaFileObject> diagnostics = new DiagnosticCollector<>();
        StandardJavaFileManager fileManager = compiler.getStandardFileManager(diagnostics, null, null);

        Iterable<? extends JavaFileObject> units = fileManager.getJavaFileObjects(scriptFile);

        List<String> options = new ArrayList<>(Arrays.asList("-d", outputFolder.getAbsolutePath()));
        if (engineJar != null)
        {
            options.addAll(Arrays.asList("-classpath", engineJar.getAbsolutePath()));
        }

        boolean success = compiler.getTask(null, fileManager, diagnostics, options, null, units).call();
        fileManager.close();

        if (success)
        {
            Console.log(scriptFile.getName() + ": script compiled successfully.");
        }
        else
        {
            for (Diagnostic<? extends JavaFileObject> diagnostic : diagnostics.getDiagnostics())
            {
                String errorMsg = diagnostic.getSource() != null
                        ? diagnostic.getSource().getName() + ":" + diagnostic.getLineNumber() + " - " + diagnostic.getMessage(null)
                        : "Unknown source - " + diagnostic.getMessage(null);
                Console.error(errorMsg);
            }
            Console.warning(scriptFile.getName() + ": script compilation had errors.");
        }
    }

    public void compileSystems()
    {
        // Placeholder for compiling system classes later
    }
}
