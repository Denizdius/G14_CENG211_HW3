package com.gardenpuzzle.app;

import com.gardenpuzzle.model.garden.Garden;
import com.gardenpuzzle.model.garden.GardenSquare;
import com.gardenpuzzle.model.garden.PollenCloud;
import com.gardenpuzzle.model.objects.GardenObject;
import com.gardenpuzzle.model.objects.GardenPlant;
import com.gardenpuzzle.model.objects.LightSource;
import com.gardenpuzzle.model.objects.Statue;
import com.gardenpuzzle.model.objects.enums.Color;
import com.gardenpuzzle.model.objects.enums.PlantType;
import com.gardenpuzzle.util.FileReader;
import com.gardenpuzzle.util.GameGoalGenerator;

import java.util.List;
import java.util.Random;

public class GameController {
    private GameState gameState;
    private UserInterface ui;
    private GameGoalGenerator goalGenerator;

    public GameController() {
        this.gameState = new GameState();
        this.ui = new UserInterface();
        this.goalGenerator = new GameGoalGenerator();
    }

    public void startGame() {
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
            gameState.getStorageShed().addGardenObject(obj);
        }
    }

    private void generateGoal() {
        gameState.setGoalPlantTypes(goalGenerator.generatePlantTypes());
        gameState.setGoalColors(goalGenerator.generateColors());
        gameState.setGoalSquare(goalGenerator.generateGoalSquare());
        ui.displayGoal(gameState.getGoalPlantTypes(), gameState.getGoalColors());
    }

    private void placeStatues() {
        Random rand = new Random();
        Garden garden = gameState.getGarden();
        int statuesPlaced = 0;
        while (statuesPlaced < 7) {
            int row = rand.nextInt(garden.getRows());
            int col = rand.nextInt(garden.getColumns());
            if (!garden.isBlocked(row, col)) {
                garden.placeObject(row, col, new Statue("S" + (statuesPlaced + 1)));
                statuesPlaced++;
            }
        }
    }

    private void carryGardenObjects() {
        while (gameState.getRemainingStorageCapacity() > 0) {
            ui.displayMessage("You can take up to " + gameState.getRemainingStorageCapacity() + " object(s).");
            String choice = ui.getObjectTypeChoice();
            
            if (!processObjectSelection(choice)) {
                continue;
            }

            if (gameState.getRemainingStorageCapacity() == 0) break;
            
            if (!ui.wantsToContinue("Would you like to select another one?")) {
                break;
            }
        }
    }

    private boolean processObjectSelection(String choice) {
        boolean isPlant = choice.equals("1");
        if (!choice.equals("1") && !choice.equals("2")) return false;

        String criteria = convertSearchFilter(ui.getSearchCriteria(isPlant), isPlant);
        String value = ui.getSearchValue(criteria, isPlant);
        
        List<GardenObject> results = gameState.getStorageShed().searchGardenObjects(criteria, value);
        ui.displaySearchResults(results);
        
        String id = ui.getObjectId();
        for (GardenObject obj : results) {
            if (obj.getId().equalsIgnoreCase(id)) {
                gameState.addToGardenStorage(obj);
                return true;
            }
        }
        ui.displayMessage("Invalid id entered.");
        return false;
    }

    private String convertSearchFilter(String filter, boolean isPlant) {
        switch (filter) {
            case "1": return "type";
            case "2": return isPlant ? "name" : "id";
            case "3": return isPlant ? "id" : "color";
            case "4": return "area";
            default: return "";
        }
    }

    private void placeGardenObjects() {
        ui.displayMessage("==> The gardener carries selected objects to the Garden.\n");
        int[] targetPosition = gameState.parseGoalSquare();
        gameState.getGarden().displayGarden(targetPosition[0], targetPosition[1]);

        while (!gameState.getGardenStorage().isEmpty()) {
            displayAvailableObjects();
            String id = ui.getObjectId();
            GardenObject selectedObj = findObjectById(id);
            
            if (selectedObj == null) {
                ui.displayMessage("Invalid id entered.");
                continue;
            }

            String location = ui.getPlacementLocation();
            placeObject(location, selectedObj);
            
            if (gameState.getGardenStorage().isEmpty()) break;
            
            if (!ui.wantsToContinue("Would you like to place another object?")) {
                break;
            }
        }
    }

    private void displayAvailableObjects() {
        ui.displayMessage("Your chosen Garden Objects:");
        for (GardenObject obj : gameState.getGardenStorage()) {
            ui.displayMessage("- " + obj.getClass().getSimpleName() + ", Id: " + obj.getId());
        }
    }

    private GardenObject findObjectById(String id) {
        return gameState.getGardenStorage().stream()
                .filter(obj -> obj.getId().equalsIgnoreCase(id))
                .findFirst()
                .orElse(null);
    }

    private void placeObject(String location, GardenObject obj) {
        int row = location.charAt(0) - 'A';
        int col = Integer.parseInt(location.substring(1)) - 1;
        gameState.getGarden().placeObject(row, col, obj);
        if (obj instanceof LightSource) {
            ((LightSource) obj).setGarden(gameState.getGarden());
        }
        gameState.removeFromGardenStorage(obj);
    }

    private void waitForBloomAndLight() {
        ui.displayMessage("The gardener starts waiting. All lights are lit up. All plants start blooming.");
        ui.displayMessage("******************************************************************");
        
        Garden garden = gameState.getGarden();
        // Trigger light for all light sources first to infuse colors
        for (int i = 0; i < garden.getRows(); i++) {
            for (int j = 0; j < garden.getColumns(); j++) {
                GardenSquare square = garden.getSquare(i, j);
                if (square.getGardenObject() instanceof LightSource) {
                    ((LightSource) square.getGardenObject()).lightUp();
                }
            }
        }
        
        // Then trigger bloom for all plants
        for (int i = 0; i < garden.getRows(); i++) {
            for (int j = 0; j < garden.getColumns(); j++) {
                GardenSquare square = garden.getSquare(i, j);
                if (square.getGardenObject() instanceof GardenPlant) {
                    ((GardenPlant) square.getGardenObject()).bloom(garden, i, j);
                }
            }
        }
    }

    private void displayFinalGarden() {
        ui.displayMessage("Final Garden Map:");
        gameState.getGarden().displayGarden();
        
        ui.displayMessage("Legend:");
        Garden garden = gameState.getGarden();
        for (int i = 0; i < garden.getRows(); i++) {
            for (int j = 0; j < garden.getColumns(); j++) {
                GardenSquare square = garden.getSquare(i, j);
                if (square.getGardenObject() != null) {
                    ui.displayMessage("-- " + square.getGardenObject().getId() + ": " + 
                        getObjectDescription(square.getGardenObject()));
                }
            }
        }
    }

    private String getObjectDescription(GardenObject obj) {
        if (obj instanceof GardenPlant) {
            GardenPlant plant = (GardenPlant) obj;
            return plant.getName() + " " + plant.getType().toString().toLowerCase();
        } else if (obj instanceof LightSource) {
            LightSource light = (LightSource) obj;
            return light.getType().toString().toLowerCase().replace('_', ' ') + 
                   " with " + light.getColor().toString().toLowerCase() + " color";
        }
        return "Statue";
    }

    private void checkGoal() {
        ui.displayMessage("\nTarget Square: " + gameState.getGoalSquare());
        
        int[] pos = gameState.parseGoalSquare();
        GardenSquare square = gameState.getGarden().getSquare(pos[0], pos[1]);
        PollenCloud cloud = square.getPollenCloud();
        
        boolean success = isGoalAchieved(cloud);
        ui.displayGameResult(success);
    }

    private boolean isGoalAchieved(PollenCloud cloud) {
        if (cloud == null) return false;
        
        // Check if all required plant types are present
        for (PlantType type : gameState.getGoalPlantTypes()) {
            if (!cloud.getPlantTypes().contains(type)) {
                return false;
            }
        }
        
        // Check if all required colors are present
        for (Color color : gameState.getGoalColors()) {
            if (!cloud.getColors().contains(color)) {
                return false;
            }
        }
        
        return true;
    }
} 