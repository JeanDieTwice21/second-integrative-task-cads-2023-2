package graphImplementation.test;
import graphImplementation.CustomExceptions.InvalidEntriesException;
import graphImplementation.classes.*;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class TestsMatrixGraph {
    private GraphMatriz<String,String> graph;
    private GraphMatriz<String,String> complexGraph;
    GraphMatriz<String,String> emptyGraph;
    @Before
    public void setUp1() {
        graph = new GraphMatriz<>();
        graph.addVertex("A", "Data A");
    }

    @Test
    public void testAddVertexWithSameKey() {
        setUp1();
        assertEquals(1, graph.getNumVertices());
        try {
            graph.addVertex("A", "Data B");
            fail("Expected exception, but no exception was thrown.");
        } catch (InvalidEntriesException e) {
            assertEquals("Vertex with the same key already exists.", e.getMessage());
        }
    }
    @Test
    public void testAddVertexWithNullData() {
        setUp1();
        assertEquals(1, graph.getNumVertices());
        try {
            graph.addVertex("B", null);
            fail("Expected exception, but no exception was thrown.");
        } catch (InvalidEntriesException e) {
            assertEquals("Vertex data cannot be null.", e.getMessage());
        }
    }
    @Test
    public void testAddVertexWithSameData() {
        setUp1();
        assertEquals(1, graph.getNumVertices());
        try {
            graph.addVertex("C", "Data A");
            fail("Expected exception, but no exception was thrown.");
        } catch (InvalidEntriesException e) {
            assertEquals("Vertex with the same data already exists.", e.getMessage());
        }
    }
    @Before
    public void settingUpEdges() {
        graph = new GraphMatriz<>();
        graph.addVertex("A", "Data A");
        graph.addVertex("B", "Data B");
        graph.addEdge("A", "B", 1);
    }
    @Test
    public void testAddEdgeWithNegativeWeight() {
        settingUpEdges();
        assertEquals(1, graph.getEdgeWeight("A", "B"));
        try {
            graph.addEdge("A", "B", -1);
            fail("Expected GraphException, but no exception was thrown.");
        } catch (InvalidEntriesException e) {
            assertEquals("Edge weight cannot be negative.", e.getMessage());
        }
    }
    @Test
    public void testAddEdgeWithNonexistentVertex() {
        settingUpEdges();
        assertEquals(1, graph.getEdgeWeight("A", "B"));
        try {
            graph.addEdge("A", "C", 2);
            fail("Expected IllegalArgumentException, but no exception was thrown.");
        } catch (IllegalArgumentException e) {
            assertEquals("One or both vertices not found in the graph.", e.getMessage());
        }
    }
    @Test
    public void testAddEdgeToPointingItself() {
        settingUpEdges();
        assertEquals(1, graph.getEdgeWeight("A", "B"));
        try {
            graph.addEdge("A", "A", 3);
            fail("Expected GraphException, but no exception was thrown.");
        } catch (InvalidEntriesException e) {
            assertEquals("Cannot add an edge that points to itself.", e.getMessage());
        }
    }
    @Before
    public void setUpForDijkstra() {
        graph = new GraphMatriz<>();
        graph.addVertex("A", "Data A");
        graph.addVertex("B", "Data B");
        graph.addVertex("C", "Data C");
    }
    @Before
    public void setUpForDijkstra1() {
        graph = new GraphMatriz<>();
        graph.addVertex("A", "Data A");
        graph.addVertex("B", "Data B");
        graph.addVertex("C", "Data C");
        graph.addEdge("A", "B", 1);
        graph.addEdge("B", "C", 1);
        graph.addEdge("A", "C", 1);
    }
    public void setUpForDijkstra2(){
        graph = new GraphMatriz<>();
        graph.addVertex("X", "Data X");
        graph.addVertex("Y", "Data Y");
        graph.addVertex("Z", "Data Z");
        graph.addVertex("A", "Data A");
        graph.addVertex("B", "Data B");
        graph.addVertex("C", "Data C");
        graph.addEdge("X", "Y", 4);
        graph.addEdge("Y", "Z", 5);
        graph.addEdge("Z", "X", 1);
        graph.addEdge("X", "A", 1);
        graph.addEdge("A", "B", 1);
        graph.addEdge("B", "Z", 1);
    }
    @Test
    public void testDijkstra() {
        setUpForDijkstra();
        String dijkstraResult1 = graph.dijkstra("A", "C");
        assertEquals("No path found.", dijkstraResult1);

        setUpForDijkstra1();
        String dijkstraResult2 = graph.dijkstra("A", "C");
        assertEquals("A -> C", dijkstraResult2);

        setUpForDijkstra2();
        String dijkstraResult3 = graph.dijkstra("X", "Z");
        assertEquals("X -> A -> B -> Z", dijkstraResult3);
    }
    @Before
    public void settingUpForMatrix(){
        emptyGraph = new GraphMatriz<String,String>();
        complexGraph = new GraphMatriz<>();
        complexGraph.addVertex("A", "Data A");
        complexGraph.addVertex("B", "Data B");
        complexGraph.addVertex("C", "Data C");
        complexGraph.addEdge("A", "B", 1);
        complexGraph.addEdge("B", "C", 2);
        complexGraph.addEdge("C", "A", 3);
    }
    public void settingUpForMatrix1(){
        complexGraph = new GraphMatriz<>();
        complexGraph.addVertex("Q", "Data Q");
    }
    @Test
    public void testPrintAdjacencyMatrix(){
        settingUpForMatrix();
        String adjacencyMatrixString = emptyGraph.getAdjacencyMatrixAsString();
        assertEquals("Adjacency Matrix:\n", adjacencyMatrixString);
        settingUpForMatrix1();
        String adjacencyMatrixSingleString = complexGraph.getAdjacencyMatrixAsString();
        assertEquals("Adjacency Matrix:\n   Q \nQ -1 \n", adjacencyMatrixSingleString);
        settingUpForMatrix();
        String adjacencyMatrixAsString = complexGraph.getAdjacencyMatrixAsString();
        assertEquals("Adjacency Matrix:\n   A B C \nA -1 1 -1 \nB -1 -1 2 \nC 3 -1 -1 \n", adjacencyMatrixAsString );
    }
    @Before
    public void settingUpTrasversal(){
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
    public void settingUpTrasversal1(){
        emptyGraph = new GraphMatriz<>();
    }
    public void settingUpTrasversal2(){
        complexGraph = new GraphMatriz<>();
        complexGraph.addVertex("A", "Data A");
        complexGraph.addVertex("B", "Data B");
        complexGraph.addVertex("C", "Data C");
        complexGraph.addEdge("A", "B", 1);
        complexGraph.addEdge("B", "C", 4);
    }
    @Test
    public void testDfs(){
        settingUpTrasversal();
        String dfsResult = complexGraph.dfs("A");
        String expectedDfsResult = "Visited: A\nVisited: C\nVisited: D\nVisited: E\nVisited: B\n";
        assertEquals(expectedDfsResult, dfsResult);
        settingUpTrasversal1();
        String dfsEmptyResult = emptyGraph.dfs("A");
        assertEquals("Error: Start vertex not found.\n", dfsEmptyResult);
        settingUpTrasversal2();
        String dfsDisconnectedResult = complexGraph.dfs("C");
        assertEquals("Visited: C\n", dfsDisconnectedResult);
    }
    @Test
    public void testBfs(){
        settingUpTrasversal1();
        String bfsResult = emptyGraph.bfs("A");
        assertEquals("Error: Start vertex not found.\n", bfsResult);
        settingUpTrasversal();
        String bfsConnectedResult = complexGraph.bfs("A");
        String expectedBfsResult = "Visited: A\nVisited: B\nVisited: C\nVisited: D\nVisited: E\n";
        assertEquals(expectedBfsResult, bfsConnectedResult);
        settingUpTrasversal2();
        String dfsDisconnectedResult = complexGraph.bfs("C");
        assertEquals("Visited: C\n", dfsDisconnectedResult);
    }
}
