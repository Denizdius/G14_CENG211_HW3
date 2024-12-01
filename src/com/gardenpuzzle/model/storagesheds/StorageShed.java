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
        gardenObjects.add(gardenObject);
    }

    public List<GardenObject> getGardenObjects() {
        return gardenObjects;
    }

    public List<GardenObject> searchGardenObjects(String criteria, String value) {
        List<GardenObject> results = new ArrayList<>();
        for (GardenObject obj : gardenObjects) {
            if (obj instanceof Statue) {
                continue;
            }
            if (obj instanceof GardenObject && obj instanceof com.gardenpuzzle.model.objects.interfaces.Searchable) {
                com.gardenpuzzle.model.objects.interfaces.Searchable searchable = (com.gardenpuzzle.model.objects.interfaces.Searchable) obj;
                if (searchable.matches(criteria, value)) {
                    results.add(obj);
                }
            }
        }
        return results;
    }
}