package graphImplementation.classes;

public class Edge<T, K> implements Comparable<Edge<T, K>> {
    private Vertex<T, K> destination;
    private int weight;
    private VertexList<T, K> destinationList;

    public Edge(Vertex<T, K> destination, int weight) {
        this.destination = destination;
        this.weight = weight;
    }

    public Edge(VertexList<T, K> destinationList, int weight) {
        this.destinationList = destinationList;
        this.weight = weight;
    }

    public Vertex<T, K> getDestination() {
        return destination;
    }

    public VertexList<T, K> getDestinationList() {
        return destinationList;
    }

    public int getWeight() {
        return weight;
    }

    @Override
    public int compareTo(Edge<T, K> other) {
        return Integer.compare(this.weight, other.weight);
    }

}
