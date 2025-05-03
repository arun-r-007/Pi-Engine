package org.PiEngine.Utils;

import org.PiEngine.Component.Component;
import org.reflections.Reflections;

import java.util.*;
import java.util.function.Supplier;

public class ComponentFactory 
{
    private static final Map<String, Supplier<Component>> componentConstructors = new HashMap<>();

    static 
    {
        registerComponentsFromPackage("org.PiEngine.Component");
    }

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

    public static void register(String name, Supplier<Component> constructor)
    {
        if (isRegistered(name))
        {
            System.out.println("Re-registering component: " + name);
            componentConstructors.remove(name);
        }
        componentConstructors.put(name, constructor);
    }

    public static Component create(String name) 
    {
        Supplier<Component> constructor = componentConstructors.get(name);
        return constructor != null ? constructor.get() : null;
    }

    public static boolean isRegistered(String name) 
    {
        return componentConstructors.containsKey(name);
    }

    public static Set<String> getRegisteredComponentNames() 
    {
        return componentConstructors.keySet();
    }

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

    public static Component createComponent(int typeId) 
    {
        throw new UnsupportedOperationException("Unimplemented method 'createComponent'");
    }

    public static void Clear()
    {
        componentConstructors.clear();
        System.gc();
    }
}
