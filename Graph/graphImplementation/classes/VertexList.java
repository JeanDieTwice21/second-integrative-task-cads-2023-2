package graphImplementation.classes;

import java.util.ArrayList;
import java.util.List;

public class VertexList<T> {
    private T id;
    private T data;
    private boolean isVisited;
    private List<Edge<T>> aristas;
    private List<VertexList<T>> adyacencyList;

    public VertexList(T id, T data) {
        this.id = id;
        this.data = data;
        this.isVisited = false;
        this.aristas = new ArrayList<>();
        this.adyacencyList = new ArrayList<>();
    }
    public T getId() {
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
    public List<Edge<T>> getAristas() {
        return aristas;
    }
    public void addEdge(VertexList<T> destiny, int weight) {
        Edge<T> edge = new Edge<>(destiny, weight);
        aristas.add(edge);
    }
    public void addVertex(VertexList<T> vertex) {
        adyacencyList.add(vertex);
    }
    public List<VertexList<T>> getAdyacencyList(){
        return adyacencyList;
    }
}
