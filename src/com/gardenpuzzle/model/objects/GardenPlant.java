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
        // Bloom logic
    }

    @Override
    public boolean matches(String criteria, String value) {
        switch (criteria) {
            case "type":
                return type.toString().equalsIgnoreCase(value);
            case "id":
                return id.equalsIgnoreCase(value);
            case "name":
                return name.equalsIgnoreCase(value);
            case "area":
                return Integer.toString(areaOfPollenSpread).equals(value);
            default:
                return false;
        }
    }

    public String getName() {
        return name;
    }

    public PlantType getType() {
        return type;
    }

    public int getAreaOfPollenSpread() {
        return areaOfPollenSpread;
    }
}