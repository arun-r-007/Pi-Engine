package org.PiEngine.Editor.Serialization;

import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.List;

public class SerializeFieldConstructors {

    // A static list to store the constructors
    private static final List<Constructor<?>> constructorsList = new ArrayList<>();

    static {
        // Add constructors for specific field classes to the list
        addConstructors(IntField.class);
        addConstructors(StringField.class);
        addConstructors(FloatField.class);
    }

    /**
     * Adds the constructors of a specific class to the static list.
     */
    public static void addConstructors(Class<?> clazz) {
        Constructor<?>[] constructors = clazz.getDeclaredConstructors();
        for (Constructor<?> constructor : constructors) {
            constructorsList.add(constructor);
            System.out.println("Added Constructor: " + constructor);
        }
    }

    /**
     * Get the list of constructors.
     */
    public static List<Constructor<?>> getConstructorsList() {
        return constructorsList;
    }

    /**
     * Example usage - print all constructors stored in the list.
     */
    public static void printAllConstructors() {
        System.out.println("List of all constructors:");
        for (Constructor<?> constructor : constructorsList) {
            System.out.println(constructor);
        }
    }
}
