package com.gardenpuzzle.model.garden;

import com.gardenpuzzle.model.objects.enums.Color;
import com.gardenpuzzle.model.objects.enums.PlantType;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class PollenCloud {
    private List<PlantType> plantTypes;
    private List<Color> colors;

    public PollenCloud() {
        this.plantTypes = new ArrayList<>();
        this.colors = new ArrayList<>();
    }

    public void addPlantType(PlantType type) {
        if (!plantTypes.contains(type)) {
            plantTypes.add(type);
        }
    }

    public void addColor(Color color) {
        if (!colors.contains(color)) {
            colors.add(color);
        }
    }

    public List<PlantType> getPlantTypes() {
        return new ArrayList<>(plantTypes);
    }

    public List<Color> getColors() {
        return new ArrayList<>(colors);
    }

    @Override
    public String toString() {
        return "PollenCloud{" +
               "plantTypes=" + plantTypes +
               ", colors=" + colors +
               '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PollenCloud that = (PollenCloud) o;
        return Objects.equals(plantTypes, that.plantTypes) &&
               Objects.equals(colors, that.colors);
    }

    @Override
    public int hashCode() {
        return Objects.hash(plantTypes, colors);
    }
}