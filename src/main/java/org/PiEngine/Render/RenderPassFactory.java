package org.PiEngine.Render;

import org.PiEngine.Engine.Console;
import org.reflections.Reflections;

import java.lang.reflect.InvocationTargetException;
import java.util.*;
import java.util.function.Supplier;

public class RenderPassFactory {

    private static final Map<String, Supplier<RenderPass>> passConstructors = new HashMap<>();

    static 
    {
        registerRenderPassesFromPackage("org.PiEngine.Render.Passes");
    }

    /**
     * Scans a package and registers all RenderPass subclasses.
     * @param basePackage The base package to scan
     */
    public static void registerRenderPassesFromPackage(String basePackage) {
        Reflections reflections = new Reflections(basePackage);

        Set<Class<? extends RenderPass>> passClasses = reflections.getSubTypesOf(RenderPass.class);

        for (Class<? extends RenderPass> passClass : passClasses) {
            try {
                passClass.getConstructor(); // Make sure there's a no-arg constructor
                registerRenderPass(passClass);
            } catch (NoSuchMethodException e) {
                Console.log("Skipping " + passClass.getSimpleName() + ": No public no-arg constructor.");
            }
        }
    }

    /**
     * Manually register a single RenderPass class.
     * @param passClass The RenderPass class to register
     */
    public static void registerRenderPass(Class<? extends RenderPass> passClass) {
        passConstructors.put(passClass.getSimpleName(), () -> {
            try {
                return passClass.getConstructor().newInstance();
            } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
                e.printStackTrace();
                return null;
            }
        });
    }

    /**
     * Gets all registered RenderPass names.
     * @return Set of registered pass names
     */
    public static Set<String> getRegisteredRenderPassNames() {
        return passConstructors.keySet();
    }

    /**
     * Creates a new instance of a registered RenderPass by name.
     * @param name The name of the pass
     * @return The new RenderPass instance
     */
    public static RenderPass create(String name) {
        Supplier<RenderPass> constructor = passConstructors.get(name);
        return constructor != null ? constructor.get() : null;
    }

    /**
     * Checks if a RenderPass is registered.
     * @param name The name of the pass
     * @return True if registered, false otherwise
     */
    public static boolean isRegistered(String name) {
        return passConstructors.containsKey(name);
    }

    /**
     * Registers a RenderPass constructor by name.
     * @param name The name of the pass
     * @param constructor The constructor supplier
     */
    public static void register(String name, Supplier<RenderPass> constructor) {
        passConstructors.put(name, constructor);
    }
}
