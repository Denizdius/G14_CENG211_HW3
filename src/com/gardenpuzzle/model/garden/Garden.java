package com.gardenpuzzle.model.garden;

import com.gardenpuzzle.model.objects.GardenObject;

public class Garden {
    private int rows = 6;
    private int columns = 8;
    private GardenSquare[][] grid;

    public Garden() {
        grid = new GardenSquare[rows][columns];
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                grid[i][j] = new GardenSquare();
            }
        }
    }

    public int getRows() {
        return rows;
    }

    public int getColumns() {
        return columns;
    }

    public GardenSquare getSquare(int row, int column) {
        if (row >= 0 && row < rows && column >= 0 && column < columns) {
            return grid[row][column];
        } else {
            return null;
        }
    }

    public void placeObject(int row, int column, GardenObject object) {
        if (row >= 0 && row < rows && column >= 0 && column < columns) {
            grid[row][column].setGardenObject(object);
        }
    }

    public boolean isBlocked(int row, int column) {
        if (row >= 0 && row < rows && column >= 0 && column < columns) {
            GardenSquare square = grid[row][column];
            return square.getGardenObject() != null && !(square.getGardenObject().getClass().getSimpleName().equals("PollenCloud"));
        }
        return true;  // Out of bounds positions are considered blocked
    }

    public void displayGarden() {
        // Display column headers
        System.out.print("     ");
        for (int col = 1; col <= columns; col++) {
            System.out.printf("  %-3d ", col);
        }
        System.out.println();
        System.out.print("   ");
        System.out.println("------".repeat(columns));

        // Display rows with content
        for (int i = 0; i < rows; i++) {
            char rowLabel = (char) ('A' + i);
            System.out.print(" " + rowLabel + " |");
            for (int j = 0; j < columns; j++) {
                GardenSquare square = grid[i][j];
                String content = "     ";
                if (square.getGardenObject() != null) {
                    content = " " + square.getGardenObject().getId() + " ";
                } else if (square.getPollenCloud() != null) {
                    content = "  P  ";
                }
                System.out.print(content + "|");
            }
            System.out.println();
            System.out.print("   ");
            System.out.println("------".repeat(columns));
        }
    }
}