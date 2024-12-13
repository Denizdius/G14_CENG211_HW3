package com.gardenpuzzle.model.objects;

public class Statue extends GardenObject {
    public Statue(String id) {
        super(id);
    }

    @Override
    public String toString() {
        return "Statue{id='" + id + "'}";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        return super.equals(o);
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }
}