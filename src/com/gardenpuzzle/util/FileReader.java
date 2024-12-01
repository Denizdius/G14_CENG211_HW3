package com.gardenpuzzle.util;

import java.util.List;
import java.util.ArrayList;
import java.nio.file.Paths;
import java.util.Scanner;
import com.gardenpuzzle.model.objects.GardenObject;
import com.gardenpuzzle.model.objects.GardenPlant;
import com.gardenpuzzle.model.objects.LightSource;
import com.gardenpuzzle.model.objects.enums.PlantType;
import com.gardenpuzzle.model.objects.enums.LightType;
import com.gardenpuzzle.model.objects.enums.Color;

public class FileReader {
    public List<GardenObject> readGardenObjects(String filePath) {
        List<GardenObject> gardenObjects = new ArrayList<>();
        try (Scanner scanner = new Scanner(Paths.get(filePath))) {
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                String[] tokens = line.split(",");
                String type = tokens[0];
                if (type.equalsIgnoreCase("Plant")) {
                    String id = tokens[1];
                    String name = tokens[2];
                    PlantType plantType = PlantType.valueOf(tokens[3].toUpperCase());
                    int areaOfSpread = Integer.parseInt(tokens[4]);
                    GardenPlant plant = new GardenPlant(id, name, plantType, areaOfSpread);
                    gardenObjects.add(plant);
                } else if (type.equalsIgnoreCase("Light")) {
                    String id = tokens[1];
                    LightType lightType = LightType.valueOf(tokens[2].toUpperCase().replace(" ", "_"));
                    Color color = Color.valueOf(tokens[3].toUpperCase());
                    int areaOfReach = Integer.parseInt(tokens[4]);
                    LightSource light = new LightSource(id, lightType, color, areaOfReach);
                    gardenObjects.add(light);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return gardenObjects;
    }
}