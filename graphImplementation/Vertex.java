
import java.util.ArrayList;
import java.util.List;

public class Vertex<T> {
    private String id;
    private T data;
    private boolean isVisited;
    private List<Edge<T>> aristas;
    private int index;

    public Vertex(String id, T data, int index) {
        this.id = id;
        this.data = data;
        this.isVisited = false;
        this.aristas = new ArrayList<>();
        this.index = index;
    }
    public String getId() {
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
    public void addEdge(Vertex<T> destiny, int weight) {
        Edge<T> edge = new Edge<>(destiny, weight);
        aristas.add(edge);
    }
    public int getIndex() {
        return index;
    }
}