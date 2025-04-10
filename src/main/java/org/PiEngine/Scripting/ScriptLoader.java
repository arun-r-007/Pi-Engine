package org.PiEngine.Scripting;
import javax.tools.*; 
import java.io.*; 
import java.net.*; 
import java.util.*; 


public class ScriptLoader 
{
    private final File scriptFolder;
    private final File outputFolder;
    private final File engineJar; // Optional: used for classpath

    public ScriptLoader(String scriptPath, String outputPath, String engineJarPath) {
        this.scriptFolder = new File(scriptPath);
        this.outputFolder = new File(outputPath);
        this.engineJar = engineJarPath != null ? new File(engineJarPath) : null;

        if (!outputFolder.exists()) outputFolder.mkdirs();
    }

    public List<Object> loadAndCompileScripts() throws Exception 
    {
        List<Object> loadedComponents = new ArrayList<>();

        File[] javaFiles = scriptFolder.listFiles((f, name) -> name.endsWith(".java"));
        if (javaFiles == null || javaFiles.length == 0) return loadedComponents;

        JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
        if (compiler == null) throw new IllegalStateException("JDK required! JavaCompiler not available.");

        StandardJavaFileManager fileManager = compiler.getStandardFileManager(null, null, null);
        Iterable<? extends JavaFileObject> units = fileManager.getJavaFileObjects(javaFiles);

        List<String> options = new ArrayList<>(Arrays.asList("-d", outputFolder.getAbsolutePath()));
        if (engineJar != null) options.addAll(Arrays.asList("-classpath", engineJar.getAbsolutePath()));

        boolean success = compiler.getTask(null, fileManager, null, options, null, units).call();
        fileManager.close();

        if (!success) 
        {
            throw new RuntimeException("Script compilation failed.");
        }

        URLClassLoader classLoader = new URLClassLoader(
        new URL[] 
        {
            outputFolder.toURI().toURL(),
            new File(".").toURI().toURL() 
        });

        for (File file : javaFiles) 
        {
            String className = file.getName().replace(".java", "");
            Class<?> cls = classLoader.loadClass(className);

            // Check if it extends Component
            if (isComponent(cls)) 
            {
                Object instance = cls.getDeclaredConstructor().newInstance();
                loadedComponents.add(instance);
            }
        }

        classLoader.close();
        return loadedComponents;
    }

    private boolean isComponent(Class<?> cls) 
    {
        try 
        {
            Class<?> componentClass = Class.forName("engine.Component"); // Adjust this based on your engine
            return componentClass.isAssignableFrom(cls);
        } 
        catch (ClassNotFoundException e) 
        {
            return false;
        }
    }
    
}
