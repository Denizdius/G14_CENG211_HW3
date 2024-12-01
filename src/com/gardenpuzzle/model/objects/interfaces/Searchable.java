package com.gardenpuzzle.model.objects.interfaces;

public interface Searchable {
    boolean matches(String criteria, String value);
}