package graphImplementation.test;
import graphImplementation.CustomExceptions.InvalidEntriesException;
import graphImplementation.classes.*;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
public class TestListGraph {
    private GraphList<String, Integer> graph;

    @Before
    public void setUp() {
        graph = new GraphList<>();
    }
    @Test
    public void testAddVertex_ValidVertex() {
        setUp();
        graph.addVertex("A", 1);
        assertNotNull(graph.getAdjacencyListsAsString());
        assertEquals(1, graph.getAdjacencyListsAsString().split("\n").length);
    }

    @Test
    public void testAddVertex_DuplicateVertex() {
        setUp();
        graph.addVertex("A", 1);
        graph.addVertex("A", 2);
        assertEquals(1, graph.getAdjacencyListsAsString().split("\n").length);
    }

    @Test
    public void testAddVertex_AddMultipleVertices() {
        setUp();
        graph.addVertex("A", 1);
        graph.addVertex("B", 2);
        graph.addVertex("C", 3);
        assertEquals(3, graph.getAdjacencyListsAsString().split("\n").length);
    }
    @Test
    public void testAddVertex_WithNullData() {
        setUp();

        graph.addVertex("A", 1);
        graph.addVertex("B", 2);

        graph.addVertex("C", null);

        assertNull(graph.getVertices().get("C"));
    }
    @Test
    public void testAddEdge_AddValidEdge() {
        setUp();
        graph.addVertex("A", 1);
        graph.addVertex("B", 2);


        graph.addEdge("A", "B", 5);
        graph.addEdge("B", "A", 5);

        VertexList<Integer, String> vertexA = graph.getVertices().get("A");
        VertexList<Integer, String> vertexB = graph.getVertices().get("B");

        assertNotNull(vertexA);
        assertNotNull(vertexB);

        assertEquals(1, vertexA.getAdjacencyList().size());
        assertEquals(1, vertexB.getAdjacencyList().size());

        Edge<Integer, String> edge = vertexA.getAristas().get(0);
        assertEquals(vertexB, edge.getDestinationList());
        assertEquals(5, edge.getWeight());
    }

