package com.gardenpuzzle.util;

import java.util.Random;
import java.util.HashSet;
import java.util.Set;
import com.gardenpuzzle.model.objects.enums.PlantType;
import com.gardenpuzzle.model.objects.enums.Color;

public class GameGoalGenerator {
    private Random random;
    private PlantType[] plantTypes;
    private Color[] colors;

    public GameGoalGenerator() {
        random = new Random();
        plantTypes = PlantType.values();
        colors = Color.values();
    }

    public PlantType[] generatePlantTypes() {
        int count = random.nextInt(3) + 1; // At least one type
        Set<PlantType> selectedTypes = new HashSet<>();
        while (selectedTypes.size() < count) {
            selectedTypes.add(plantTypes[random.nextInt(plantTypes.length)]);
        }
        return selectedTypes.toArray(new PlantType[0]);
    }

    public Color[] generateColors() {
        int includeColors = random.nextBoolean() ? 1 : 0; // Colors can be included or not
        if (includeColors == 0) {
            return new Color[0]; // No colors required
        }
        int count = random.nextInt(3) + 1; // At least one color
        Set<Color> selectedColors = new HashSet<>();
        while (selectedColors.size() < count) {
            selectedColors.add(colors[random.nextInt(colors.length)]);
        }
        return selectedColors.toArray(new Color[0]);
    }

    public String generateGoalSquare() {
        char row = (char) ('A' + random.nextInt(6));
        int column = random.nextInt(8) + 1;
        return row + Integer.toString(column);
    }
}