package com.gardenpuzzle.model.storagesheds;

import java.util.ArrayList;
import java.util.List;
import com.gardenpuzzle.model.objects.GardenObject;
import com.gardenpuzzle.model.objects.Statue;

public class StorageShed {
    private List<GardenObject> gardenObjects;

    public StorageShed() {
        gardenObjects = new ArrayList<>();
    }

    public void addGardenObject(GardenObject gardenObject) {
        if (gardenObject == null) {
            throw new IllegalArgumentException("Garden object cannot be null");
        }
        gardenObjects.add(gardenObject);
    }

    public List<GardenObject> getGardenObjects() {
        return gardenObjects;
    }

    public List<GardenObject> searchGardenObjects(String criteria, String value) {
        if (criteria == null || value == null) {
            throw new IllegalArgumentException("Search criteria and value cannot be null");
        }
        
        List<GardenObject> results = new ArrayList<>();
        for (GardenObject obj : gardenObjects) {
            if (!(obj instanceof com.gardenpuzzle.model.objects.interfaces.Searchable)) {
                continue;
            }
            com.gardenpuzzle.model.objects.interfaces.Searchable searchable = (com.gardenpuzzle.model.objects.interfaces.Searchable) obj;
            if (searchable.matches(criteria.toLowerCase(), value.toLowerCase())) {
                results.add(obj);
            }
        }
        return results;
    }
}