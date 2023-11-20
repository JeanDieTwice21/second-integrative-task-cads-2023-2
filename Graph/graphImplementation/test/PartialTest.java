package graphImplementation.test;


import graphImplementation.classes.GraphMatriz;
import graphImplementation.classes.Vertex;

import java.util.List;

public class PartialTest {
    public static void main(String[] args) {

        GraphMatriz<String> graph = new GraphMatriz<>();
        graph.addVertex("A", "Vertex A");
        graph.addVertex("B", "Vertex B");
        graph.addVertex("C", "Vertex C");
        graph.addVertex("D", "Vertex D");
        graph.addVertex("E", "Vertex E");

        graph.addEdge("A", "B", 2);
        graph.addEdge("A", "C", 4);
        graph.addEdge("B", "D", 10);
        graph.addEdge("C", "D", 1);
        graph.addEdge("D", "E", 7);

        System.out.println(graph.getAdjacencyMatrixAsString());
        System.out.println("BFS starting from A:");
        System.out.println(graph.bfs("A"));
        System.out.println("DFS starting from A:");
        System.out.println(graph.dfs("A"));

    }
}
