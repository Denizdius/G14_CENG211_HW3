package com.gardenpuzzle.app;

import com.gardenpuzzle.model.objects.GardenObject;
import com.gardenpuzzle.model.objects.GardenPlant;
import com.gardenpuzzle.model.objects.LightSource;
import com.gardenpuzzle.model.objects.enums.PlantType;
import com.gardenpuzzle.model.objects.enums.Color;

import java.util.List;
import java.util.Scanner;

public class UserInterface {
    private Scanner scanner;

    public UserInterface() {
        this.scanner = new Scanner(System.in);
    }

    public void displayGoal(PlantType[] goalPlantTypes, Color[] goalColors) {
        System.out.println("Your goal Square needs the following Pollens infused with following Colors:");
        System.out.print("Pollens: ");
        for (PlantType type : goalPlantTypes) {
            System.out.print(type + " ");
        }
        System.out.print("\nColors: ");
        for (Color color : goalColors) {
            System.out.print(color + " ");
        }
        System.out.println();
    }

    public String getObjectTypeChoice() {
        System.out.print("Please select the type of object ([1] Plant, [2] Light): ");
        return scanner.nextLine();
    }

    public String getSearchCriteria(boolean isPlant) {
        if (isPlant) {
            System.out.print("Please select search filter for Plants ([1] Plant type, [2] Plant name, [3] Plant id, [4] Area of spread): ");
        } else {
            System.out.print("Please select search filter for Light Sources ([1] Light type, [2] Light id, [3] Color, [4] Area of reach): ");
        }
        return scanner.nextLine();
    }

    public String getSearchValue(String criteria, boolean isPlant) {
        switch (criteria) {
            case "type":
                if (isPlant) {
                    System.out.print("Please enter a plant type ([1] FLOWER, [2] TREE, [3] BUSH): ");
                    String typeChoice = scanner.nextLine();
                    return typeChoice.equals("1") ? "FLOWER" : typeChoice.equals("2") ? "TREE" : "BUSH";
                } else {
                    System.out.print("Please enter a light type ([1] SMALL_LAMP, [2] LARGE_LAMP, [3] SPOTLIGHT): ");
                    String typeChoice = scanner.nextLine();
                    return typeChoice.equals("1") ? "SMALL_LAMP" : typeChoice.equals("2") ? "LARGE_LAMP" : "SPOTLIGHT";
                }
            case "color":
                System.out.print("Please enter color ([1] RED, [2] GREEN, [3] BLUE): ");
                String colorChoice = scanner.nextLine();
                return colorChoice.equals("1") ? "RED" : colorChoice.equals("2") ? "GREEN" : "BLUE";
            default:
                System.out.print("Please enter " + criteria + ": ");
                return scanner.nextLine();
        }
    }

    public void displaySearchResults(List<GardenObject> results) {
        if (results.isEmpty()) {
            System.out.println("No objects found for the given criteria.");
            return;
        }
        System.out.println("The objects that fit the criteria:");
        for (GardenObject obj : results) {
            if (obj instanceof GardenPlant) {
                GardenPlant plant = (GardenPlant) obj;
                System.out.println("- Type: " + plant.getType() + ", Id: " + plant.getId() + 
                                 ", Name: " + plant.getName() + ", Area of spread: " + plant.getAreaOfPollenSpread());
            } else if (obj instanceof LightSource) {
                LightSource light = (LightSource) obj;
                System.out.println("- Type: " + light.getType() + ", Id: " + light.getId() + 
                                 ", Color: " + light.getColor() + ", Area of reach: " + light.getAreaOfLightReach());
            }
        }
    }

    public String getObjectId() {
        System.out.print("Please enter the id of the object you would like to take: ");
        return scanner.nextLine();
    }

    public String getPlacementLocation() {
        System.out.print("Enter the location you would like to place the selected Garden Object (e.g., A1): ");
        return scanner.nextLine();
    }

    public boolean wantsToContinue(String message) {
        System.out.print(message + " ([1] Yes, [2] No): ");
        return scanner.nextLine().equals("1");
    }

    public void displayGameResult(boolean success) {
        System.out.println(success ? "====>> SUCCESSFUL" : "====>> UNSUCCESSFUL");
    }

    public void displayMessage(String message) {
        System.out.println(message);
    }
} 