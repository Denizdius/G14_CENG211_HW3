// FILE: com/gardenpuzzle/app/GardenerApp.java
package com.gardenpuzzle.app;

import com.gardenpuzzle.model.garden.Garden;
import com.gardenpuzzle.model.storagesheds.StorageShed;
import com.gardenpuzzle.util.FileReader;
import com.gardenpuzzle.util.GameGoalGenerator;
import com.gardenpuzzle.model.objects.GardenObject;
import com.gardenpuzzle.model.objects.enums.PlantType;
import com.gardenpuzzle.model.objects.enums.Color;

import java.util.List;
import java.util.Scanner;

public class GardenerApp {
    private Garden garden;
    private StorageShed storageShed;
    private GameGoalGenerator goalGenerator;

    public GardenerApp() {
        garden = new Garden();
        storageShed = new StorageShed();
        goalGenerator = new GameGoalGenerator();
    }

    public void start() {
        // Implement game logic
    }

    public static void main(String[] args) {
        GardenerApp app = new GardenerApp();
        app.start();
    }
}