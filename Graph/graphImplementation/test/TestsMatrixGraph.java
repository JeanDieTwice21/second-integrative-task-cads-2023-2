package graphImplementation.test;
import graphImplementation.CustomExceptions.InvalidEntriesException;
import graphImplementation.classes.*;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;

public class TestsMatrixGraph {
    private GraphMatriz<String> graph;
    private GraphMatriz<String> complexGraph;
    GraphMatriz<String> emptyGraph;
    @Before
    public void setUp1() {
        graph = new GraphMatriz<>();
        graph.addVertex("A", "Data A");
    }
    @Test
    public void testAddVertex() {
        setUp1();
        assertEquals(1, graph.getNumVertices());
        try {
            graph.addVertex("A", "Data B");
            fail("Expected exception, but no exception was thrown.");
        } catch (InvalidEntriesException e) {
            assertEquals("Vertex with the same key already exists.", e.getMessage());
        }

        try {
            graph.addVertex("B", null);
            fail("Expected exception, but no exception was thrown.");
        } catch (InvalidEntriesException e) {
            assertEquals("Vertex data cannot be null.", e.getMessage());
        }

        try {
            graph.addVertex("C", "Data A");
            fail("Expected exception, but no exception was thrown.");
        } catch (InvalidEntriesException e) {
            assertEquals("Vertex with the same data already exists.", e.getMessage());
        }
    }
    @Before
    public void setUp2() {
        graph = new GraphMatriz<>();
        graph.addVertex("A", "Data A");
        graph.addVertex("B", "Data B");
        graph.addEdge("A", "B", 1);
    }
    @Test
    public void testAddEdge() {
        setUp2();
        assertEquals(1, graph.getEdgeWeight("A", "B"));
        try {
            graph.addEdge("A", "B", -1);
            fail("Expected GraphException, but no exception was thrown.");
        } catch (InvalidEntriesException e) {
            assertEquals("Edge weight cannot be negative.", e.getMessage());
        }
        try {
            graph.addEdge("A", "C", 2);
            fail("Expected IllegalArgumentException, but no exception was thrown.");
        } catch (IllegalArgumentException e) {
            assertEquals("One or both vertices not found in the graph.", e.getMessage());
        }
        try {
            graph.addEdge("A", "A", 3);
            fail("Expected GraphException, but no exception was thrown.");
        } catch (InvalidEntriesException e) {
            assertEquals("Cannot add an edge that points to itself.", e.getMessage());
        }
    }
    @Before
    public void setUp3() {
        graph = new GraphMatriz<>();
        graph.addVertex("A", "Data A");
        graph.addVertex("B", "Data B");
        graph.addVertex("C", "Data C");
    }
    @Before
    public void setUp4() {
        graph = new GraphMatriz<>();
        graph.addVertex("A", "Data A");
        graph.addVertex("B", "Data B");
        graph.addVertex("C", "Data C");
        graph.addEdge("A", "B", 1);
        graph.addEdge("B", "C", 1);
        graph.addEdge("A", "C", 1);
    }
    public void setUp5(){
        graph.addVertex("X", "Data X");
        graph.addVertex("Y", "Data Y");
        graph.addVertex("Z", "Data Z");
        graph.addEdge("X", "Y", 1);
        graph.addEdge("Y", "Z", 2);
        graph.addEdge("Z", "X", 3);
    }
    @Test
    public void testDijkstra() {
        setUp3();
        List<Vertex<String>> shortestPath1 = graph.dijkstra("A", "C");
        assertTrue(shortestPath1.isEmpty());
        setUp4();
        List<Vertex<String>> shortestPath2 = graph.dijkstra("A", "C");
        assertEquals(2, shortestPath2.size());
        assertEquals("A", shortestPath2.get(0).getId());
        assertEquals("C", shortestPath2.get(1).getId());
        setUp5();
        List<Vertex<String>> shortestPath3 = graph.dijkstra("X", "Z");
        assertEquals(3, shortestPath3.size());
        assertEquals("X", shortestPath3.get(0).getId());
        assertEquals("Y", shortestPath3.get(1).getId());
        assertEquals("Z", shortestPath3.get(2).getId());
    }
    @Before
    public void setUp7(){
        complexGraph = new GraphMatriz<>();
        complexGraph.addVertex("A", "Data A");
        complexGraph.addVertex("B", "Data B");
        complexGraph.addVertex("C", "Data C");
        complexGraph.addEdge("A", "B", 1);
        complexGraph.addEdge("B", "C", 2);
        complexGraph.addEdge("C", "A", 3);
    }
    public void setUp6(){
        complexGraph = new GraphMatriz<>();
        complexGraph.addVertex("Q", "Data Q");
    }
    @Test
    public void testPrintAdjacencyMatrix(){
        GraphMatriz<String> emptyGraph = new GraphMatriz<>();
        String adjacencyMatrixString = emptyGraph.getAdjacencyMatrixAsString();
        assertEquals("Adjacency Matrix:\n", adjacencyMatrixString);
        setUp6();
        String adjacencyMatrixSingleString = complexGraph.getAdjacencyMatrixAsString();
        assertEquals("Adjacency Matrix:\n   Q \nQ -1 \n", adjacencyMatrixSingleString);
        setUp7();
        String adjacencyMatrixAsString = complexGraph.getAdjacencyMatrixAsString();
        assertEquals("Adjacency Matrix:\n   A B C \nA -1 1 -1 \nB -1 -1 2 \nC 3 -1 -1 \n", adjacencyMatrixAsString );
    }
    @Before
    public void setUp8(){
        complexGraph = new GraphMatriz<>();
        complexGraph.addVertex("A", "Vertex A");
        complexGraph.addVertex("B", "Vertex B");
        complexGraph.addVertex("C", "Vertex C");
        complexGraph.addVertex("D", "Vertex D");
        complexGraph.addVertex("E", "Vertex E");

        complexGraph.addEdge("A", "B", 2);
        complexGraph.addEdge("A", "C", 4);
        complexGraph.addEdge("B", "D", 10);
        complexGraph.addEdge("C", "D", 1);
        complexGraph.addEdge("D", "E", 7);
    }
    @Before
    public void setUp9(){
        emptyGraph = new GraphMatriz<>();
    }
    public void setUp10(){
        complexGraph = new GraphMatriz<>();
        complexGraph.addVertex("A", "Data A");
        complexGraph.addVertex("B", "Data B");
        complexGraph.addVertex("C", "Data C");
        complexGraph.addEdge("A", "B", 1);
        complexGraph.addEdge("B", "C", 4);
    }
    @Test
    public void testDfs(){
        setUp8();
        String dfsResult = complexGraph.dfs("A");
        String expectedDfsResult = "Visited: A\nVisited: C\nVisited: D\nVisited: E\nVisited: B\n";
        assertEquals(expectedDfsResult, dfsResult);
        setUp9();
        String dfsEmptyResult = emptyGraph.dfs("A");
        assertEquals("Error: Start vertex not found.\n", dfsEmptyResult);
        setUp10();
        String dfsDisconnectedResult = complexGraph.dfs("C");
        assertEquals("Visited: C\n", dfsDisconnectedResult);
    }
    @Test
    public void testBfs(){
        setUp9();
        String bfsResult = emptyGraph.bfs("A");
        assertEquals("Error: Start vertex not found.\n", bfsResult);
        setUp8();
        String bfsConnectedResult = complexGraph.bfs("A");
        String expectedBfsResult = "Visited: A\nVisited: B\nVisited: C\nVisited: D\nVisited: E\n";
        assertEquals(expectedBfsResult, bfsConnectedResult);
        setUp10();
        String dfsDisconnectedResult = complexGraph.bfs("C");
        assertEquals("Visited: C\n", dfsDisconnectedResult);
    }


}
