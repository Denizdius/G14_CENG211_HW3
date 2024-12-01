package com.gardenpuzzle.model.objects;

public abstract class GardenObject {
    private String id;

    public GardenObject(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

}
