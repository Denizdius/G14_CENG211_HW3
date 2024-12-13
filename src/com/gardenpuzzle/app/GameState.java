package com.gardenpuzzle.app;

import com.gardenpuzzle.model.garden.Garden;
import com.gardenpuzzle.model.storagesheds.StorageShed;
import com.gardenpuzzle.model.objects.GardenObject;
import com.gardenpuzzle.model.objects.enums.PlantType;
import com.gardenpuzzle.model.objects.enums.Color;

import java.util.List;
import java.util.ArrayList;

public class GameState {
    private Garden garden;
    private StorageShed storageShed;
    private List<GardenObject> gardenStorage;
    private PlantType[] goalPlantTypes;
    private Color[] goalColors;
    private String goalSquare;

    public GameState() {
        this.garden = new Garden();
        this.storageShed = new StorageShed();
        this.gardenStorage = new ArrayList<>();
    }

    public Garden getGarden() {
        return garden;
    }

    public StorageShed getStorageShed() {
        return storageShed;
    }

    public List<GardenObject> getGardenStorage() {
        return gardenStorage;
    }

    public void setGoalPlantTypes(PlantType[] goalPlantTypes) {
        this.goalPlantTypes = goalPlantTypes;
    }

    public void setGoalColors(Color[] goalColors) {
        this.goalColors = goalColors;
    }

    public void setGoalSquare(String goalSquare) {
        this.goalSquare = goalSquare;
    }

    public PlantType[] getGoalPlantTypes() {
        return goalPlantTypes;
    }

    public Color[] getGoalColors() {
        return goalColors;
    }

    public String getGoalSquare() {
        return goalSquare;
    }

    public void addToGardenStorage(GardenObject object) {
        gardenStorage.add(object);
    }

    public void removeFromGardenStorage(GardenObject object) {
        gardenStorage.remove(object);
    }

    public int getRemainingStorageCapacity() {
        return 7 - gardenStorage.size();
    }

    public int[] parseGoalSquare() {
        int row = goalSquare.charAt(0) - 'A';
        int col = Integer.parseInt(goalSquare.substring(1)) - 1;
        return new int[]{row, col};
    }

    @Override
    public String toString() {
        return "GameState{" +
               "garden=" + garden +
               ", storageShed=" + storageShed +
               ", gardenStorage=" + gardenStorage +
               ", goalSquare='" + goalSquare + '\'' +
               '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        GameState gameState = (GameState) o;
        return garden.equals(gameState.garden) &&
               storageShed.equals(gameState.storageShed) &&
               gardenStorage.equals(gameState.gardenStorage) &&
               goalSquare.equals(gameState.goalSquare);
    }
} 