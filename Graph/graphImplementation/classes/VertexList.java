package graphImplementation.classes;

import java.util.ArrayList;
import java.util.List;

public class VertexList<T, K> {
    private K id;
    private T data;
    private boolean isVisited;
    private List<Edge<T,K>> aristas;
    private List<VertexList<T, K>> adjacencyList;

    public VertexList(K id, T data) {
        this.id = id;
        this.data = data;
        this.isVisited = false;
        this.aristas = new ArrayList<>();
        this.adjacencyList = new ArrayList<>();
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

    public void addEdge(VertexList<T, K> destiny, int weight) {
        Edge<T, K> edge = new Edge<>(destiny, weight);
        aristas.add(edge);
    }

    public void addVertex(VertexList<T, K> vertex) {
        adjacencyList.add(vertex);
    }

    public List<VertexList<T, K>> getAdjacencyList() {
        return adjacencyList;
    }
    public String getAdjacencyListAsString() {
        StringBuilder result = new StringBuilder("Adjacency list for vertex " + id + ": ");
        for (VertexList<T,K> neighbor : adjacencyList) {
            result.append(neighbor.getId()).append(" ");
        }
        return result.toString();
    }
}
