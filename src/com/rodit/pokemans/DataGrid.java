package com.rodit.pokemans;

import java.util.HashMap;

public class DataGrid<E, T, Y> {

    private HashMap<E, HashMap<T, Y>> grid;

    public DataGrid() {
        grid = new HashMap<E, HashMap<T, Y>>();
    }

    public void add(E key, T key2, Y value) {
        if (grid.containsKey(key)) grid.get(key).put(key2, value);
    }

    public void addColumn(E key) {
        grid.put(key, new HashMap<T, Y>());
    }

    public void removeColumn(E key) {
        grid.remove(key);
    }

    public void remove(E key, T value) {
        grid.get(key).remove(value);
    }

    public HashMap<T, Y> getColumn(E key) {
        return grid.get(key);
    }

    public Y getValue(E key1, T key2){
        return grid.get(key1).get(key2);
    }
}
