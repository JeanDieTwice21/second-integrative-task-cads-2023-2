import java.util.List;

public class TestMatrixGraph {
    public static void main(String[] args) {
        
        GraphMatriz<String> graphMatrix = new GraphMatriz<>();

        graphMatrix.addVertex("A", "Vertex A");
        graphMatrix.addVertex("B", "Vertex B");
        graphMatrix.addVertex("C", "Vertex C");
        graphMatrix.addVertex("D", "Vertex D");
        graphMatrix.addVertex("E", "Vertex E");

        graphMatrix.addEdge("A", "B", 2);
        graphMatrix.addEdge("A", "C", 4);
        graphMatrix.addEdge("B", "D", 10);
        graphMatrix.addEdge("C", "D", 1);
        graphMatrix.addEdge("D", "E", 7);

        System.out.println(graphMatrix.getAdjacencyMatrixAsString());
        System.out.println("BFS starting from E:");
        System.out.println(graphMatrix.bfs("E"));
        System.out.println("DFS starting from B:");
        System.out.println(graphMatrix.dfs("Z"));
        System.out.println("Dijkstra from A to E:");
        List<Vertex<String>> dijkstraResult = graphMatrix.dijkstra("A", "E");
        for (Vertex<String> vertex : dijkstraResult) {
            System.out.println(vertex.getId());
        }
    }
}
