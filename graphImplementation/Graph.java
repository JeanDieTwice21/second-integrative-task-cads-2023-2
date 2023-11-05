import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Stack;

public class Graph<T> {

    private HashMap<T, Vertex<T>> vertexMap;
    private boolean isDirected;

    public Graph(boolean isDirected) {
        vertexMap = new HashMap<>();
        this.isDirected = isDirected;
    }
    public void addVertex(T key, Vertex<T> vertex) {
        vertexMap.put(key, vertex);
    }

    public Vertex<T> getVertex(T key) {
        return vertexMap.get(key);
    }

    public void addEdge(T sourceData, T destinationData, int weight) {
        Vertex<T> sourceVertex = vertexMap.get(sourceData);
        Vertex<T> destinationVertex = vertexMap.get(destinationData);
    
        if (sourceVertex != null && destinationVertex != null) {
            Edge<T> edge = new Edge<>(destinationVertex, weight);
            sourceVertex.addEdge(edge);
    
            if (!isDirected) {
                Edge<T> reverseEdge = new Edge<>(sourceVertex, weight);
                destinationVertex.addEdge(reverseEdge);
            }
        }
    }
    public void deleteEdge(T sourceData, T destinationData) {
        Vertex<T> sourceVertex = vertexMap.get(sourceData);
        Vertex<T> destinationVertex = vertexMap.get(destinationData);
    
        if (sourceVertex != null && destinationVertex != null) {
            Edge<T> edgeToRemove = null;
            for (Edge<T> edge : sourceVertex.getEdges()) {
                if (edge.getDestination() == destinationVertex) {
                    edgeToRemove = edge;
                    break;
                }
            }
            if (edgeToRemove != null) {
                sourceVertex.getEdges().remove(edgeToRemove);
                if (!isDirected) {
                    for (Edge<T> reverseEdge : destinationVertex.getEdges()) {
                        if (reverseEdge.getDestination() == sourceVertex) {
                            destinationVertex.getEdges().remove(reverseEdge);
                            break;
                        }
                    }
                }
            }
        }
    }
     public List<Vertex<T>> bfsRoute(T startKey) {
        Vertex<T> startVertex = getVertex(startKey);

        if (startVertex == null) {
            throw new VertexNotFoundException("Start vertex not found.");
        }
        List<Vertex<T>> visitedVertices = new ArrayList<>();
        PriorityQueue<Edge<T>> priorityQueue = new PriorityQueue<>(Comparator.comparing(Edge::getWeight));
        startVertex.setVisited(true);
        visitedVertices.add(startVertex);
        priorityQueue.addAll(startVertex.getEdges());

        while (!priorityQueue.isEmpty()) {
            Edge<T> edge = priorityQueue.poll();
            Vertex<T> currentVertex = edge.getDestination();
            
            if (!currentVertex.isVisited()) {
                currentVertex.setVisited(true);
                visitedVertices.add(currentVertex);
                priorityQueue.addAll(currentVertex.getEdges());
            }
        }
        resetVisitedStatus();
        
        return visitedVertices;
    }
    private void resetVisitedStatus() {
        for (Vertex<T> vertex : vertexMap.values()) {
            vertex.setVisited(false);
        }
    }
    public List<Vertex<T>> dfsRoute(T startKey, T targetKey) {
        Vertex<T> startVertex = getVertex(startKey);
        Vertex<T> targetVertex = getVertex(targetKey);

        if (startVertex == null || targetVertex == null) {
            throw new VertexNotFoundException("Start or target vertex not found.");
        }
        List<Vertex<T>> visitedVertices = new ArrayList<>();
        Stack<Vertex<T>> stack = new Stack<>();
        stack.push(startVertex);

        while (!stack.isEmpty()) {
            Vertex<T> currentVertex = stack.pop();

            if (!currentVertex.isVisited()) {
                currentVertex.setVisited(true);
                visitedVertices.add(currentVertex);

                if (currentVertex == targetVertex) {
                    break;
                }
                for (Edge<T> edge : currentVertex.getEdges()) {
                    Vertex<T> neighbor = edge.getDestination();
                    if (!neighbor.isVisited()) {
                        stack.push(neighbor);
                    }
                }
            }
        }
        resetVisitedStatus();

        return visitedVertices;
    }
    public int[][] generateAdjacencyMatrix() {
        int numVertices = vertexMap.size();
        int[][] adjacencyMatrix = new int[numVertices][numVertices];
        for (int i = 0; i < numVertices; i++) {
            for (int j = 0; j < numVertices; j++) {
                adjacencyMatrix[i][j] = 0;
            }
        }
        for (Vertex<T> sourceVertex : vertexMap.values()) {
            int sourceIndex = getIndexForVertex(sourceVertex);
            for (Edge<T> edge : sourceVertex.getEdges()) {
                Vertex<T> destinationVertex = edge.getDestination();
                int destIndex = getIndexForVertex(destinationVertex);
                int weight = edge.getWeight();
    
                adjacencyMatrix[sourceIndex][destIndex] = weight;
    
                if (!isDirected) {
                    adjacencyMatrix[destIndex][sourceIndex] = weight;
                }
            }
        }
    
        return adjacencyMatrix;
    }
    
    private int getIndexForVertex(Vertex<T> vertex) {
        return new ArrayList<>(vertexMap.values()).indexOf(vertex);
    }

    public List<Vertex<T>> getAdjacencyList(Vertex<T> vertex) {
        if (vertex == null) {
            throw new VertexNotFoundException("Vertex not found.");
        }
    
        List<Vertex<T>> adjacencyList = new ArrayList<>();
    
        for (Edge<T> edge : vertex.getEdges()) {
            Vertex<T> neighborVertex = edge.getDestination();
            adjacencyList.add(neighborVertex);
        }
        return adjacencyList;
    }
    
    public Map<Vertex<T>, Integer> dijkstraShortestPath(T sourceData) {
        Vertex<T> sourceVertex = getVertex(sourceData);
        if (sourceVertex == null) {
            throw new VertexNotFoundException("Source vertex not found.");
        }

        Map<Vertex<T>, Integer> distanceMap = new HashMap<>();
        PriorityQueue<Vertex<T>> priorityQueue = new PriorityQueue<>(Comparator.comparing(distanceMap::get));

        distanceMap.put(sourceVertex, 0);
        priorityQueue.add(sourceVertex);

        while (!priorityQueue.isEmpty()) {
            Vertex<T> currentVertex = priorityQueue.poll();
            int currentDistance = distanceMap.get(currentVertex);

            for (Edge<T> edge : currentVertex.getEdges()) {
                Vertex<T> neighborVertex = edge.getDestination();
                int weight = edge.getWeight();
                int newDistance = currentDistance + weight;

                if (newDistance < distanceMap.getOrDefault(neighborVertex, Integer.MAX_VALUE)) {
                    distanceMap.put(neighborVertex, newDistance);
                    priorityQueue.add(neighborVertex);
                }
            }
        }
        return distanceMap;
    }



}