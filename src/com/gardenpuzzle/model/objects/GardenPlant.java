package com.gardenpuzzle.model.objects;

import com.gardenpuzzle.model.objects.enums.PlantType;
import com.gardenpuzzle.model.objects.interfaces.Bloomable;
import com.gardenpuzzle.model.objects.interfaces.Searchable;
import com.gardenpuzzle.model.garden.Garden;
import com.gardenpuzzle.model.garden.GardenSquare;
import com.gardenpuzzle.model.garden.PollenCloud;

import java.util.HashSet;
import java.util.Set;

public class GardenPlant extends GardenObject implements Bloomable, Searchable {
    private String name;
    private PlantType type;
    private int areaOfPollenSpread;

    public GardenPlant(String id, String name, PlantType type, int areaOfPollenSpread) {
        super(id);
        if (name == null || type == null || areaOfPollenSpread < 0) {
            throw new IllegalArgumentException("Invalid plant parameters");
        }
        this.name = name;
        this.type = type;
        this.areaOfPollenSpread = areaOfPollenSpread;
    }

    public String getName() {
        return name;
    }

    public PlantType getType() {
        return type;
    }

    public int getAreaOfPollenSpread() {
        return areaOfPollenSpread;
    }

    @Override
    public void bloom(Garden garden, int row, int col) {
        if (garden == null || !isValidPosition(row, col, garden)) {
            throw new IllegalArgumentException("Invalid bloom parameters");
        }
        Set<String> visited = new HashSet<>();
        bloomRecursive(garden, row, col, visited);
    }

    private void bloomRecursive(Garden garden, int row, int col, Set<String> visited) {
        String positionKey = row + "," + col;
        if (visited.contains(positionKey)) return;
        visited.add(positionKey);

        GardenSquare square = garden.getSquare(row, col);
        if (square == null || garden.isBlocked(row, col)) return;

        if (square.getPollenCloud() == null) {
            square.setPollenCloud(new PollenCloud());
        }
        square.getPollenCloud().addPlantType(this.getType());

        GardenObject obj = square.getGardenObject();
        if (obj instanceof GardenPlant && obj != this) {
            GardenPlant otherPlant = (GardenPlant) obj;
            // Add the other plant's type to the pollen cloud
            square.getPollenCloud().addPlantType(otherPlant.getType());
            // Continue spreading from the current plant to avoid infinite recursion
        }

        int maxDistance = areaOfPollenSpread;
        switch (type) {
            case FLOWER:
                spreadDiagonally(garden, row, col, maxDistance, visited);
                break;
            case TREE:
                spreadHorizontally(garden, row, col, maxDistance, visited);
                break;
            case BUSH:
                spreadVertically(garden, row, col, maxDistance, visited);
                break;
        }
    }

    private void spreadDiagonally(Garden garden, int row, int col, int maxDistance, Set<String> visited) {
        int[][] directions = { {-1, -1}, {-1, 1}, {1, -1}, {1, 1} };
        for (int[] dir : directions) {
            for (int distance = 1; distance <= maxDistance; distance++) {
                int newRow = row + dir[0] * distance;
                int newCol = col + dir[1] * distance;
                if (!isValidPosition(newRow, newCol, garden)) break;
                if (garden.isBlocked(newRow, newCol)) break;
                bloomRecursive(garden, newRow, newCol, visited);
            }
        }
    }

    private void spreadHorizontally(Garden garden, int row, int col, int maxDistance, Set<String> visited) {
        for (int distance = 1; distance <= maxDistance; distance++) {
            int newColLeft = col - distance;
            if (isValidPosition(row, newColLeft, garden) && !garden.isBlocked(row, newColLeft)) {
                bloomRecursive(garden, row, newColLeft, visited);
            } else {
                break;
            }
            int newColRight = col + distance;
            if (isValidPosition(row, newColRight, garden) && !garden.isBlocked(row, newColRight)) {
                bloomRecursive(garden, row, newColRight, visited);
            } else {
                break;
            }
        }
    }

    private void spreadVertically(Garden garden, int row, int col, int maxDistance, Set<String> visited) {
        for (int distance = 1; distance <= maxDistance; distance++) {
            int newRowUp = row - distance;
            if (isValidPosition(newRowUp, col, garden) && !garden.isBlocked(newRowUp, col)) {
                bloomRecursive(garden, newRowUp, col, visited);
            } else {
                break;
            }
            int newRowDown = row + distance;
            if (isValidPosition(newRowDown, col, garden) && !garden.isBlocked(newRowDown, col)) {
                bloomRecursive(garden, newRowDown, col, visited);
            } else {
                break;
            }
        }
    }

    private boolean isValidPosition(int row, int col, Garden garden) {
        return row >= 0 && row < garden.getRows() && col >= 0 && col < garden.getColumns();
    }

    @Override
    public boolean matches(String criteria, String value) {
        switch (criteria) {
            case "type":
                return type.toString().equalsIgnoreCase(value);
            case "id":
                return getId().equalsIgnoreCase(value);
            case "name":
                return name.equalsIgnoreCase(value);
            case "area":
                return Integer.toString(areaOfPollenSpread).equals(value);
            default:
                return false;
        }
    }
}