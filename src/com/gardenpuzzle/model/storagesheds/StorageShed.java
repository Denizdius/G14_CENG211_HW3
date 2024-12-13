package com.gardenpuzzle.model.storagesheds;

import com.gardenpuzzle.model.objects.GardenObject;
import com.gardenpuzzle.model.objects.GardenPlant;
import com.gardenpuzzle.model.objects.LightSource;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class StorageShed {
    private List<GardenObject> objects;

    public StorageShed() {
        this.objects = new ArrayList<>();
    }

    public void addGardenObject(GardenObject obj) {
        objects.add(obj);
    }

    public List<GardenObject> searchGardenObjects(String criteria, String value) {
        return objects.stream()
                .filter(obj -> matchesCriteria(obj, criteria, value))
                .collect(Collectors.toList());
    }

    private boolean matchesCriteria(GardenObject obj, String criteria, String value) {
        if (obj instanceof GardenPlant) {
            GardenPlant plant = (GardenPlant) obj;
            switch (criteria) {
                case "type": return plant.getType().toString().equalsIgnoreCase(value);
                case "name": return plant.getName().equalsIgnoreCase(value);
                case "id": return plant.getId().equalsIgnoreCase(value);
                case "area": return String.valueOf(plant.getAreaOfPollenSpread()).equals(value);
                default: return false;
            }
        } else if (obj instanceof LightSource) {
            LightSource light = (LightSource) obj;
            switch (criteria) {
                case "type": return light.getType().toString().equalsIgnoreCase(value);
                case "id": return light.getId().equalsIgnoreCase(value);
                case "color": return light.getColor().toString().equalsIgnoreCase(value);
                case "area": return String.valueOf(light.getAreaOfLightReach()).equals(value);
                default: return false;
            }
        }
        return false;
    }

    @Override
    public String toString() {
        return "StorageShed{" +
               "objects=" + objects +
               '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        StorageShed that = (StorageShed) o;
        return Objects.equals(objects, that.objects);
    }

    @Override
    public int hashCode() {
        return Objects.hash(objects);
    }
}