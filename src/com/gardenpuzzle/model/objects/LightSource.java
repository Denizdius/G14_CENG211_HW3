package com.gardenpuzzle.model.objects;

import com.gardenpuzzle.model.garden.Garden;
import com.gardenpuzzle.model.garden.GardenSquare;
import com.gardenpuzzle.model.objects.enums.Color;
import com.gardenpuzzle.model.objects.enums.LightType;
import java.util.Objects;

public class LightSource extends GardenObject {
    private LightType type;
    private Color color;
    private int areaOfLightReach;
    private Garden garden;

    public LightSource(String id, LightType type, Color color, int areaOfLightReach) {
        super(id);
        this.type = type;
        this.color = color;
        this.areaOfLightReach = areaOfLightReach;
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

    public void setGarden(Garden garden) {
        this.garden = garden;
    }

    public void lightUp() {
        if (garden == null) {
            return;
        }

        int row = -1;
        int col = -1;
        
        // Find this light source's position
        for (int i = 0; i < garden.getRows(); i++) {
            for (int j = 0; j < garden.getColumns(); j++) {
                if (garden.getSquare(i, j).getGardenObject() == this) {
                    row = i;
                    col = j;
                    break;
                }
            }
            if (row != -1) break;
        }

        if (row == -1) return; // Light source not found in garden

        // Apply light effect based on type
        switch (type) {
            case SMALL_LAMP:
                applyCircularLight(row, col);
                break;
            case LARGE_LAMP:
                applySquareLight(row, col);
                break;
            case SPOTLIGHT:
                applyDirectionalLight(row, col);
                break;
        }
    }

    private void applyCircularLight(int centerRow, int centerCol) {
        for (int i = Math.max(0, centerRow - areaOfLightReach); i <= Math.min(garden.getRows() - 1, centerRow + areaOfLightReach); i++) {
            for (int j = Math.max(0, centerCol - areaOfLightReach); j <= Math.min(garden.getColumns() - 1, centerCol + areaOfLightReach); j++) {
                if (isWithinCircle(centerRow, centerCol, i, j)) {
                    applyLightToPlant(i, j);
                }
            }
        }
    }

    private boolean isWithinCircle(int centerRow, int centerCol, int targetRow, int targetCol) {
        double distance = Math.sqrt(Math.pow(centerRow - targetRow, 2) + Math.pow(centerCol - targetCol, 2));
        return distance <= areaOfLightReach;
    }

    private void applySquareLight(int centerRow, int centerCol) {
        for (int i = Math.max(0, centerRow - areaOfLightReach); i <= Math.min(garden.getRows() - 1, centerRow + areaOfLightReach); i++) {
            for (int j = Math.max(0, centerCol - areaOfLightReach); j <= Math.min(garden.getColumns() - 1, centerCol + areaOfLightReach); j++) {
                applyLightToPlant(i, j);
            }
        }
    }

    private void applyDirectionalLight(int startRow, int startCol) {
        // Spotlight shines in all four directions
        for (int i = Math.max(0, startRow - areaOfLightReach); i <= Math.min(garden.getRows() - 1, startRow); i++) {
            applyLightToPlant(i, startCol); // Up
        }
        for (int i = startRow; i <= Math.min(garden.getRows() - 1, startRow + areaOfLightReach); i++) {
            applyLightToPlant(i, startCol); // Down
        }
        for (int j = Math.max(0, startCol - areaOfLightReach); j <= Math.min(garden.getColumns() - 1, startCol); j++) {
            applyLightToPlant(startRow, j); // Left
        }
        for (int j = startCol; j <= Math.min(garden.getColumns() - 1, startCol + areaOfLightReach); j++) {
            applyLightToPlant(startRow, j); // Right
        }
    }

    private void applyLightToPlant(int row, int col) {
        GardenSquare square = garden.getSquare(row, col);
        if (square.getGardenObject() instanceof GardenPlant) {
            ((GardenPlant) square.getGardenObject()).addColor(color);
        }
    }

    @Override
    public String toString() {
        return "LightSource{" +
               "id='" + id + '\'' +
               ", type=" + type +
               ", color=" + color +
               ", areaOfLightReach=" + areaOfLightReach +
               '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        LightSource that = (LightSource) o;
        return areaOfLightReach == that.areaOfLightReach &&
               type == that.type &&
               color == that.color;
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), type, color, areaOfLightReach);
    }
}