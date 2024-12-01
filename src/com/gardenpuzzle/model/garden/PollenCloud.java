// FILE: com/gardenpuzzle/model/garden/PollenCloud.java
package com.gardenpuzzle.model.garden;

import java.util.List;
import java.util.ArrayList;
import com.gardenpuzzle.model.objects.enums.Color;

public class PollenCloud {
    private List<Color> colors;

    public PollenCloud() {
        colors = new ArrayList<>();
    }

    public void addColor(Color color) {
        colors.add(color);
    }
}