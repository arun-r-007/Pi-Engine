package org.PiEngine.Engine;

import com.google.gson.*;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

import org.PiEngine.Component.*;
import org.PiEngine.GameObjects.*;
import org.PiEngine.Math.Vector;

public class SceneSerializerJSON {

    public static void serialize(Scene scene, String filePath) throws IOException {
        Gson gson = new GsonBuilder().disableHtmlEscaping().setPrettyPrinting().create();
        // TDO: need to remove setPrettyPrinting for faster and compact file size

        Map<String, Object> sceneData = new HashMap<>();
        sceneData.put("sceneName", scene.getName());
        sceneData.put("GameCamera", GameObject.Location(scene.getGameCamera()));

        List<Object> gameObjects = new ArrayList<>();
        for (Transform child : scene.getRoot().transform.getChildren()) {
            serializeGameObject(child.getGameObject(), gameObjects);
        }
        sceneData.put("gameObjects", gameObjects);

        try (FileWriter writer = new FileWriter(filePath)) {
            gson.toJson(sceneData, writer);
        }
    }

    private static void serializeGameObject(GameObject gameObject, List<Object> outputList) {
        Map<String, Object> objData = new HashMap<>();
        objData.put("id", gameObject.getId());
        objData.put("name", gameObject.getName());
        objData.put("layer", gameObject.getLayerBit());
        objData.put("Location", GameObject.Location(gameObject));

        
        
        objData.put("position", vectorToMap(gameObject.transform.getLocalPosition()));
        objData.put("rotation", vectorToMap(gameObject.transform.getLocalRotation()));
        objData.put("scale", vectorToMap(gameObject.transform.getLocalScale()));
        
        List<Object> components = new ArrayList<>();
        for (Component component : gameObject.getComponents()) {
            Map<String, Object> compData = new HashMap<>();
            compData.put("id", component.getId());
            compData.put("name", component.getClass().getSimpleName());

            // Copy only safe properties
            Map<String, Object> safeProperties = new HashMap<>();
            for (Map.Entry<String, Object> entry : component.getProperties().entrySet()) {
                Object value = entry.getValue();
                if (isSimpleType(value)) {
                    safeProperties.put(entry.getKey(), value);
                } else if (value instanceof Vector) {
                    safeProperties.put(entry.getKey(), vectorToMap((Vector)value));
                }
                // else ignore complex objects
            }

            compData.put("properties", safeProperties);
            components.add(compData);
        }
        objData.put("components", components);

        List<Object> children = new ArrayList<>();
        for (Transform child : gameObject.transform.getChildren()) {
            if (child != null) {
                serializeGameObject(child.getGameObject(), children);
            }
        }
        objData.put("children", children);

        outputList.add(objData);
    }

    private static Map<String, Float> vectorToMap(Vector vector) {
        Map<String, Float> map = new HashMap<>();
        map.put("x", vector.getX());
        map.put("y", vector.getY());
        map.put("z", vector.getZ());
        return map;
    }

    private static boolean isSimpleType(Object value) {
        return value instanceof String || value instanceof Number || value instanceof Boolean;
    }
}
