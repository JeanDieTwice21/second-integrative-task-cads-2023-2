package graphImplementation.classes;

import java.util.List;

public interface IGraph<T,K> {
    public void addVertex(T Key,K data);
    public void addEdge(T sourceKey, T destinationKey, int weight); 
    public String bfs(T startVertexKey);
    public String dfs(T startVertexKey);
    public String dijkstra(T startVertexId, T endVertexId);
    public void resetVisitedState();
}
