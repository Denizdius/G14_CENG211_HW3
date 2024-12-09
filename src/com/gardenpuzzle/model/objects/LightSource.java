package com.gardenpuzzle.model.objects;

import com.gardenpuzzle.model.garden.Garden;
import com.gardenpuzzle.model.garden.GardenSquare;
import com.gardenpuzzle.model.garden.PollenCloud;
import com.gardenpuzzle.model.objects.enums.Color;
import com.gardenpuzzle.model.objects.enums.LightType;
import com.gardenpuzzle.model.objects.interfaces.Lightable;
import com.gardenpuzzle.model.objects.interfaces.Searchable;

import java.util.HashMap;
import java.util.Map;

public class LightSource extends GardenObject implements Lightable, Searchable {
    private LightType type;
    private Color color;
    private int areaOfLightReach;

    private static Map<LightType, Integer> exceptionCount = new HashMap<>();

    public LightSource(String id, LightType type, Color color, int areaOfLightReach) {

        super(id);
        this.type = type;
        this.color = color;
        this.areaOfLightReach = areaOfLightReach;
        this.garden = null; // Initialize garden
        if (areaOfLightReach <= 0) {
            throw new IllegalArgumentException("Area of light reach must be positive");
        }
        // Enforce color exceptions
        if (type == LightType.SMALL_LAMP) {
            if (color != Color.BLUE && !isExceptionAllowed(type)) {
                throw new IllegalArgumentException("All SmallLamps must be blue-colored except one.");
            }
        } else if (type == LightType.LARGE_LAMP) {
            if (color != Color.GREEN && !isExceptionAllowed(type)) {
                throw new IllegalArgumentException("All LargeLamps must be green-colored except two.");
            }
        } else if (type == LightType.SPOTLIGHT) {
            if (color != Color.RED && !isExceptionAllowed(type)) {
                throw new IllegalArgumentException("All Spotlights must be red-colored except one.");
            }
        }
    }

    private Garden garden;

    public void setGarden(Garden garden) {
        this.garden = garden;
    }

    @Override
    public void lightUp() {

        if (garden == null) {
            return; // Or throw an exception
        }
        // Get position of light source from garden
        int[] position = findPositionInGarden();
        if (position == null) return;

        int row = position[0];
        int col = position[1];

        switch (type) {
            case SMALL_LAMP:
                // Light 1-3 squares to the right
                lightRightDirection(row, col);
                break;
            case LARGE_LAMP:
                // Light 3-4 squares to the left
                lightLeftDirection(row, col);
                break;
            case SPOTLIGHT:
                // Light 4-5 squares downwards
                lightDownDirection(row, col);
                break;
        }
    }

    private void lightRightDirection(int row, int col) {
        for (int i = 1; i <= areaOfLightReach; i++) {
            int newCol = col + i;
            if (!isValidPosition(row, newCol)) break;
            GardenSquare square = garden.getSquare(row, newCol);
            if (square.getGardenObject() != null) break; // Stop at obstacles
            updateSquareWithLight(square);
        }
    }

    private void lightLeftDirection(int row, int col) {
        for (int i = 1; i <= areaOfLightReach; i++) {
            int newCol = col - i;
            if (!isValidPosition(row, newCol)) break;
            GardenSquare square = garden.getSquare(row, newCol);
            if (square.getGardenObject() != null) break;
            updateSquareWithLight(square);
        }
    }

    private void lightDownDirection(int row, int col) {
        for (int i = 1; i <= areaOfLightReach; i++) {
            int newRow = row + i;
            if (!isValidPosition(newRow, col)) break;
            GardenSquare square = garden.getSquare(newRow, col);
            if (square.getGardenObject() != null) break;
            updateSquareWithLight(square);
        }
    }

    private void updateSquareWithLight(GardenSquare square) {
        if (square.getPollenCloud() == null) {
            square.setPollenCloud(new PollenCloud());
        }
        square.getPollenCloud().addColor(this.color);
    }

    private boolean isValidPosition(int row, int col) {
        return row >= 0 && row < garden.getRows() &&
                col >= 0 && col < garden.getColumns();
    }

    private int[] findPositionInGarden() {
        for (int i = 0; i < garden.getRows(); i++) {
            for (int j = 0; j < garden.getColumns(); j++) {
                GardenSquare square = garden.getSquare(i, j);
                if (square.getGardenObject() == this) {
                    return new int[]{i, j};
                }            }        }        return null;    }    private boolean isExceptionAllowed(LightType type) {        int allowedExceptions = (type == LightType.SMALL_LAMP) ? 1 : (type == LightType.LARGE_LAMP) ? 2 : 1;        int count = exceptionCount.getOrDefault(type, 0);        if (count < allowedExceptions) {            exceptionCount.put(type, count + 1);            return true;        }
        return false;
    }

    @Override
    public boolean matches(String criteria, String value) {
        switch (criteria) {
            case "type":
                return type.toString().equalsIgnoreCase(value);
            case "id":
                return id.equalsIgnoreCase(value);
            case "color":
                return color.toString().equalsIgnoreCase(value);
            case "area":
                return Integer.toString(areaOfLightReach).equals(value);
            default:
                return false;
        }
    }

    public LightType getType() {
        return type;
    }

    public Color getColor() {
        return color;
    }

    public int getAreaOfLightReach() {
        return areaOfLightReach;
    }
}