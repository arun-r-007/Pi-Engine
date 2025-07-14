package org.PiEngine.Utils;

import org.PiEngine.Component.Component;
import org.reflections.Reflections;

import java.util.*;
import java.util.function.Supplier;

/**
 * Factory class for creating and managing component instances.
 * Handles dynamic component registration, creation, and lookup.
 */
public class ComponentFactory 
{
    /** Map of component constructors by name */
    private static final Map<String, Supplier<Component>> componentConstructors = new HashMap<>();

    static 
    {
        registerComponentsFromPackage("org.PiEngine.Component");
    }

    /**
     * Scans a package for Component subclasses and registers them.
     * @param basePackage The base package to scan
     */
    public static void registerComponentsFromPackage(String basePackage) 
    {
        Reflections reflections = new Reflections(basePackage);
        Set<Class<? extends Component>> componentClasses = reflections.getSubTypesOf(Component.class);

        for (Class<? extends Component> compClass : componentClasses) 
        {
            try 
            {
                compClass.getConstructor();
                registerComponent(compClass);
            } 
            catch (NoSuchMethodException e) 
            {
                System.out.println("Skipping " + compClass.getSimpleName() + ": No public no-arg constructor.");
            }
        }
    }

    /**
     * Registers a single component class.
     * @param compClass The component class to register
     */
    public static void registerComponent(Class<? extends Component> compClass) 
    {
        componentConstructors.put(compClass.getSimpleName(), () -> {
            try {
                return compClass.getConstructor().newInstance();
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        });
    }

    /**
     * Registers a component constructor with a name.
     * @param name The name to register
     * @param constructor The constructor supplier
     */
    public static void register(String name, Supplier<Component> constructor)
    {
        if (isRegistered(name))
        {
            System.out.println("Re-registering component: " + name);
            componentConstructors.remove(name);
        }
        componentConstructors.put(name, constructor);
    }

    /**
     * Creates a component instance by name.
     * @param name The name of the component to create
     * @return The created component, or null if not found
     */
    public static Component create(String name) 
    {
        Supplier<Component> constructor = componentConstructors.get(name);
        return constructor != null ? constructor.get() : null;
    }

    /**
     * Checks if a component type is registered.
     * @param name The name to check
     * @return True if registered, false otherwise
     */
    public static boolean isRegistered(String name) 
    {
        return componentConstructors.containsKey(name);
    }

    /**
     * Gets all registered component names.
     * @return Set of registered component names
     */
    public static Set<String> getRegisteredComponentNames() 
    {
        return componentConstructors.keySet();
    }

    /**
     * Gets the class type of a component.
     * @param name The component name
     * @return The component class, or null if not found
     */
    public static Class<? extends Component> GetClass(String name) 
    {
        Supplier<Component> supplier = componentConstructors.get(name);
        if (supplier != null) 
        {
            Component component = supplier.get();
            return component.getClass();
        } 
        return null;
    }

    /**
     * Creates a component by type ID (not implemented).
     * @param typeId The type ID
     * @return The created component
     */
    public static Component createComponent(int typeId) 
    {
        throw new UnsupportedOperationException("Unimplemented method 'createComponent'");
    }

    /**
     * Clears all registered components.
     */
    public static void Clear()
    {
        componentConstructors.clear();
        System.gc();
    }
}
