package org.PiEngine.Scripting;

import java.io.File;
import java.net.URL;
import java.net.URLClassLoader;

public class CompiledScriptClassLoader
{
    private final File rootDirectory;
    private final URLClassLoader urlClassLoader;

    public CompiledScriptClassLoader(String compiledOutputPath) throws Exception
    {
        this.rootDirectory = new File(compiledOutputPath);
        if (!rootDirectory.exists())
        {
            throw new IllegalArgumentException("Compiled directory does not exist: " + compiledOutputPath);
        }

        URL[] urls = new URL[] { rootDirectory.toURI().toURL() };
        this.urlClassLoader = new URLClassLoader(urls, getClass().getClassLoader());
    }

    public Class<?> loadClass(String fullyQualifiedName) throws ClassNotFoundException
    {
        return urlClassLoader.loadClass(fullyQualifiedName);
    }

    public void close() throws Exception
    {
        urlClassLoader.close();
    }
}