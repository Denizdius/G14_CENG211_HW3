package com.gardenpuzzle.model.garden;

import com.gardenpuzzle.model.objects.GardenObject;
import com.gardenpuzzle.model.garden.PollenCloud;

public class GardenSquare {
    private GardenObject gardenObject;
    private PollenCloud pollenCloud;

    public void setGardenObject(GardenObject gardenObject) {
        this.gardenObject = gardenObject;
    }

    public void setPollenCloud(PollenCloud pollenCloud) {
        this.pollenCloud = pollenCloud;
    }

    public GardenObject getGardenObject() {
        return gardenObject;
    }

    public PollenCloud getPollenCloud() {
        return pollenCloud;
    }
}