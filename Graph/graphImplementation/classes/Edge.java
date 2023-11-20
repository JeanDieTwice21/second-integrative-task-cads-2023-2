package graphImplementation.classes;

public class Edge<T> implements Comparable<Edge<T>> {
    private Vertex<T> destination;
    private int weight;
    private VertexList<T> destinationList;

    public Edge(Vertex<T> destination, int weight) {
        this.destination = destination;
        this.weight = weight;
    }
    public Edge(VertexList<T> destination, int weight) {
        this.destinationList = destination;
        this.weight = weight;
    }
    public Vertex<T> getDestination() {
        return destination;
    }
     public VertexList<T> getDestinationList() {
        return destinationList;
    }
    public int getWeight() {
        return weight;
    }
    @Override
    public int compareTo(Edge<T> other) {
        return Integer.compare(this.weight, other.weight);
    }
}
