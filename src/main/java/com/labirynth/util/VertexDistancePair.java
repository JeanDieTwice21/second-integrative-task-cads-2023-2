package com.labirynth.util;

public class VertexDistancePair<T, K> implements Comparable<VertexDistancePair<T, K>> {
    private Vertex<T, K> vertex;
        private int distance;

        public VertexDistancePair(Vertex<T, K> vertex, int distance) {
            this.vertex = vertex;
            this.distance = distance;
        }

        public Vertex<T, K> getVertex() {
            return vertex;
        }
        @Override
        public int compareTo(VertexDistancePair<T, K> other) {
            return Integer.compare(this.distance, other.distance);
        }
}
