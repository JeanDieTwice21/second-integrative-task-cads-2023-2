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

public class GraphList<T> implements IGraph {
    private Hashtable<T, VertexList<T>> vertices;
    public GraphList() {
        this.vertices = new Hashtable<>();
    }
    @Override
    public void addVertex(T key, T data) {
        if (!vertices.containsKey(key)) {
            VertexList<T> vertex = new VertexList<>(key, data);
            vertices.put(key, vertex);
        }
    }
    @Override
    public void addEdge(T sourceKey, T destinationKey, int weight) {
        VertexList<T> origin = vertices.get(sourceKey);
        VertexList<T> destiny = vertices.get(destinationKey);

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
    public String bfs(T startVertexKey) {
        VertexList<T> startVertex = vertices.get(startVertexKey);
        StringBuilder result = new StringBuilder();

        if (startVertex != null) {
            Queue<VertexList<T>> queue = new LinkedList<>();
            queue.add(startVertex);
            startVertex.setVisited(true);

            while (!queue.isEmpty()) {
                VertexList<T> currentVertex = queue.poll();
                result.append("Visited: ").append(currentVertex.getId()).append("\n");

                for (VertexList<T> adjacentVertex : currentVertex.getAdyacencyList()) {
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
    public String dfs(Object startVertexKey) {
        VertexList<T> startVertex = vertices.get(startVertexKey);
        StringBuilder result = new StringBuilder();

        if (startVertex != null) {
            Stack<VertexList<T>> stack = new Stack<>();
            stack.push(startVertex);

            while (!stack.isEmpty()) {
                VertexList<T> currentVertex = stack.pop();

                if (!currentVertex.isVisited()) {
                    result.append("Visited: ").append(currentVertex.getId()).append("\n");
                    currentVertex.setVisited(true);

                    for (VertexList<T> adjacentVertex : currentVertex.getAdyacencyList()) {
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

    public List<VertexList<T>> dijkstra(T startVertexId, T endVertexId) {
        VertexList<T> startVertex = vertices.get(startVertexId);
        VertexList<T> endVertex = vertices.get(endVertexId);
    
        if (startVertex == null || endVertex == null) {
            System.out.println("Error: One or both vertices not found.");
            return Collections.emptyList();
        }
        Map<T, Integer> distances = new HashMap<>();
        Map<T, List<VertexList<T>>> shortestPaths = new HashMap<>();
        
        for (VertexList<T> vertex : vertices.values()) {
            distances.put(vertex.getId(), Integer.MAX_VALUE);
            shortestPaths.put(vertex.getId(), new ArrayList<>());
            vertex.setVisited(false);
        }
        distances.put(startVertexId, 0);
        shortestPaths.get(startVertexId).add(startVertex);
    
        PriorityQueue<VertexList<T>> priorityQueue = new PriorityQueue<>(Comparator.comparingInt(distances::get));
        priorityQueue.add(startVertex);
    
        while (!priorityQueue.isEmpty()) {
            VertexList<T> currentVertex = priorityQueue.poll();
            if (!currentVertex.isVisited()) {
                currentVertex.setVisited(true);
                for (Edge<T> edge : currentVertex.getAristas()) {
                    VertexList<T> neighbor = edge.getDestinationList();
                    if (!neighbor.isVisited()) {
                        int newDistance = distances.get(currentVertex.getId()) + edge.getWeight();
                        if (newDistance < distances.get(neighbor.getId())) {
                            distances.put(neighbor.getId(), newDistance);
                            List<VertexList<T>> currentPath = new ArrayList<>(shortestPaths.get(currentVertex.getId()));
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
        for (VertexList<T> vertex : vertices.values()) {
            vertex.setVisited(false);
        }
    }
    
}
