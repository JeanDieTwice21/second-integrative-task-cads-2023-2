package graphImplementation.classes;

public interface IGraph<T> {
    public void addVertex(T Key,T data);
    public void addEdge(T sourceKey, T destinationKey, int weight); 
    public String bfs(T startVertexKey);
    public String dfs(T startVertexKey);
    
}
