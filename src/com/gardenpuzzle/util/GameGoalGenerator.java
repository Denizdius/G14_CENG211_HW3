// FILE: com/gardenpuzzle/util/GameGoalGenerator.java
package com.gardenpuzzle.util;

import java.util.Random;
import com.gardenpuzzle.model.objects.enums.PlantType;
import com.gardenpuzzle.model.objects.enums.Color;

public class GameGoalGenerator {
    private Random random;

    public GameGoalGenerator() {
        random = new Random();
    }

    public PlantType generatePlantType() {
        PlantType[] types = PlantType.values();
        return types[random.nextInt(types.length)];
    }

    public Color generateColor() {
        Color[] colors = Color.values();
        return colors[random.nextInt(colors.length)];
    }
}
