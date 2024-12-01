package com.gardenpuzzle.model.objects;

import com.gardenpuzzle.model.objects.enums.Color;
import com.gardenpuzzle.model.objects.enums.LightType;
import com.gardenpuzzle.model.objects.interfaces.Lightable;
import com.gardenpuzzle.model.objects.interfaces.Searchable;

public class LightSource extends GardenObject implements Lightable, Searchable {
    private LightType type;
    private Color color;
    private int areaOfLightReach;

    public LightSource(String id, LightType type, Color color, int areaOfLightReach) {
        super(id);
        this.type = type;
        this.color = color;
        this.areaOfLightReach = areaOfLightReach;
    }

    @Override
    public void lightUp() {
        // Light up logic
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