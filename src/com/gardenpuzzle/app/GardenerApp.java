package com.gardenpuzzle.app;

import com.gardenpuzzle.model.garden.Garden;
import com.gardenpuzzle.model.garden.GardenSquare;
import com.gardenpuzzle.model.garden.PollenCloud;
import com.gardenpuzzle.model.storagesheds.StorageShed;
import com.gardenpuzzle.util.FileReader;
import com.gardenpuzzle.util.GameGoalGenerator;
import com.gardenpuzzle.model.objects.GardenObject;
import com.gardenpuzzle.model.objects.GardenPlant;
import com.gardenpuzzle.model.objects.LightSource;
import com.gardenpuzzle.model.objects.Statue;
import com.gardenpuzzle.model.objects.enums.PlantType;
import com.gardenpuzzle.model.objects.enums.LightType;
import com.gardenpuzzle.model.objects.enums.Color;

import java.util.List;
import java.util.ArrayList;
import java.util.Scanner;

public class GardenerApp {
    private Garden garden;
    private StorageShed storageShed;
    private GameGoalGenerator goalGenerator;
    private List<GardenObject> gardenStorage;
    private PlantType[] goalPlantTypes;
    private Color[] goalColors;
    private String goalSquare;
    private Scanner scanner;

    public GardenerApp() {
        garden = new Garden();
        storageShed = new StorageShed();
        goalGenerator = new GameGoalGenerator();
        gardenStorage = new ArrayList<>();
        scanner = new Scanner(System.in);
    }

    public void start() {
        loadStorageShed();
        generateGoal();
        placeStatues();
        carryGardenObjects();
        placeGardenObjects();
        waitForBloomAndLight();
        displayFinalGarden();
        checkGoal();
    }

    private void loadStorageShed() {
        FileReader fileReader = new FileReader();
        List<GardenObject> objects = fileReader.readGardenObjects("storage_contents.csv");
        for (GardenObject obj : objects) {
            storageShed.addGardenObject(obj);
        }
    }

    private void generateGoal() {
        goalPlantTypes = goalGenerator.generatePlantTypes();
        goalColors = goalGenerator.generateColors();
        goalSquare = goalGenerator.generateGoalSquare();
        System.out.println("Your goal Square needs the following Pollens infused with following Colors:");
        System.out.print("Pollens: ");
        for (PlantType type : goalPlantTypes) {
            System.out.print(type + " ");
        }
        System.out.print("\nColors: ");
        for (Color color : goalColors) {
            System.out.print(color + " ");
        }
        System.out.println("\nTarget Square: " + goalSquare);
    }

    private void placeStatues() {
        for (int i = 1; i <= 7; i++) {
            Statue statue = new Statue("S" + i);
            int row = (int) (Math.random() * 6);
            int col = (int) (Math.random() * 8);
            garden.placeObject(row, col, statue);
        }
    }

