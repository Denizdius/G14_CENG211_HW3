package com.gardenpuzzle.model.garden;

import java.util.HashSet;
import java.util.Set;
import com.gardenpuzzle.model.objects.enums.Color;
import com.gardenpuzzle.model.objects.enums.PlantType;

public class PollenCloud {
    private Set<Color> colors;
    private Set<PlantType> plantTypes;

    public PollenCloud() {
        colors = new HashSet<>();
        plantTypes = new HashSet<>();
    }

    public void addColor(Color color) {
        colors.add(color);
    }

    public void addPlantType(PlantType type) {
        plantTypes.add(type);
    }

    public Set<Color> getColors() {
        return colors;
    }

    public Set<PlantType> getPlantTypes() {
        return plantTypes;
    }
}