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
                String type = tokens[0].toLowerCase();
                String id = tokens[1];
                
                // Fix: Handle plant types (flower, tree, bush)
                if (type.equals("flower") || type.equals("tree") || type.equals("bush")) {
                    String name = tokens[2];
                    PlantType plantType = PlantType.valueOf(type.toUpperCase());
                    int areaOfSpread = Integer.parseInt(tokens[3]);
                    GardenPlant plant = new GardenPlant(id, name, plantType, areaOfSpread);
                    gardenObjects.add(plant);
                }
                // Fix: Handle light types (small_lamp, large_lamp, spotlight)
                else if (type.equals("small_lamp") || type.equals("large_lamp") || type.equals("spotlight")) {
                    LightType lightType = LightType.valueOf(type.toUpperCase().replace("_", "_"));
                    Color color = Color.valueOf(tokens[2].toUpperCase());
                    int areaOfReach = Integer.parseInt(tokens[3]);
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