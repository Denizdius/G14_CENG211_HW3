package com.gardenpuzzle.model.garden;

import com.gardenpuzzle.model.objects.GardenObject;
import java.util.Objects;

public class GardenSquare {
    private GardenObject gardenObject;
    private PollenCloud pollenCloud;

    public GardenSquare() {
        this.gardenObject = null;
        this.pollenCloud = null;
    }

    public GardenObject getGardenObject() {
        return gardenObject;
    }

    public void setGardenObject(GardenObject gardenObject) {
        this.gardenObject = gardenObject;
    }

    public PollenCloud getPollenCloud() {
        return pollenCloud;
    }

    public void setPollenCloud(PollenCloud pollenCloud) {
        this.pollenCloud = pollenCloud;
    }

    @Override
    public String toString() {
        return "GardenSquare{" +
               "gardenObject=" + gardenObject +
               ", pollenCloud=" + pollenCloud +
               '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        GardenSquare that = (GardenSquare) o;
        return Objects.equals(gardenObject, that.gardenObject) &&
               Objects.equals(pollenCloud, that.pollenCloud);
    }

    @Override
    public int hashCode() {
        return Objects.hash(gardenObject, pollenCloud);
    }
}