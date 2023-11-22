package com.labirynth.model;

import com.labirynth.util.GraphList;
import com.labirynth.util.VertexList;

import java.util.ArrayList;
import java.util.List;

public class Maze extends GraphList<String, Cell> {

    public Maze() {
        super();
    }

    public void addCell(int row, int col, CellType type) {
        Cell cell = new Cell(row, col, type);
        String vertexKey = generateKey(row, col);
        addVertex(vertexKey, cell);
    }

    public void connectCells(Cell cell1, Cell cell2) {
        if (canConnect(cell1, cell2)) {
            String key1 = generateKey(cell1.getRow(), cell1.getCol());
            String key2 = generateKey(cell2.getRow(), cell2.getCol());
            addEdge(key1, key2, 1);
        }
    }

    public VertexList<Cell, String> getCell(int row, int col) {
        String key = generateKey(row, col);
        return getVertex(key);
    }

    public List<VertexList<Cell, String>> getNeighbors(VertexList<Cell, String> vertex) {
        List<VertexList<Cell, String>> neighbors = new ArrayList<>();

        // Obtén las celdas adyacentes a la celda representada por el vértice
        int row = vertex.getData().getRow();
        int col = vertex.getData().getCol();

        if (row > 0) {
            neighbors.add(getCell(row - 1, col));
        }
        if (row < 49) {
            neighbors.add(getCell(row + 1, col));
        }
        if (col > 0) {
            neighbors.add(getCell(row, col - 1));
        }
        if (col < 49) {
            neighbors.add(getCell(row, col + 1));
        }

        return neighbors;
    }

    public List<VertexList<Cell, String>> findShortestPath(Cell startCell, Cell endCell) {
        String startKey = generateKey(startCell.getRow(), startCell.getCol());
        String endKey = generateKey(endCell.getRow(), endCell.getCol());

        return dijkstra(startKey, endKey);
    }

    private boolean canConnect(Cell cell1, Cell cell2) {

        return cell1.getType() == CellType.EMPTY && cell2.getType() == CellType.EMPTY;

    }

    public String generateKey(int a, int b){

        return (a+"-"+b);
    }

    public VertexList<Cell, String> getVertex(String key) {

        return getVertices().get(key);

    }

}
