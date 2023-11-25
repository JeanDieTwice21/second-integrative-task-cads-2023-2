package com.labirynth.util;

import java.util.ArrayList;
import java.util.List;

public class Vertex<T, K> {
    private K id;
    private T data;
    private boolean isVisited;
    private List<Edge<T, K>> aristas;
    private int index;

    public Vertex(K id, T data, int index) {
        this.id = id;
        this.data = data;
        this.isVisited = false;
        this.aristas = new ArrayList<>();
        this.index = index;
    }

    public K getId() {
        return id;
    }

    public T getData() {
        return data;
    }

    public boolean isVisited() {
        return isVisited;
    }

    public void setVisited(boolean visited) {
        this.isVisited = visited;
    }

    public List<Edge<T, K>> getAristas() {
        return aristas;
    }

    public void addEdge(Vertex<T, K> destiny, int weight) {
        Edge<T, K> edge = new Edge<>(destiny, weight);
        aristas.add(edge);
    }

    public int getIndex() {
        return index;
    }
}
