package com.labirynth.screens;
import com.labirynth.model.*;
import com.labirynth.util.VertexList;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;

import java.util.ArrayList;
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
        this.maze = new Maze(); // Instancia del laberinto
        initializeMaze();
        this.avatar = new MainCharacter(this.canvas, this.maze);


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
        int cellSizeX = maze.getCellSizeX();
        int cellSizeY = maze.getCellSizeY();

        for (int i = 0; i < numRows; i++) {
            for (int j = 0; j < numCols; j++) {
                VertexList<Cell, String> vertexList = maze.getCell(i, j);
                Cell currentCell = vertexList.getData();
                double x = i * cellSizeX;
                double y = j * cellSizeY;

                // Validar si la posición está dentro de los límites del canvas
                if (x >= canvas.getWidth() || y >= canvas.getHeight()) {
                    continue;
                }

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
        int numRows = 50;
        int numCols = 50;
        int cellSizeX = maze.getCellSizeX();
        int cellSizeY = maze.getCellSizeY();
    
        boolean exitPlaced = false;  // Variable de control
    
        Random random = new Random();  // Se crea una instancia de Random para generar números aleatorios
    
        // Genera el grafo del laberinto con 50 vértices y 50 aristas
        for (int i = 0; i < numRows; i++) {
            for (int j = 0; j < numCols; j++) {
                // Generar un tipo de celda fijo
                CellType cellType = getRandomCellType(random);
    
                Cell currentCell = new Cell(i, j, cellType);
                String currentKey = maze.generateKey(i, j);
                maze.addCell(i, j, cellType);
    
                // Asignar posición a la celda
                double x = i * cellSizeX;
                double y = j * cellSizeY;
                currentCell.setPosition(new Position(x, y));
    
                // Verificar si es hora de colocar la celda de salida
                if (!exitPlaced && i == numRows - 1 && j == numCols - 1) {
                    currentCell.setType(CellType.EXIT);
                    exitPlaced = true;
                }
    
                // Conectar con la celda a la derecha o debajo
                if (i < numRows - 1) {
                    Cell nextCellInRow = new Cell(i + 1, j, getRandomCellType(random));
                    String nextKeyInRow = maze.generateKey(i + 1, j);
                    maze.addCell(i + 1, j, nextCellInRow.getType());  // Añadir celda a la derecha al laberinto
                    nextCellInRow.setPosition(new Position((i + 1) * cellSizeX, j * cellSizeY));
                    maze.connectCells(currentCell, nextCellInRow);
                }
    
                if (j < numCols - 1) {
                    Cell nextCellInColumn = new Cell(i, j + 1, getRandomCellType(random));
                    String nextKeyInColumn = maze.generateKey(i, j + 1);
                    maze.addCell(i, j + 1, nextCellInColumn.getType());  // Añadir celda debajo al laberinto
                    nextCellInColumn.setPosition(new Position(i * cellSizeX, (j + 1) * cellSizeY));
                    maze.connectCells(currentCell, nextCellInColumn);
                }
            }
        }
    }
    
    // Método para obtener un tipo de celda aleatorio basado en probabilidades
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


    public List<Position> getWallPositions() {
        List<Position> wallPositions = new ArrayList<>();

        for (int i = 0; i < maze.getNumCols(); i++) {
            for (int j = 0; j < maze.getNumRows(); j++) {
                VertexList<Cell, String> vertexList = maze.getCell(i, j);

                if (vertexList != null) {
                    Cell cell = vertexList.getData();
                    if (cell != null && cell.getType() == CellType.WALL) {
                        wallPositions.add(cell.getPosition());
                    }
                }
            }
        }

        return wallPositions;
    }

    public void onKeyPressed(KeyEvent event){
        this.avatar.onKeyPressed(event);
    }

    public void onKeyReleased(KeyEvent event){
        this.avatar.onKeyReleased(event);
    }

}
