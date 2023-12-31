package com.labirynth.util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Stack;

public class GraphList<K, T> implements IGraph<K, T> {
    private Map<K, VertexList<T, K>> vertices;

    public GraphList() {
        this.vertices = new HashMap<>();
    }

    @Override
    public void addVertex(K key, T data) {
        if (!vertices.containsKey(key)) {
            VertexList<T, K> vertex = new VertexList<>(key, data);
            vertices.put(key, vertex);
        }
    }

    public Map<K, VertexList<T,K>> getVertices(){

        return vertices;

    }

    @Override
    public void addEdge(K sourceKey, K destinationKey, int weight) {
        VertexList<T, K> origin = vertices.get(sourceKey);
        VertexList<T, K> destiny = vertices.get(destinationKey);

        try {
            if (origin != null && destiny != null) {
                origin.addEdge(destiny, weight);
                origin.addVertex(destiny);

            } else {
                throw new IllegalArgumentException("Vertex not found.");
            }
        } catch (IllegalArgumentException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    @Override
    public String bfs(K startVertexKey) {
        VertexList<T, K> startVertex = vertices.get(startVertexKey);
        StringBuilder result = new StringBuilder();

        if (startVertex != null) {
            Queue<VertexList<T, K>> queue = new LinkedList<>();
            queue.add(startVertex);
            startVertex.setVisited(true);

            while (!queue.isEmpty()) {
                VertexList<T, K> currentVertex = queue.poll();
                result.append("Visited: ").append(currentVertex.getId()).append("\n");

                for (VertexList<T, K> adjacentVertex : currentVertex.getAdyacencyList()) {
                    if (!adjacentVertex.isVisited()) {
                        queue.add(adjacentVertex);
                        adjacentVertex.setVisited(true);
                    }
                }
            }
            resetVisitedState();
        } else {
            result.append("Vertex not found.");
        }
        return result.toString();
    }

    @Override
    public String dfs(K startVertexKey) {
        VertexList<T, K> startVertex = vertices.get(startVertexKey);
        StringBuilder result = new StringBuilder();

        if (startVertex != null) {
            Stack<VertexList<T, K>> stack = new Stack<>();
            stack.push(startVertex);

            while (!stack.isEmpty()) {
                VertexList<T, K> currentVertex = stack.pop();

                if (!currentVertex.isVisited()) {
                    result.append("Visited: ").append(currentVertex.getId()).append("\n");
                    currentVertex.setVisited(true);

                    for (VertexList<T, K> adjacentVertex : currentVertex.getAdyacencyList()) {
                        if (!adjacentVertex.isVisited()) {
                            stack.push(adjacentVertex);
                        }
                    }
                }
            }
            resetVisitedState();
        } else {
            result.append("Vertex not found.");
        }

        return result.toString();
    }

    public List<VertexList<T, K>> dijkstra(K startVertexId, K endVertexId) {
        VertexList<T, K> startVertex = vertices.get(startVertexId);
        VertexList<T, K> endVertex = vertices.get(endVertexId);

        if (startVertex == null || endVertex == null) {
            System.out.println("Error: One or both vertices not found.");
            return Collections.emptyList();
        }
        Map<K, Integer> distances = new HashMap<>();
        Map<K, List<VertexList<T, K>>> shortestPaths = new HashMap<>();

        for (VertexList<T, K> vertex : vertices.values()) {
            distances.put(vertex.getId(), Integer.MAX_VALUE);
            shortestPaths.put(vertex.getId(), new ArrayList<>());
            vertex.setVisited(false);
        }
        distances.put(startVertexId, 0);
        shortestPaths.get(startVertexId).add(startVertex);

        PriorityQueue<VertexList<T, K>> priorityQueue = new PriorityQueue<>(Comparator.comparingInt(distances::get));
        priorityQueue.add(startVertex);

        while (!priorityQueue.isEmpty()) {
            VertexList<T, K> currentVertex = priorityQueue.poll();
            if (!currentVertex.isVisited()) {
                currentVertex.setVisited(true);
                for (Edge<T, K> edge : currentVertex.getAristas()) {
                    VertexList<T, K> neighbor = edge.getDestinationList(); // Corregir aquí
                    if (!neighbor.isVisited()) {
                        int newDistance = distances.get(currentVertex.getId()) + edge.getWeight();
                        if (newDistance < distances.get(neighbor.getId())) {
                            distances.put(neighbor.getId(), newDistance);
                            List<VertexList<T, K>> currentPath = new ArrayList<>(shortestPaths.get(currentVertex.getId()));
                            currentPath.add(neighbor);
                            shortestPaths.put(neighbor.getId(), currentPath);

                            priorityQueue.add(neighbor);
                        }
                    }
                }
            }
        }

        return shortestPaths.get(endVertexId);
    }

    private void resetVisitedState() {
        for (VertexList<T, K> vertex : vertices.values()) {
            vertex.setVisited(false);
        }
    }
}

