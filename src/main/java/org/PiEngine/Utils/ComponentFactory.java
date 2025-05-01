package org.PiEngine.Utils;

import org.PiEngine.Component.Component;
import org.reflections.Reflections;

import java.lang.reflect.InvocationTargetException;
import java.util.*;
import java.util.function.Supplier;

public class ComponentFactory {

    private static final Map<String, Supplier<Component>> componentConstructors = new HashMap<>();

    static {
        // Scan the base package for built-in components
        registerComponentsFromPackage("org.PiEngine.Component");
    }

    /**
     * Scans a package and registers all Component subclasses.
     */
    public static void registerComponentsFromPackage(String basePackage) {
        Reflections reflections = new Reflections(basePackage);

        Set<Class<? extends Component>> componentClasses = reflections.getSubTypesOf(Component.class);

        for (Class<? extends Component> compClass : componentClasses) {
            try {
                compClass.getConstructor(); // Make sure there's a no-arg constructor
                registerComponent(compClass);
            } catch (NoSuchMethodException e) {
                System.out.println("Skipping " + compClass.getSimpleName() + ": No public no-arg constructor.");
            }
        }
    }

    /**
     * Manually register a single class.
     */
    public static void registerComponent(Class<? extends Component> compClass) {
        componentConstructors.put(compClass.getSimpleName(), () -> {
            try {
                return compClass.getConstructor().newInstance();
            } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
                e.printStackTrace();
                return null;
            }
        });
    }

    /**
     * Get a list of all registered component names.
     */
    public static Set<String> getRegisteredComponentNames() {
        return componentConstructors.keySet();
    }

    /**
     * Create a new instance of a registered component by name.
     */
    public static Component create(String name) {
        Supplier<Component> constructor = componentConstructors.get(name);
        return constructor != null ? constructor.get() : null;
    }

    /**
     * Check if a component is registered.
     */
    public static boolean isRegistered(String name) {
        return componentConstructors.containsKey(name);
    }

    public static void register(String name, Supplier<Component> constructor)
    {
        componentConstructors.put(name, constructor);
    }

    public static Class<? extends Component> GetClass(String name) 
    {
        
        Supplier<Component> supplier = componentConstructors.get(name);
        
        if (supplier != null) {
            Component component = supplier.get();
            return component.getClass();
        } 
        else 
        {
            return null;
        }
    }

    public static Component createComponent(int typeId) 
    {
        throw new UnsupportedOperationException("Unimplemented method 'createComponent'");
    }

}
