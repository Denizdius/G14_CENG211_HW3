package com.gardenpuzzle.model.objects;
import java.io.*;
import java.util.*;

public abstract class GardenObject {
    public String id;

    public GardenObject(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    

}