    @Test
    public void testAddEdge_SourceVertexNotFound() {
        setUp();
        graph.addVertex("B", 2);
        try {
            graph.addEdge("A", "B", 5);
            fail("Expected IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            assertEquals("Vertex not found.", e.getMessage());
        }
    }

    @Test
    public void testAddEdge_DestinationVertexNotFound() {
        setUp();
        graph.addVertex("A", 1);
        try {
            graph.addEdge("A", "B", 5);
            fail("Expected IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            assertEquals("Vertex not found.", e.getMessage());
        }
    }
    @Test
    public void testBFS_LinearGraph() {
        setUp();
        graph.addVertex("A", 1);
        graph.addVertex("B", 2);
        graph.addVertex("C", 3);
        graph.addVertex("D", 4);


        graph.addEdge("A", "B", 1);
        graph.addEdge("B", "C", 1);
        graph.addEdge("C", "D", 1);


        String result = graph.bfs("A");

        assertEquals("Visited: A\nVisited: B\nVisited: C\nVisited: D\n", result);
    }

    @Test
    public void testBFS_BranchedGraph() {
        setUp();
        graph.addVertex("A", 1);
        graph.addVertex("B", 2);
        graph.addVertex("C", 3);
        graph.addVertex("D", 4);
        graph.addVertex("E", 5);
        graph.addVertex("F", 6);
        graph.addEdge("A", "B", 1);
        graph.addEdge("B", "C", 2);
        graph.addEdge("A", "D", 3);
        graph.addEdge("B", "E", 4);
        graph.addEdge("B", "F", 5);
        String result = graph.bfs("A");

        assertEquals("Visited: A\nVisited: B\nVisited: D\nVisited: C\nVisited: E\nVisited: F\n", result);
    }

    @Test
    public void testBFS_DisconnectedGraph() {
        setUp();
        graph.addVertex("A", 1);
        graph.addVertex("B", 2);
        graph.addVertex("C", 3);
        graph.addVertex("D", 4);

        graph.addEdge("A", "B", 1);
        graph.addEdge("C", "D", 1);

        String result = graph.bfs("A");

        assertEquals("Visited: A\nVisited: B\n", result);
    }
    @Test
    public void testDFS_SimpleGraph() {
        setUp();
        graph.addVertex("A", 1);
        graph.addVertex("B", 2);
        graph.addVertex("C", 3);
        graph.addVertex("D", 4);


        graph.addEdge("A", "B", 1);
        graph.addEdge("B", "C", 1);
        graph.addEdge("C", "D", 1);


        String result = graph.dfs("A");


        assertEquals("Visited: A\nVisited: B\nVisited: C\nVisited: D\n", result);
    }

    @Test
    public void testDFS_BranchedGraphWithWeights() {
        setUp();
        graph.addVertex("A", 1);
        graph.addVertex("B", 2);
        graph.addVertex("C", 3);
        graph.addVertex("D", 4);
        graph.addVertex("E", 5);
        graph.addVertex("F", 6);


        graph.addEdge("A", "B", 1);
        graph.addEdge("A", "C", 2);
        graph.addEdge("A", "D", 3);
        graph.addEdge("A", "E", 4);
        graph.addEdge("A", "F", 5);


        graph.addEdge("B", "C", 2);
        graph.addEdge("B", "E", 4);
        graph.addEdge("B", "F", 5);

        String result = graph.dfs("A");

        assertEquals("Visited: A\nVisited: B\nVisited: C\nVisited: E\nVisited: F\nVisited: D\n", result);
    }

    @Test
    public void testDFS_DisconnectedGraphWithWeights() {
        setUp();
        graph.addVertex("A", 1);
        graph.addVertex("B", 2);
        graph.addVertex("C", 3);
        graph.addVertex("D", 4);

        graph.addEdge("A", "B", 1);
        graph.addEdge("C", "D", 2);

        String result = graph.dfs("A");

        assertEquals("Visited: A\nVisited: B\n", result);
    }
    @Test
    public void testDFS_BranchedGraphWithWeights1() {
        setUp();
        graph.addVertex("A", 1);
        graph.addVertex("B", 2);
        graph.addVertex("C", 3);
        graph.addVertex("D", 4);
        graph.addVertex("E", 5);
        graph.addVertex("F", 6);


        graph.addEdge("A", "B", 1);
        graph.addEdge("B", "C", 2);
        graph.addEdge("A", "D", 3);
        graph.addEdge("B", "E", 4);
        graph.addEdge("B", "F", 5);


        String result = graph.dfs("A");

        assertEquals("Visited: A\nVisited: B\nVisited: C\nVisited: E\nVisited: F\nVisited: D\n", result);
    }
    @Test
    public void testDijkstra_ShortestPathExists_MultipleVertices() {
        setUp();
        graph.addVertex("A", 1);
        graph.addVertex("B", 2);
        graph.addVertex("C", 3);
        graph.addVertex("D", 4);
        graph.addVertex("E", 5);

        graph.addEdge("A", "B", 2);
        graph.addEdge("B", "C", 1);
        graph.addEdge("C", "D", 3);
        graph.addEdge("D", "E", 2);

        String result = graph.dijkstra("A", "E");

        assertEquals("Shortest path from A to E: A -> B -> C -> D -> E", result);
    }
    @Test
    public void testDijkstra_NoPathExists() {
        setUp();
        graph.addVertex("A", 1);
        graph.addVertex("B", 2);
        graph.addVertex("C", 3);
        graph.addVertex("D", 4);

        graph.addEdge("A", "B", 2);
        graph.addEdge("B", "C", 1);


        String result = graph.dijkstra("A", "D");


        assertEquals("Shortest path from A to D: No path found.", result);
    }

    @Test
    public void testDijkstra_DisconnectedVertices() {
        setUp();
        graph.addVertex("A", 1);
        graph.addVertex("B", 2);
        graph.addVertex("C", 3);
        graph.addVertex("D", 4);

        graph.addEdge("A", "B", 2);
        graph.addEdge("C", "D", 1);


        String result = graph.dijkstra("A", "C");

        assertEquals("Shortest path from A to C: No path found.", result);
    }
    @Test
    public void testGetAdjacencyListsAsString_LinearGraph() {
        setUp();
        graph.addVertex("A", 1);
        graph.addVertex("B", 2);
        graph.addVertex("C", 3);
        graph.addVertex("D", 4);

        graph.addEdge("A", "B", 2);
        graph.addEdge("B", "C", 1);
        graph.addEdge("C", "D", 3);

        String result = graph.getAdjacencyListsAsString();

        assertEquals("Adjacency list for vertex A: B (2) \nAdjacency list for vertex D: \nAdjacency list for vertex C: D (3) \nAdjacency list for vertex B: C (1) \n", result);
    }

    @Test
    public void testGetAdjacencyListsAsString_BranchedGraph() {
        setUp();
        graph.addVertex("A", 1);
        graph.addVertex("B", 2);
        graph.addVertex("C", 3);
        graph.addVertex("D", 4);
        graph.addVertex("E", 5);
        graph.addVertex("F", 6);

        graph.addEdge("A", "B", 2);
        graph.addEdge("B", "C", 1);
        graph.addEdge("A", "D", 3);
        graph.addEdge("B", "E", 4);
        graph.addEdge("B", "F", 5);
        graph.addEdge("C", "D", 2);
        graph.addEdge("D", "E", 1);

        String result = graph.getAdjacencyListsAsString();

        assertEquals("Adjacency list for vertex A: B (2) D (3) \nAdjacency list for vertex F: \nAdjacency list for vertex E: \nAdjacency list for vertex D: E (1) \nAdjacency list for vertex C: D (2) \nAdjacency list for vertex B: C (1) E (4) F (5) \n", result);
    }
    @Test
    public void testGetAdjacencyListsAsString_DisconnectedGraph() {
        setUp();
        graph.addVertex("A", 1);
        graph.addVertex("B", 2);
        graph.addVertex("C", 3);
        graph.addVertex("D", 4);

        String result = graph.getAdjacencyListsAsString();

        assertEquals("Adjacency list for vertex A: \nAdjacency list for vertex D: \nAdjacency list for vertex C: \nAdjacency list for vertex B: \n", result);
    }
}
