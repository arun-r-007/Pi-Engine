package org.PiEngine.Scripting;

import java.io.File;
import java.net.URL;
import java.net.URLClassLoader;

import org.PiEngine.Component.Component;
import org.PiEngine.Component.ComponentFactory;

public class ScriptLoader 
{
    private static ScriptLoader instance;

    private final File rootDirectory;
    private final URLClassLoader urlClassLoader;

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

    public Class<?> loadClass(String fullyQualifiedName) throws ClassNotFoundException 
    {
        return urlClassLoader.loadClass(fullyQualifiedName);
    }

    public void close() 
    {
        try 
        {
            urlClassLoader.close();
        } catch (Exception e) 
        {
            e.printStackTrace();
        }
    }

    public void loadComponentScripts(String folderPath) 
    {
        File componentDir = new File(folderPath);
        if (!componentDir.exists() || !componentDir.isDirectory()) return;

        File[] classFiles = componentDir.listFiles((dir, name) -> name.endsWith(".class"));
        if (classFiles == null) return;

        for (File file : classFiles) 
        {
            String className = file.getName().replace(".class", "");
            String fullClassName = "Scripts." + className;

            try 
            {
                Class<?> scriptClass = loadClass(fullClassName);

                if (Component.class.isAssignableFrom(scriptClass)) 
                {
                    ComponentFactory.register(scriptClass.getSimpleName(), () -> 
                    {
                        try 
                        {
                            return (Component) scriptClass.getDeclaredConstructor().newInstance();
                        } 
                        catch (Exception e) 
                        {
                            e.printStackTrace();
                            return null;
                        }
                    });
                    System.out.println("Loaded & registered component: " + fullClassName);
                } 
                else 
                {
                    System.out.println("Skipped (not a Component): " + fullClassName);
                }
            } 
            catch (Exception e) 
            {
                System.err.println("Failed to load: " + fullClassName);
                e.printStackTrace();
            }
        }
    }

    public void loadSystemScripts(String folderPath) 
    {
    }

    public void loadBehaviorScripts(String folderPath) 
    {
    }
}
