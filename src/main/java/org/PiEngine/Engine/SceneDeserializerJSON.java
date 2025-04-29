package org.PiEngine.Engine;

import com.google.gson.*;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

import org.PiEngine.Component.*;
import org.PiEngine.GameObjects.*;
import org.PiEngine.Math.Vector;

public class SceneDeserializerJSON {

    public static Scene deserialize(String filePath) throws IOException {
        // Gson gson = new Gson();

        JsonObject jsonObject = JsonParser.parseReader(new FileReader(filePath)).getAsJsonObject();

        String sceneName = jsonObject.get("sceneName").getAsString();

        Scene scene = Scene.getInstance();
        scene.getGameCamera();
        scene.setRoot(new GameObject(sceneName));
        scene.getRoot().addChild(Scene.getInstance().getGameCamera());

        JsonArray gameObjectsArray = jsonObject.getAsJsonArray("gameObjects");
        for (JsonElement gameObjectElement : gameObjectsArray) {
            GameObject gameObject = deserializeGameObject(gameObjectElement.getAsJsonObject());
            scene.getRoot().addChild(gameObject); 
        }

        return scene;
    }

    private static GameObject deserializeGameObject(JsonObject jsonObject) {
        int id = jsonObject.get("id").getAsInt();  
        String name = jsonObject.get("name").getAsString();
        int layer = jsonObject.get("layer").getAsInt();
        
        

        GameObject gameObject = new GameObject(name);
        gameObject.setId(id);
        gameObject.setLayer(layer);

        // Deserialize the position, rotation, and scale
        JsonObject position = jsonObject.getAsJsonObject("position");
        Vector pos = new Vector(position.get("x").getAsFloat(), position.get("y").getAsFloat(), position.get("z").getAsFloat());
        gameObject.transform.setLocalPosition(pos);

        JsonObject rotation = jsonObject.getAsJsonObject("rotation");
        Vector rot = new Vector(rotation.get("x").getAsFloat(), rotation.get("y").getAsFloat(), rotation.get("z").getAsFloat());
        gameObject.transform.setLocalRotation(rot);

        JsonObject scale = jsonObject.getAsJsonObject("scale");
        Vector sca = new Vector(scale.get("x").getAsFloat(), scale.get("y").getAsFloat(), scale.get("z").getAsFloat());
        gameObject.transform.setLocalScale(sca);

        JsonArray componentsArray = jsonObject.getAsJsonArray("components");
        for (JsonElement componentElement : componentsArray) {
            JsonObject componentObject = componentElement.getAsJsonObject();
            String componentName = componentObject.get("name").getAsString();

            Component component = ComponentFactory.create(componentName);
            if (component != null) {
                // Deserialize properties
                JsonObject properties = componentObject.getAsJsonObject("properties");
                for (Map.Entry<String, JsonElement> entry : properties.entrySet()) {
                    String propertyName = entry.getKey();
                    JsonElement propertyValue = entry.getValue();
                    component.setComponentProperty(propertyName, propertyValue); 
                }

                gameObject.addComponent(component);
            }
        }

        JsonArray childrenArray = jsonObject.getAsJsonArray("children");
        for (JsonElement childElement : childrenArray) {
            GameObject child = deserializeGameObject(childElement.getAsJsonObject());
            gameObject.transform.addChild(child.transform);
        }

        return gameObject;
    }
}
