import java.util.ArrayList;
import java.util.List;

public class Vertex<T> {
    private T data;
    private int weight;
    private boolean isVisited;
    private List<Edge<T>> edges;

    public Vertex(T data, int weight) {
        this.data = data;
        this.weight = weight;
        this.isVisited = false;
        this.edges = new ArrayList<>(); 
    }

    public T getData() {
        return data;
    }

    public int getWeight() {
        return weight;
    }

    public boolean isVisited() {
        return isVisited;
    }

    public void setVisited(boolean visited) {
        this.isVisited = visited;
    }

    public List<Edge<T>> getEdges() {
        return edges;
    }

    public void addEdge(Edge<T> edge) {
        edges.add(edge);
    }
}