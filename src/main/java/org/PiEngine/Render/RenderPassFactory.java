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
     * Get a list of all registered RenderPass names.
     */
    public static Set<String> getRegisteredRenderPassNames() {
        return passConstructors.keySet();
    }

    /**
     * Create a new instance of a registered RenderPass by name.
     */
    public static RenderPass create(String name) {
        Supplier<RenderPass> constructor = passConstructors.get(name);
        return constructor != null ? constructor.get() : null;
    }

    /**
     * Check if a RenderPass is registered.
     */
    public static boolean isRegistered(String name) {
        return passConstructors.containsKey(name);
    }

    public static void register(String name, Supplier<RenderPass> constructor) {
        passConstructors.put(name, constructor);
    }
}
