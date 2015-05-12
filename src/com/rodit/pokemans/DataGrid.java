package com.rodit.pokemans;

import java.util.HashMap;

public class DataGrid<R, T, Y> {

    private HashMap<R, HashMap<T, Y>> grid;

    public DataGrid() {
        grid = new HashMap<R, HashMap<T, Y>>();
    }

    public void add(R key, T key2, Y value) {
        if (grid.containsKey(key)) grid.get(key).put(key2, value);
    }

    public void addColumn(R key) {
        grid.put(key, new HashMap<T, Y>());
    }

    public void removeColumn(R key) {
        grid.remove(key);
    }

    public void remove(R key, T value) {
        grid.get(key).remove(value);
    }

    public HashMap<T, Y> getColumn(R key) {
        return grid.get(key);
    }

    public Y getValue(R key1, T key2){
        return grid.get(key1).get(key2);
    }
}
