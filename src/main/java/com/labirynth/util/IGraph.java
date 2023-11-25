package com.labirynth.util;

public interface IGraph<T, K> {
    void addVertex(T key, K data);
    void addEdge(T sourceKey, T destinationKey, int weight);
    String bfs(T startVertexKey);
    String dfs(T startVertexKey);
}
