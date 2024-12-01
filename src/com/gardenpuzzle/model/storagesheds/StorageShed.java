// FILE: com/gardenpuzzle/model/storagesheds/StorageShed.java
package com.gardenpuzzle.model.storagesheds;

import java.util.List;
import java.util.ArrayList;
import com.gardenpuzzle.model.objects.GardenObject;

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
}