import java.util.ArrayList;
import java.util.Hashtable;
import java.util.LinkedList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Stack;

public class GraphMatriz<T> implements IGraph<T> {
    private Hashtable<String, Vertex<T>> vertices;
    private int[][] adjMatrix;
    private int numVertices;

    public GraphMatriz() {
        this.vertices = new Hashtable<>();
        this.adjMatrix = new int[numVertices][numVertices];
        this.numVertices = 0;
        for (int i = 0; i < numVertices; i++) {
            for (int j = 0; j < numVertices; j++) {
                adjMatrix[i][j] = -1;
            }
        }
    }
    /**
     * The function adds a new vertex to a graph and updates the adjacency matrix accordingly.
     * 
     * @param key The key is a unique identifier for the vertex. It is used to access and reference the
     * vertex in the graph.
     * @param data The "data" parameter is of type T, which means it can be any type of data. It is
     * used to store the data associated with the vertex.
     */
    @Override
    public void addVertex(String key, T data) {
        Vertex<T> newVertex = new Vertex<>(key, data, numVertices);
        vertices.put(key, newVertex);

        for (int i = 0; i <= numVertices; i++) {
            adjMatrix[i][numVertices] = -1;
            adjMatrix[numVertices][i] = -1;
        }

        numVertices++;
    }
    /**
     * The function adds an edge between two vertices in a graph and updates the adjacency matrix with
     * the weight of the edge.
     * 
     * @param sourceKey The key of the source vertex.
     * @param destinationKey The `destinationKey` parameter is a `String` that represents the key of
     * the vertex where the edge will be directed towards.
     * @param weight The weight parameter represents the weight or cost associated with the edge
     * between the source vertex and the destination vertex. It is an integer value that indicates the
     * strength or distance between the two vertices.
     */
    @Override
    public void addEdge(String sourceKey, String destinationKey, int weight) throws IllegalArgumentException {
        Vertex<T> sourceVertex = vertices.get(sourceKey);
        Vertex<T> destinationVertex = vertices.get(destinationKey);
        if (sourceVertex == null || destinationVertex == null) {
            throw new IllegalArgumentException("One or both vertices not found in the graph.");
        }
        sourceVertex.addEdge(destinationVertex, weight);
        int sourceIndex = sourceVertex.getIndex();
        int destinationIndex = destinationVertex.getIndex();
        adjMatrix[sourceIndex][destinationIndex] = weight;
    }
      /**
       * The bfs function performs a breadth-first search starting from a given vertex and returns a
       * string representation of the visited vertices.
       * 
       * @param startVertexKey The startVertexKey parameter is a String that represents the key of the
       * vertex from which the breadth-first search (BFS) should start.
       * @return The method is returning a string representation of the vertices visited during the
       * breadth-first search (BFS) traversal starting from the specified startVertexKey.
       */
      @Override
      public String bfs(String startVertexKey) {
        StringBuilder result = new StringBuilder();
        try {
            Vertex<T> startVertex = vertices.get(startVertexKey);

    
            if (startVertex == null) {
                throw new IllegalArgumentException("Start vertex not found.");
            }

            Queue<Vertex<T>> queue = new LinkedList<>();
            queue.add(startVertex);
            startVertex.setVisited(true);

            while (!queue.isEmpty()) {
                Vertex<T> currentVertex = queue.poll();
                result.append("Visited: ").append(currentVertex.getId()).append("\n");
                for (Edge<T> edge : currentVertex.getAristas()) {
                    Vertex<T> adjacentVertex = edge.getDestination();
                    if (!adjacentVertex.isVisited()) {
                        queue.add(adjacentVertex);
                        adjacentVertex.setVisited(true);
                    }
                }
            }
            resetVisitedState();
        } catch (IllegalArgumentException e) {
            result.append("Error: ").append(e.getMessage()).append("\n");
        }
        return result.toString();
    }
    /**
     * The function resets the visited state of all vertices in a graph.
     */
    private void resetVisitedState() {
        for (Vertex<T> vertex : vertices.values()) {
            vertex.setVisited(false);
        }
    }
   /**
    * The dfsMatrix function performs a depth-first search on a graph represented by an adjacency
    * matrix, starting from a specified vertex.
    * 
    * @param startVertexKey The startVertexKey is a String parameter that represents the key of the
    * vertex from which the Depth First Search (DFS) traversal will start.
    * @return The method is returning a string representation of the vertices visited during the Depth
    * First Search (DFS) traversal of the graph, starting from the specified start vertex.
    */
    @Override
    public String dfs(String startVertexKey) {
        StringBuilder result = new StringBuilder();
        try {
            Vertex<T> startVertex = vertices.get(startVertexKey);

            if (startVertex == null) {
                throw new IllegalArgumentException("Start vertex not found.");
            }
            Stack<Vertex<T>> stack = new Stack<>();
            stack.push(startVertex);
            startVertex.setVisited(true);
            while (!stack.isEmpty()) {
                Vertex<T> currentVertex = stack.pop();
                result.append("Visited: ").append(currentVertex.getId()).append("\n");
                int currentVertexIndex = currentVertex.getIndex();
                for (int i = 0; i < adjMatrix[currentVertexIndex].length; i++) {
                    if (adjMatrix[currentVertexIndex][i] != -1) {
                        Vertex<T> adjacentVertex = getVertexByIndex(i);

                        if (!adjacentVertex.isVisited()) {
                            stack.push(adjacentVertex);
                            adjacentVertex.setVisited(true);
                        }
                    }
                }
            }
            resetVisitedState();
        } catch (IllegalArgumentException e) {
            result.append("Error: ").append(e.getMessage()).append("\n");
        }
        return result.toString();
    }
    /**
     * The function returns a vertex object based on its index value.
     * 
     * @param index The index parameter is an integer value that represents the index of the vertex we
     * are looking for.
     * @return The method is returning a Vertex object with the specified index. If a vertex with the
     * specified index is found in the vertices map, it will be returned. Otherwise, null will be
     * returned.
     */
    private Vertex<T> getVertexByIndex(int index) {
        for (Vertex<T> vertex : vertices.values()) {
            if (vertex.getIndex() == index) {
                return vertex;
            }
        }
        return null;
    }
    
