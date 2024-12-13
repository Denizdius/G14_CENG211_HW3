package com.gardenpuzzle.model.objects;

import com.gardenpuzzle.model.garden.Garden;
import com.gardenpuzzle.model.garden.GardenSquare;
import com.gardenpuzzle.model.garden.PollenCloud;
import com.gardenpuzzle.model.objects.enums.Color;
import com.gardenpuzzle.model.objects.enums.PlantType;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class GardenPlant extends GardenObject {
    private String name;
    private PlantType type;
    private int areaOfPollenSpread;
    private List<Color> colors;

    public GardenPlant(String id, String name, PlantType type, int areaOfPollenSpread) {
        super(id);
        this.name = name;
        this.type = type;
        this.areaOfPollenSpread = areaOfPollenSpread;
        this.colors = new ArrayList<>();
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

    public List<Color> getColors() {
        return new ArrayList<>(colors);
    }

    public void addColor(Color color) {
        if (!colors.contains(color)) {
            colors.add(color);
        }
    }

    public void bloom(Garden garden, int row, int col) {
        if (colors.isEmpty()) {
            return;
        }

        int startRow = Math.max(0, row - areaOfPollenSpread);
        int endRow = Math.min(garden.getRows() - 1, row + areaOfPollenSpread);
        int startCol = Math.max(0, col - areaOfPollenSpread);
        int endCol = Math.min(garden.getColumns() - 1, col + areaOfPollenSpread);

        for (int i = startRow; i <= endRow; i++) {
            for (int j = startCol; j <= endCol; j++) {
                if (isWithinSpreadArea(row, col, i, j)) {
                    GardenSquare square = garden.getSquare(i, j);
                    PollenCloud cloud = square.getPollenCloud();
                    if (cloud == null) {
                        cloud = new PollenCloud();
                        square.setPollenCloud(cloud);
                    }
                    cloud.addPlantType(type);
                    for (Color color : colors) {
                        cloud.addColor(color);
                    }
                }
            }
        }
    }

    private boolean isWithinSpreadArea(int centerRow, int centerCol, int targetRow, int targetCol) {
        int rowDiff = Math.abs(centerRow - targetRow);
        int colDiff = Math.abs(centerCol - targetCol);
        return rowDiff <= areaOfPollenSpread && colDiff <= areaOfPollenSpread;
    }

    @Override
    public String toString() {
        return "GardenPlant{" +
               "id='" + id + '\'' +
               ", name='" + name + '\'' +
               ", type=" + type +
               ", areaOfPollenSpread=" + areaOfPollenSpread +
               ", colors=" + colors +
               '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        GardenPlant that = (GardenPlant) o;
        return areaOfPollenSpread == that.areaOfPollenSpread &&
               Objects.equals(name, that.name) &&
               type == that.type &&
               Objects.equals(colors, that.colors);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), name, type, areaOfPollenSpread, colors);
    }
}