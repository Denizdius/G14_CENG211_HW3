package com.gardenpuzzle.model.garden;

import com.gardenpuzzle.model.objects.GardenObject;
import java.util.ArrayList;
import java.util.List;

public class Garden {
    private int rows = 6;
    private int columns = 8;
    private List<GardenSquare> grid;

    public Garden() {
        grid = new ArrayList<>(rows * columns);
        for (int i = 0; i < rows * columns; i++) {
            grid.add(new GardenSquare());
        }
    }

    public int getRows() {
        return rows;
    }

    public int getColumns() {
        return columns;
    }

    public GardenSquare getSquare(int row, int column) {
        if (isValidPosition(row, column)) {
            int index = row * columns + column;
            return grid.get(index);
        }
        return null;
    }

    public void placeObject(int row, int column, GardenObject object) {
        if (isValidPosition(row, column)) {
            int index = row * columns + column;
            grid.get(index).setGardenObject(object);
        }
    }

    public boolean isBlocked(int row, int column) {
        GardenSquare square = getSquare(row, column);
        if (square != null) {
            return square.getGardenObject() != null && !(square.getGardenObject().getClass().getSimpleName().equals("PollenCloud"));
        }
        return true;  // Out of bounds positions are considered blocked
    }

    private boolean isValidPosition(int row, int column) {
        return row >= 0 && row < rows && column >= 0 && column < columns;
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
                GardenSquare square = getSquare(i, j);
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

    public void displayGarden(int targetRow, int targetCol) {
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
                GardenSquare square = getSquare(i, j);
                String content = "     ";
                if (i == targetRow && j == targetCol) {
                    content = "Target";
                } else if (square.getGardenObject() != null) {
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