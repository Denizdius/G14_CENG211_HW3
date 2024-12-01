package com.gardenpuzzle.model.objects;

import com.gardenpuzzle.model.objects.enums.PlantType;
import com.gardenpuzzle.model.objects.interfaces.Bloomable;
import com.gardenpuzzle.model.objects.interfaces.Searchable;

public class GardenPlant extends GardenObject implements Bloomable, Searchable {
    private String name;
    private PlantType type;
    private int areaOfPollenSpread;

    public GardenPlant(String id, String name, PlantType type, int areaOfPollenSpread) {
        super(id);
        this.name = name;
        this.type = type;
        this.areaOfPollenSpread = areaOfPollenSpread;
    }

    @Override
    public void bloom() {
        // Implement bloom logic
    }

    @Override
    public boolean matches(String criteria) {
        // Implement search logic
        return false;
    }
}