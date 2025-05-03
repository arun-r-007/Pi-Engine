package org.PiEngine.Scripting;

import java.io.File;
import java.net.URL;
import java.net.URLClassLoader;

import org.PiEngine.Component.Component;
import org.PiEngine.Engine.Console;
import org.PiEngine.Utils.ComponentFactory;

public class ScriptLoader 
{
    private static ScriptLoader instance;

    private File rootDirectory;
    private URLClassLoader urlClassLoader;

    private ScriptLoader(String compiledOutputPath) throws Exception 
    {
        this.rootDirectory = new File(compiledOutputPath);

        if (!rootDirectory.exists()) 
        {
            throw new IllegalArgumentException("Compiled directory does not exist: " + compiledOutputPath);
        }

        URL[] urls = new URL[]{ rootDirectory.toURI().toURL() };
        this.urlClassLoader = new URLClassLoader(urls, getClass().getClassLoader());
    }

    public static ScriptLoader getInstance() 
    {
        if (instance == null) 
        {
            try 
            {
                instance = new ScriptLoader("Compiled");
            } 
            catch (Exception e) 
            {
                throw new RuntimeException("Failed to initialize ScriptLoader", e);
            }
        }
        return instance;
    }

    public static void reset() 
    {
        if (instance != null) 
        {
            ComponentFactory.Clear();  
            instance.close();
            instance.urlClassLoader = null;
            instance = null;
        }
    }


    public Class<?> loadClass(String fullyQualifiedName) throws ClassNotFoundException 
    {
        return urlClassLoader.loadClass(fullyQualifiedName);
    }

    public void close() 
    {
        try 
        {
            urlClassLoader.close();
        } 
        catch (Exception e) 
        {
            e.printStackTrace();
        }
    }

    private void loadClassAndRegister(String fullClassName)
    {
        try 
        {
            Class<?> scriptClass = loadClass(fullClassName);

            if (Component.class.isAssignableFrom(scriptClass)) 
            {
                ComponentFactory.register(scriptClass.getSimpleName(), () -> {
                    try {
                        return (Component) scriptClass.getDeclaredConstructor().newInstance();
                    } catch (Exception e) {
                        e.printStackTrace();
                        return null;
                    }
                });

                Console.log("Loaded & registered component: " + fullClassName);
            } 
            else 
            {
                Console.warning("Skipped (not a Component): " + fullClassName);
            }
        } 
        catch (Exception e) 
        {
            Console.error("Failed to load: " + fullClassName);
            e.printStackTrace();
        }
    }

    public void loadComponentScript(File scriptFile) 
    {
        String className = scriptFile.getName().replace(".class", "");
        String fullClassName = "Scripts." + className;
        loadClassAndRegister(fullClassName);
    }

    public void loadComponentFolder(String folderPath) 
    {
        File componentDir = new File(folderPath);
        if (!componentDir.exists() || !componentDir.isDirectory()) return;

        File[] classFiles = componentDir.listFiles((dir, name) -> name.endsWith(".class"));
        if (classFiles == null) return;

        for (File file : classFiles) 
        {
            String className = file.getName().replace(".class", "");
            String fullClassName = "Scripts." + className;
            loadClassAndRegister(fullClassName);
        }
    }

    public void loadSystemScripts(String folderPath) { }
    public void loadBehaviorScripts(String folderPath) { }

    @Override
    protected void finalize() throws Throwable
    {
        System.out.println("Removed: " + this);
    }
}
