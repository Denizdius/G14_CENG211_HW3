package com.gardenpuzzle.model.garden;

import java.util.ArrayList;
import java.util.List;

public class Garden {
    private List<GardenSquare> squares;
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

    public GardenSquare getSquare(int row, int column) {
        return grid[row][column];
    }

    public void placeObject(int row, int column, GardenObject object) {
        grid[row][column].setGardenObject(object);
    }

    public void displayGarden() {
        // Display logic
    }
}