package com.labirynth.screens;
import com.labirynth.model.Cell;
import com.labirynth.model.CellType;
import com.labirynth.model.MainCharacter;
import com.labirynth.model.Maze;
import com.labirynth.util.VertexList;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;

import java.util.List;
import java.util.Random;


public class ScreenA {

    private Canvas canvas;
    private GraphicsContext graphicsContext;
    private MainCharacter avatar;
    private Maze maze;

    public ScreenA(Canvas canvas) {

        this.canvas = canvas;
        this.graphicsContext = this.canvas.getGraphicsContext2D();
        this.avatar = new MainCharacter(this.canvas);
        this.maze = new Maze(); // Instancia del laberinto
        initializeMaze();
    }

    public void paint() {

        graphicsContext.setFill(Color.DARKGRAY);
        graphicsContext.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());
        paintMaze();
        avatar.paint();

    }

    private void paintMaze() {
        int numRows = 50;
        int numCols = 50;
        int cellSizeX = (int) canvas.getWidth() / numRows;
        int cellSizeY = (int) canvas.getHeight() / numCols;

        for (int i = 0; i < numRows; i++) {
            for (int j = 0; j < numCols; j++) {
                VertexList<Cell, String> vertexList = maze.getCell(i, j);
                Cell currentCell = vertexList.getData();
                double x = i * cellSizeX;
                double y = j * cellSizeY;

                // Pintar la celda según su tipo
                switch (currentCell.getType()) {
                    case WALL:
                        graphicsContext.setFill(Color.BLACK);
                        break;
                    case EXIT:
                        graphicsContext.setFill(Color.RED);
                        break;
                    default:
                        // Solo pinta el fondo del laberinto si no es una celda especial
                        graphicsContext.setFill(Color.WHITE);
                        graphicsContext.fillRect(x, y, cellSizeX, cellSizeY);
                        break;
                }

                // Dibujar la celda en el lienzo
                if (currentCell.getType() != CellType.EMPTY) {
                    graphicsContext.fillRect(x, y, cellSizeX, cellSizeY);
                }
            }
        }
    }

    private void initializeMaze() {
        Random random = new Random();

        // Genera el grafo del laberinto con 50 vértices y 50 aristas
        for (int i = 0; i < 50; i++) {
            for (int j = 0; j < 50; j++) {
                // Generar un tipo de celda aleatorio
                CellType cellType = getRandomCellType(random);

                Cell currentCell = new Cell(i, j, cellType);
                String currentKey = maze.generateKey(i, j);
                maze.addCell(i, j, cellType);

                // Conectar con la celda a la derecha o debajo (aleatoriamente)
                if (i < 49 && (j == 49 || random.nextBoolean())) {
                    Cell nextCellInRow = new Cell(i + 1, j, cellType);
                    String nextKeyInRow = maze.generateKey(i + 1, j);
                    maze.addCell(i + 1, j, cellType);  // Añadir celda a la derecha al laberinto
                    maze.connectCells(currentCell, nextCellInRow);
                } else if (j < 49) {
                    Cell nextCellInColumn = new Cell(i, j + 1, cellType);
                    String nextKeyInColumn = maze.generateKey(i, j + 1);
                    maze.addCell(i, j + 1, cellType);  // Añadir celda debajo al laberinto
                    maze.connectCells(currentCell, nextCellInColumn);
                }
            }
        }

        // Asegurar una casilla de inicio y una casilla de salida
        Cell startCell = getRandomEmptyCell(random);
        Cell exitCell = getRandomEmptyCell(random);

        startCell.setType(CellType.START);
        exitCell.setType(CellType.EXIT);

        // Asegurar que haya una ruta entre la casilla de inicio y la casilla de salida
        List<VertexList<Cell, String>> path = maze.findShortestPath(startCell, exitCell);
        while (path.isEmpty()) {
            // Cambiar la posición de la casilla de salida y volver a intentar
            exitCell.setType(CellType.EMPTY);
            exitCell = getRandomEmptyCell(random);
            exitCell.setType(CellType.EXIT);

            path = maze.findShortestPath(startCell, exitCell);
        }
    }

    private CellType getRandomCellType(Random random) {
        double probability = random.nextDouble();

        if (probability < 0.3) {
            return CellType.WALL;
        } else if (probability < 0.6) {
            return CellType.PASAGE;
        } else {
            return CellType.EMPTY;
        }
    }

    private Cell getRandomEmptyCell(Random random) {
        int rowIndex = random.nextInt(50);
        int colIndex = random.nextInt(50);

        while (maze.getCell(rowIndex, colIndex).getData().getType() != CellType.EMPTY) {
            rowIndex = random.nextInt(50);
            colIndex = random.nextInt(50);
        }

        return maze.getCell(rowIndex, colIndex).getData();
    }

    public void onKeyPressed(KeyEvent event){
        this.avatar.onKeyPressed(event);
    }

    public void onKeyReleased(KeyEvent event){
        this.avatar.onKeyReleased(event);
    }

}
