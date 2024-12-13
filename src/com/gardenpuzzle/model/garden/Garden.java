package com.gardenpuzzle.model.garden;

import com.gardenpuzzle.model.objects.GardenObject;
import java.util.Arrays;

public class Garden {
    private static final int ROWS = 8;
    private static final int COLUMNS = 8;
    private GardenSquare[][] squares;

    public Garden() {
        squares = new GardenSquare[ROWS][COLUMNS];
        for (int i = 0; i < ROWS; i++) {
            for (int j = 0; j < COLUMNS; j++) {
                squares[i][j] = new GardenSquare();
            }
        }
    }

    public int getRows() {
        return ROWS;
    }

    public int getColumns() {
        return COLUMNS;
    }

    public GardenSquare getSquare(int row, int col) {
        if (!isValidPosition(row, col)) {
            throw new IllegalArgumentException("Invalid position: " + row + ", " + col);
        }
        return squares[row][col];
    }

    public void placeObject(int row, int col, GardenObject obj) {
        if (!isValidPosition(row, col)) {
            throw new IllegalArgumentException("Invalid position: " + row + ", " + col);
        }
        if (isBlocked(row, col)) {
            throw new IllegalStateException("Position is already occupied: " + row + ", " + col);
        }
        squares[row][col].setGardenObject(obj);
    }

    public boolean isBlocked(int row, int col) {
        if (!isValidPosition(row, col)) {
            return true;
        }
        return squares[row][col].getGardenObject() != null;
    }

    private boolean isValidPosition(int row, int col) {
        return row >= 0 && row < ROWS && col >= 0 && col < COLUMNS;
    }

    public void displayGarden() {
        displayGarden(-1, -1);
    }

    public void displayGarden(int targetRow, int targetCol) {
        // Display column headers
        System.out.print("     ");
        for (int col = 1; col <= COLUMNS; col++) {
            System.out.printf("  %-3d ", col);
        }
        System.out.println();
        System.out.print("   ");
        System.out.println("------".repeat(COLUMNS));

        // Display rows with content
        for (int i = 0; i < ROWS; i++) {
            char rowLabel = (char) ('A' + i);
            System.out.print(" " + rowLabel + " |");
            for (int j = 0; j < COLUMNS; j++) {
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
            System.out.println("------".repeat(COLUMNS));
        }
    }

    @Override
    public String toString() {
        return "Garden{" +
               "squares=" + Arrays.deepToString(squares) +
               '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Garden garden = (Garden) o;
        return Arrays.deepEquals(squares, garden.squares);
    }

    @Override
    public int hashCode() {
        return Arrays.deepHashCode(squares);
    }
}