    private void carryGardenObjects() {
        int objectsToCarry = 7;
        while (objectsToCarry > 0) {
            System.out.println("You can take up to " + objectsToCarry + " object(s).");
            System.out.print("Please select the type of object ([1] Plant, [2] Light): ");
            String choice = scanner.nextLine();
            String criteria = "";
            String value = "";
            if (choice.equals("1")) {
                System.out.print("Please select search filter for Plants ([1] Plant type, [2] Plant name, [3] Plant id, [4] Area of spread): ");
                String filter = scanner.nextLine();
                switch (filter) {
                    case "1":
                        criteria = "type";
                        System.out.print("Please enter a plant type ([1] FLOWER, [2] TREE, [3] BUSH): ");
                        String typeChoice = scanner.nextLine();
                        if (typeChoice.equals("1")) value = "FLOWER";
                        else if (typeChoice.equals("2")) value = "TREE";
                        else value = "BUSH";
                        break;
                    case "2":
                        criteria = "name";
                        System.out.print("Please enter plant name: ");
                        value = scanner.nextLine();
                        break;
                    case "3":
                        criteria = "id";
                        System.out.print("Please enter plant id: ");
                        value = scanner.nextLine();
                        break;
                    case "4":
                        criteria = "area";
                        System.out.print("Please enter area of spread: ");
                        value = scanner.nextLine();
                        break;
                }
            } else if (choice.equals("2")) {
                System.out.print("Please select search filter for Light Sources ([1] Light type, [2] Light id, [3] Color, [4] Area of reach): ");
                String filter = scanner.nextLine();
                switch (filter) {
                    case "1":
                        criteria = "type";
                        System.out.print("Please enter a light type ([1] SMALL_LAMP, [2] LARGE_LAMP, [3] SPOTLIGHT): ");
                        String typeChoice = scanner.nextLine();
                        if (typeChoice.equals("1")) value = "SMALL_LAMP";
                        else if (typeChoice.equals("2")) value = "LARGE_LAMP";
                        else value = "SPOTLIGHT";
                        break;
                    case "2":
                        criteria = "id";
                        System.out.print("Please enter light id: ");
                        value = scanner.nextLine();
                        break;
                    case "3":
                        criteria = "color";
                        System.out.print("Please enter color ([1] RED, [2] GREEN, [3] BLUE): ");
                        String colorChoice = scanner.nextLine();
                        if (colorChoice.equals("1")) value = "RED";
                        else if (colorChoice.equals("2")) value = "GREEN";
                        else value = "BLUE";
                        break;
                    case "4":
                        criteria = "area";
                        System.out.print("Please enter area of reach: ");
                        value = scanner.nextLine();
                        break;
                }
            }
            List<GardenObject> results = storageShed.searchGardenObjects(criteria, value);
            if (results.isEmpty()) {
                System.out.println("No objects found for the given criteria.");
                continue;
            }
            System.out.println("The objects that fit the criteria:");
            for (GardenObject obj : results) {
                if (obj instanceof GardenPlant) {
                    GardenPlant plant = (GardenPlant) obj;
                    System.out.println("- Type: " + plant.getType() + ", Id: " + plant.getId() + ", Name: " + plant.getName() + ", Area of spread: " + plant.getAreaOfPollenSpread());
                } else if (obj instanceof LightSource) {
                    LightSource light = (LightSource) obj;
                    System.out.println("- Type: " + light.getType() + ", Id: " + light.getId() + ", Color: " + light.getColor() + ", Area of reach: " + light.getAreaOfLightReach());
                }
            }
            System.out.print("Please enter the id of the object you would like to take: ");
            String id = scanner.nextLine();
            boolean found = false;
            for (GardenObject obj : results) {
                if (obj.getId().equalsIgnoreCase(id)) {
                    gardenStorage.add(obj);
                    found = true;
                    break;
                }
            }
            if (found) {
                objectsToCarry--;
                System.out.println("You have taken " + (7 - objectsToCarry) + " object(s).");
                if (objectsToCarry == 0) break;
                System.out.print("Would you like to select another one? ([1] Yes, [2] No): ");
                String another = scanner.nextLine();
                if (another.equals("2")) break;
            } else {
                System.out.println("Invalid id entered.");
            }
        }
    }

    private void placeGardenObjects() {
        while (!gardenStorage.isEmpty()) {
            System.out.println("Your chosen Garden Objects:");
            for (GardenObject obj : gardenStorage) {
                System.out.println("- " + obj.getClass().getSimpleName() + ", Id: " + obj.getId());
            }
            System.out.print("Enter the id corresponding to the Garden Object you would like to place: ");
            String id = scanner.nextLine();
            GardenObject selectedObj = null;
            for (GardenObject obj : gardenStorage) {
                if (obj.getId().equalsIgnoreCase(id)) {
                    selectedObj = obj;
                    break;
                }
            }
            if (selectedObj == null) {
                System.out.println("Invalid id entered.");
                continue;
            }
            System.out.print("Enter the location you would like to place the selected Garden Object (e.g., A1): ");
            String location = scanner.nextLine();
            int row = location.charAt(0) - 'A';
            int col = Integer.parseInt(location.substring(1)) - 1;
            garden.placeObject(row, col, selectedObj);
            gardenStorage.remove(selectedObj);
            if (gardenStorage.isEmpty()) break;
            System.out.print("Would you like to place another object? ([1] Yes, [2] No): ");
            String another = scanner.nextLine();
            if (another.equals("2")) break;
        }
    }

    private void waitForBloomAndLight() {
        System.out.println("The gardener starts waiting. All lights are lit up. All plants start blooming.");
        // Implement bloom and light logic
    }

    private void displayFinalGarden() {
        // Implement garden display logic
    }

    private void checkGoal() {
        // Implement goal checking logic
    }

    public static void main(String[] args) {
        GardenerApp app = new GardenerApp();
        app.start();
    }
}