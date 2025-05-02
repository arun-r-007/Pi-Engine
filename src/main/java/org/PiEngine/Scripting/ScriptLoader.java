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
                instance = new ScriptLoader("src/main/resources/Compiled/Scripts");
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

    public void loadComponentScript(File Script) 
    {        
        File[] classFiles = {Script};
        for (File file : classFiles) 
        {
            System.out.println(file.toPath());   
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
            }
        }
    }


    public void loadComponentFolder(File Script) 
    {        
        File[] classFiles = Script.listFiles((dir, name) -> name.endsWith(".class"));
        if (classFiles == null) return;
        for (File file : classFiles) 
        {
            System.out.println(file.toPath());   
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
