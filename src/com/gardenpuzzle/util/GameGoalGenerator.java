package com.gardenpuzzle.util;

import java.util.Random;
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
        int count = random.nextInt(3) + 1;
        PlantType[] selectedTypes = new PlantType[count];
        for (int i = 0; i < count; i++) {
            selectedTypes[i] = plantTypes[random.nextInt(plantTypes.length)];
        }
        return selectedTypes;
    }

    public Color[] generateColors() {
        int count = random.nextInt(3) + 1;
        Color[] selectedColors = new Color[count];
        for (int i = 0; i < count; i++) {
            selectedColors[i] = colors[random.nextInt(colors.length)];
        }
        return selectedColors;
    }

    public String generateGoalSquare() {
        char row = (char) ('A' + random.nextInt(6));
        int column = random.nextInt(8) + 1;
        return row + Integer.toString(column);
    }
}