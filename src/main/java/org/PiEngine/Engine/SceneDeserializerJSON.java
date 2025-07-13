package org.PiEngine.Engine;

import com.google.gson.*;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

import org.PiEngine.Component.*;
import org.PiEngine.GameObjects.*;
import org.PiEngine.Math.Vector;
import org.PiEngine.Utils.ComponentFactory;

public class SceneDeserializerJSON {

    // This holds GameObject → List of (Component, properties)
    private static final Map<GameObject, List<Pair>> deferredComponentMap = new HashMap<>();

    private static class Pair
    {
        Component component;
        JsonObject properties;

        Pair(Component c, JsonObject p)
        {
            component = c;
            properties = p;
        }
    }

    /**
     * Loads a scene from a JSON file.
     * @param filePath The file path to load from
     * @return The loaded Scene
     * @throws IOException If an I/O error occurs
     */
    public static Scene deserialize(String filePath) throws IOException {
        JsonObject jsonObject = JsonParser.parseReader(new FileReader(filePath)).getAsJsonObject();

        String sceneName = jsonObject.get("sceneName").getAsString();
        String GameCamPath = jsonObject.get("GameCamera").getAsString();



        Scene scene = Scene.getInstance();
        scene.setRoot(new GameObject(sceneName));
        

        JsonArray gameObjectsArray = jsonObject.getAsJsonArray("gameObjects");
        for (JsonElement gameObjectElement : gameObjectsArray) {
            GameObject gameObject = deserializeGameObject(gameObjectElement.getAsJsonObject());
            scene.getRoot().addChild(gameObject); 
        }

        // After all GameObjects are added, set component properties
        for (Map.Entry<GameObject, List<Pair>> entry : deferredComponentMap.entrySet()) {
            for (Pair pair : entry.getValue()) {
                for (Map.Entry<String, JsonElement> prop : pair.properties.entrySet()) {
                    pair.component.setComponentProperty(prop.getKey(), prop.getValue());
                }
            }
        }

        Scene.getInstance().setGameCamera(GameObject.findGameObject(GameCamPath, Scene.getInstance().getRoot()));

        deferredComponentMap.clear();
        scene.getRoot().UpdateLocation();
        return scene;
    }

    /**
     * Loads a game object and its children from JSON.
     * @param jsonObject The JSON object to parse
     * @return The loaded GameObject
     */
    private static GameObject deserializeGameObject(JsonObject jsonObject) {
        int id = jsonObject.get("id").getAsInt();  
        String name = jsonObject.get("name").getAsString();
        int layer = jsonObject.get("layer").getAsInt();

        GameObject gameObject = new GameObject(name);
        gameObject.setId(id);
        gameObject.setLayer(layer);

        // Deserialize transform
        gameObject.transform.setLocalPosition(parseVector(jsonObject.getAsJsonObject("position")));
        gameObject.transform.setLocalRotation(parseVector(jsonObject.getAsJsonObject("rotation")));
        gameObject.transform.setLocalScale(parseVector(jsonObject.getAsJsonObject("scale")));
        
        JsonArray childrenArray = jsonObject.getAsJsonArray("children");
        for (JsonElement childElement : childrenArray) {
            GameObject child = deserializeGameObject(childElement.getAsJsonObject());
            gameObject.addChild(child);
        }
        // Components (defer property setting)
        JsonArray componentsArray = jsonObject.getAsJsonArray("components");
        List<Pair> componentList = new ArrayList<>();
        for (JsonElement componentElement : componentsArray) {
            JsonObject componentObject = componentElement.getAsJsonObject();
            String componentName = componentObject.get("name").getAsString();

            Component component = ComponentFactory.create(componentName);
            if (component != null) {
                gameObject.addComponent(component);
                JsonObject properties = componentObject.getAsJsonObject("properties");
                componentList.add(new Pair(component, properties));
            }
        }
        deferredComponentMap.put(gameObject, componentList);

        // Children

        return gameObject;
    }

    /**
     * Parses a vector from JSON.
     * @param obj The JSON object
     * @return The parsed Vector
     */
    private static Vector parseVector(JsonObject obj)
    {
        return new Vector(obj.get("x").getAsFloat(), obj.get("y").getAsFloat(), obj.get("z").getAsFloat());
    }
}
