import java.util.List;

public interface IGraph<T> {
    public void addVertex(String Key,T data);
    public void addEdge(String sourceKey, String destinationKey, int weight); 
    public String bfs(String startVertexKey);
    public String dfs(String startVertexKey);
    public List<Vertex<T>> dijkstra(String startVertexKey);
}
