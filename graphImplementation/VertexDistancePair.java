

public class VertexDistancePair<T> implements Comparable<VertexDistancePair<T>>  {
        private Vertex<T> vertex;
        private int distance;

        public VertexDistancePair(Vertex<T> vertex, int distance) {
            this.vertex = vertex;
            this.distance = distance;
        }

        public Vertex<T> getVertex() {
            return vertex;
        }

        @Override
        public int compareTo(VertexDistancePair<T> other) {
            return Integer.compare(this.distance, other.distance);
        }
    }

