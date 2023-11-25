package graphImplementation.classes;

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

public class GraphList<K,T> implements IGraph<K,T> {
    private Hashtable<K, VertexList<T,K>> vertices;
    public GraphList() {
        this.vertices = new Hashtable<>();
    }
    /**
     * The addVertex function adds a new vertex to the graph if it doesn't already exist.
     *
     * @param key The key is a unique identifier for the vertex. It is used to access and retrieve the
     * vertex from the graph.
     * @param data The "data" parameter is of type T, which represents the data associated with the
     * vertex. It can be any type of data that you want to associate with the vertex, such as a string,
     * integer, object, etc.
     */
    @Override
    public void addVertex(K key, T data) {
        if (key != null && data != null && !vertices.containsKey(key)) {
            VertexList<T, K> vertex = new VertexList<>(key, data);
            vertices.put(key, vertex);
        }
    }
    /**
     * The addEdge function adds an edge between two vertices with a given weight.
     *
     * @param sourceKey The source key is the key of the vertex from which the edge originates. It is
     * used to retrieve the corresponding vertex from the vertices map.
     * @param destinationKey The destinationKey parameter is the key of the vertex to which the edge
     * will be added.
     * @param weight The weight parameter represents the weight or cost associated with the edge
     * between the sourceKey and destinationKey vertices. It is an integer value that indicates the
     * strength, distance, or any other measure of the relationship between the two vertices.
     */
    @Override
    public void addEdge(K sourceKey, K destinationKey, int weight) throws IllegalArgumentException{
        VertexList<T, K> origin = vertices.get(sourceKey);
        VertexList<T, K> destiny = vertices.get(destinationKey);

        if (origin != null && destiny != null) {
            origin.addEdge(destiny, weight);
            origin.addVertex(destiny);
        } else {
            throw new IllegalArgumentException("Vertex not found.");
        }
    }
    /**
     * The bfs function performs a breadth-first search starting from a specified vertex and returns a
     * string representation of the visited vertices.
     *
     * @param startVertexKey The startVertexKey parameter is the key of the vertex from which the
     * breadth-first search (BFS) should start.
     * @return The method is returning a string representation of the vertices visited during the
     * breadth-first search starting from the specified startVertexKey.
     */
    @Override
    public String bfs(K startVertexKey) {
        VertexList<T,K> startVertex = vertices.get(startVertexKey);
        StringBuilder result = new StringBuilder();

        if (startVertex != null) {
            Queue<VertexList<T,K>> queue = new LinkedList<>();
            queue.add(startVertex);
            startVertex.setVisited(true);

            while (!queue.isEmpty()) {
                VertexList<T,K> currentVertex = queue.poll();
                result.append("Visited: ").append(currentVertex.getId()).append("\n");

                for (VertexList<T,K> adjacentVertex : currentVertex.getAdjacencyList()) {
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
    /**
     * The bfs function performs a breadth-first search starting from a specified vertex and returns a
     * string representation of the visited vertices.
     *
     * @param startVertexKey The startVertexKey parameter is the key of the vertex from which the
     * breadth-first search (BFS) should start.
     * @return The method is returning a string representation of the vertices visited during the
     * breadth-first search starting from the specified startVertexKey.
     */
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
                    List<VertexList<T, K>> reversedAdjacents = new ArrayList<>(currentVertex.getAdjacencyList());
                    Collections.reverse(reversedAdjacents);

                    for (VertexList<T, K> adjacentVertex : reversedAdjacents) {
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
    /**
     * The function implements Dijkstra's algorithm to find the shortest path between two vertices in a
     * graph.
     *
     * @param startVertexId The startVertexId is the identifier of the starting vertex in the graph. It
     * represents the vertex from which the shortest path will be calculated.
     * @param endVertexId The endVertexId parameter is the identifier of the vertex where the shortest
     * path should end.
     * @return The method is returning a String, that contains the vertexes that made the shortest path.
     */
    @Override
    public String dijkstra(K startVertexId, K endVertexId) {
        VertexList<T,K> startVertex = vertices.get(startVertexId);
        VertexList<T,K> endVertex = vertices.get(endVertexId);

        if (startVertex == null || endVertex == null) {
            return "Error: One or both vertices not found.";
        }

        Map<K, Integer> distances = new HashMap<>();
        Map<K, List<VertexList<T,K>>> shortestPaths = new HashMap<>();

        for (VertexList<T,K> vertex : vertices.values()) {
            distances.put(vertex.getId(), Integer.MAX_VALUE);
            shortestPaths.put(vertex.getId(), new ArrayList<>());
            vertex.setVisited(false);
        }

        distances.put(startVertexId, 0);
        shortestPaths.get(startVertexId).add(startVertex);

        PriorityQueue<VertexList<T,K>> priorityQueue = new PriorityQueue<>(Comparator.comparingInt(distances::get));
        priorityQueue.add(startVertex);

        while (!priorityQueue.isEmpty()) {
            VertexList<T,K> currentVertex = priorityQueue.poll();
            if (!currentVertex.isVisited()) {
                currentVertex.setVisited(true);
                for (VertexList<T,K> neighbor : currentVertex.getAdjacencyList()) {
                    if (!neighbor.isVisited()) {
                        int newDistance = distances.get(currentVertex.getId()) + getWeightBetweenVertices(currentVertex, neighbor);
                        if (newDistance < distances.get(neighbor.getId())) {
                            distances.put(neighbor.getId(), newDistance);
                            List<VertexList<T,K>> currentPath = new ArrayList<>(shortestPaths.get(currentVertex.getId()));
                            currentPath.add(neighbor);
                            shortestPaths.put(neighbor.getId(), currentPath);

                            priorityQueue.add(neighbor);
                        }
                    }
                }
            }
        }
        StringBuilder result = new StringBuilder();
        result.append("Shortest path from ").append(startVertexId).append(" to ").append(endVertexId).append(": ");
        List<VertexList<T,K>> path = shortestPaths.get(endVertexId);
        if (path.isEmpty() || !path.get(0).equals(startVertex)) {
            result.append("No path found.");
        } else {
            for (VertexList<T,K> vertex : path) {
                result.append(vertex.getId()).append(" -> ");
            }
            result.delete(result.length() - 4, result.length());
        }
        return result.toString();
    }
    @Override
    public void resetVisitedState() {
        for (VertexList<T,K> vertex : vertices.values()) {
            vertex.setVisited(false);
        }
    }

    /**
     * The function returns a string representation of the adjacency lists for each vertex in a graph.
     *
     * @return The method is returning a string representation of the adjacency lists for each vertex
     * in the graph.
     */
    public String getAdjacencyListsAsString() {
        StringBuilder result = new StringBuilder();
        for (VertexList<T, K> vertex : vertices.values()) {
            result.append("Adjacency list for vertex ").append(vertex.getId()).append(": ");
            for (VertexList<T, K> adjacentVertex : vertex.getAdjacencyList()) {
                result.append(adjacentVertex.getId()).append(" (").append(getWeightBetweenVertices(vertex, adjacentVertex)).append(") ");
            }
            result.append("\n");
        }
        return result.toString();
    }
    /**
     * The function returns the weight between two vertices in a graph.
     *
     * @param source The source parameter is of type VertexList<T,K>. It represents the starting vertex
     * or node from which we want to find the weight of the edge to the destination vertex.
     * @param destination The "destination" parameter is of type VertexList<T,K>. It represents the
     * vertex to which we want to find the weight of the edge connecting it to the source vertex.
     * @return The method is returning the weight between two vertices. If there is an edge between the
     * source and destination vertices, it will return the weight of that edge. If there is no edge
     * between the vertices, it will return Integer.MAX_VALUE.
     */
    private int getWeightBetweenVertices(VertexList<T,K> source, VertexList<T,K> destination) {
        for (Edge<T,K> edge : source.getAristas()) {
            if (edge.getDestinationList().equals(destination)) {
                return edge.getWeight();
            }
        }
        return Integer.MAX_VALUE;
    }
    /**
     * The function returns a Hashtable containing VertexList objects with keys of type K.
     *
     * @return A Hashtable object containing VertexList objects.
     */
    public Hashtable<K, VertexList<T, K>> getVertices() {
        return vertices;
    }
}

