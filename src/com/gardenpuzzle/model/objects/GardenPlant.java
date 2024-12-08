package com.gardenpuzzle.model.objects;

import com.gardenpuzzle.model.objects.enums.PlantType;
import com.gardenpuzzle.model.objects.interfaces.Bloomable;
import com.gardenpuzzle.model.objects.interfaces.Searchable;
import com.gardenpuzzle.model.garden.Garden;
import com.gardenpuzzle.model.garden.GardenSquare;
import com.gardenpuzzle.model.garden.PollenCloud;

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
        if (garden == null || !isValidPosition(row, col, garden.getRows(), garden.getColumns())) {
            throw new IllegalArgumentException("Invalid bloom parameters");
        }
        switch (type) {
            case FLOWER:
                bloomDiagonally(garden, row, col);
                break;
            case TREE:
                bloomHorizontally(garden, row, col);
                break;
            case BUSH:
                bloomVertically(garden, row, col);
                break;
            default:
                break;
        }
    }

    private void bloomDiagonally(Garden garden, int row, int col) {
        int maxDistance = areaOfPollenSpread;
        int rows = garden.getRows();
        int columns = garden.getColumns();

        int[][] directions = { {-1, -1}, {-1, 1}, {1, -1}, {1, 1} };

        for (int distance = 1; distance <= maxDistance; distance++) {
            for (int[] dir : directions) {
                int newRow = row + dir[0] * distance;
                int newCol = col + dir[1] * distance;
                if (isValidPosition(newRow, newCol, rows, columns)) {
                    if (garden.isBlocked(newRow, newCol)) {
                        continue;
                    }
                    updatePollenCloud(garden.getSquare(newRow, newCol));
                }
            }
        }
    }

    private void bloomHorizontally(Garden garden, int row, int col) {
        int maxDistance = areaOfPollenSpread;
        int columns = garden.getColumns();

        for (int distance = 1; distance <= maxDistance; distance++) {
            // Left
            int newColLeft = col - distance;
            if (isValidPosition(row, newColLeft, garden.getRows(), columns)) {
                if (!garden.isBlocked(row, newColLeft)) {
                    updatePollenCloud(garden.getSquare(row, newColLeft));
                }
            }
            // Right
            int newColRight = col + distance;
            if (isValidPosition(row, newColRight, garden.getRows(), columns)) {
                if (!garden.isBlocked(row, newColRight)) {
                    updatePollenCloud(garden.getSquare(row, newColRight));
                }
            }
        }
    }

    private void bloomVertically(Garden garden, int row, int col) {
        int maxDistance = areaOfPollenSpread;
        int rows = garden.getRows();

        for (int distance = 1; distance <= maxDistance; distance++) {
            // Up
            int newRowUp = row - distance;
            if (isValidPosition(newRowUp, col, rows, garden.getColumns())) {
                if (!garden.isBlocked(newRowUp, col)) {
                    updatePollenCloud(garden.getSquare(newRowUp, col));
                }
            }
            // Down
            int newRowDown = row + distance;
            if (isValidPosition(newRowDown, col, rows, garden.getColumns())) {
                if (!garden.isBlocked(newRowDown, col)) {
                    updatePollenCloud(garden.getSquare(newRowDown, col));
                }
            }
        }
    }

    private boolean isValidPosition(int row, int col, int maxRows, int maxCols) {
        return row >= 0 && row < maxRows && col >= 0 && col < maxCols;
    }

    private void updatePollenCloud(GardenSquare square) {
        if (square.getGardenObject() != null) {
            // Blocked by another object
            return;
        }
        if (square.getPollenCloud() == null) {
            square.setPollenCloud(new PollenCloud());
        }
        square.getPollenCloud().addPlantType(type);
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