  /**
   * The function implements Dijkstra's algorithm to find the shortest path from a given start vertex
   * to all other vertices in a graph.
   * 
   * @param startVertexKey The startVertexKey parameter is a String that represents the key of the
   * starting vertex for the Dijkstra's algorithm.
   * @return The method is returning a List of Vertex objects.
   */
    @Override
    public List<Vertex<T>> dijkstra(String startVertexKey) {
             List<Vertex<T>> shortestPath = new ArrayList<>();
        try {
            Vertex<T> startVertex = vertices.get(startVertexKey);

            if (startVertex == null) {
                throw new IllegalArgumentException("Start vertex not found.");
            }

            int numVertices = vertices.size();
            int[] distances = new int[numVertices];
            int[] previousVertices = new int[numVertices];
            PriorityQueue<VertexDistancePair<T>> priorityQueue = new PriorityQueue<>();

        
            for (int i = 0; i < numVertices; i++) {
                distances[i] = Integer.MAX_VALUE;
                previousVertices[i] = -1;
            }

            int startVertexIndex = startVertex.getIndex();
            distances[startVertexIndex] = 0;
            priorityQueue.add(new VertexDistancePair<T>(startVertex, 0));

            while (!priorityQueue.isEmpty()) {
                VertexDistancePair<T> currentPair = priorityQueue.poll();
                Vertex<T> currentVertex = currentPair.getVertex();

                for (int i = 0; i < numVertices; i++) {
                    if (adjMatrix[currentVertex.getIndex()][i] != -1) {
                        int edgeWeight = adjMatrix[currentVertex.getIndex()][i];
                        int newDistance = distances[currentVertex.getIndex()] + edgeWeight;

                        if (newDistance < distances[i]) {
                            distances[i] = newDistance;
                            previousVertices[i] = currentVertex.getIndex();
                            priorityQueue.add(new VertexDistancePair<T>(getVertexByIndex(i), newDistance));
                        }
                    }
                }
            }

            for (int i = 0; i < numVertices; i++) {
                shortestPath.add(getVertexByIndex(i));
            }
        } catch (IllegalArgumentException e) {
           
            System.out.println("Error: " + e.getMessage());
        }
        return shortestPath;
    }
    
    /**
     * The function returns a string representation of an adjacency matrix for a graph.
     * 
     * @return The method is returning a string representation of the adjacency matrix.
     */
    public String getAdjacencyMatrixAsString() {
        StringBuilder matrixString = new StringBuilder("Adjacency Matrix:\n");
        int numVertices = vertices.size();

        // Construir encabezados de columnas
        matrixString.append("   ");
        for (int i = 0; i < numVertices; i++) {
            matrixString.append(getVertexByIndex(i).getId()).append(" ");
        }
        matrixString.append("\n");

        // Construir filas de la matriz
        for (int i = 0; i < numVertices; i++) {
            matrixString.append(getVertexByIndex(i).getId()).append(" ");
            for (int j = 0; j < numVertices; j++) {
                matrixString.append(adjMatrix[i][j]).append(" ");
            }
            matrixString.append("\n");
        }
        return matrixString.toString();
    }
    
}