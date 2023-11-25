package com.labirynth.model;
import com.labirynth.util.VertexList;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.input.KeyEvent;
import java.util.ArrayList;
import java.util.Map;
import java.util.Random;

public class MainCharacter{

    public static final String PATH_RUN= "/animations/walk/char-walk-0";
    public static final String PATH_IDLE= "/animations/idle/char-idle-0";
    private Canvas canvas;
    private GraphicsContext graphicsContext;

    private Maze maze;
    private ArrayList<Image> idles;
    private ArrayList<Image> runRight;
    private ArrayList<Image> runLeft;
    private ArrayList<Image> runUpper;
    private ArrayList<Image> runDown;
    private int frame;

    private Position position;
    private State state;
    private boolean rightPressed;
    private boolean leftPressed;

    private boolean upPressed, downPressed;

    public MainCharacter(Canvas canvas, Maze maze){
        this.canvas = canvas;
        this.graphicsContext = this.canvas.getGraphicsContext2D();
        // 0 is idle | 1 is run
        this.state = State.IDLE;
        this.frame = 0;

        this.idles = new ArrayList<>();
        this.runRight = new ArrayList<>();
        this.runLeft = new ArrayList<>();
        this.runUpper = new ArrayList<>();
        this.runDown = new ArrayList<>();


        for(int i = 0; i <=1; i++ ) {
            Image image1 = new Image(getClass().getResourceAsStream(PATH_IDLE + i + ".png"), 20, 20, false, false);
            this.idles.add(image1);
        }

        for (int i = 0; i <= 3 ; i++) {
            Image image = new Image(getClass().getResourceAsStream(PATH_RUN+i+".png"), 20, 20, false, false);
            this.runLeft.add(image);
        }

        for(int i = 4; i <= 7; i++){

            Image img = new Image(getClass().getResourceAsStream(PATH_RUN+i+".png"),20,20,false,false);
            this.runRight.add(img);
        }

        for(int i = 8; i <= 10; i++){

            Image img = new Image(getClass().getResourceAsStream(PATH_RUN+i+".png"),20,20,false,false);
            this.runDown.add(img);
        }


        for(int i = 11; i <= 13; i++){
            Image img = new Image(getClass().getResourceAsStream(PATH_RUN+i+".png"),20,20,false,false);
            this.runUpper.add(img);
        }

        this.maze = maze;


        this.position = new Position(100, 100);

    }

    public void paint(){

        stop();
        onMoveRight();
        onMoveLeft();
        onMoveUp();
        onMoveDown();

        switch(state){
            case IDLE -> {graphicsContext.drawImage(idles.get(frame%2), position.getX(), position.getY());
                frame++;
            }
            case RUN_RIGHT -> {
                graphicsContext.drawImage(runRight.get(frame%4), position.getX(), position.getY());
                frame++;
            }
            case RUN_LEFT -> {
                graphicsContext.drawImage(runLeft.get(frame%4), position.getX(), position.getY());
                frame++;
            }
            case RUN_UP -> {
                graphicsContext.drawImage(runUpper.get(frame%3), position.getX(), position.getY());
                frame++;
            }
            case RUN_DOWN -> {
                graphicsContext.drawImage(runDown.get(frame%3), position.getX(), position.getY());
                frame++;
            }
        }
    }

    public void onKeyPressed(KeyEvent event){
        switch (event.getCode()){
            case RIGHT : {
                state = State.RUN_RIGHT;
                rightPressed = true;
                break;
            }
            case LEFT: {

                state = State.RUN_LEFT;
                leftPressed = true;
                break;
            }
            case UP:{

                state = State.RUN_UP;
                upPressed = true;
                break;
            }
            case DOWN:{

                state = State.RUN_DOWN;
                downPressed = true;
                break;
            }
        }
    }

    public void onKeyReleased(KeyEvent event){

        switch (event.getCode()){
            case RIGHT : {
                state = State.IDLE;
                rightPressed = false;
                break;
            }
            case LEFT: {

                state = State.IDLE;
                leftPressed = false;
                break;
            }
            case UP:{

                state = State.IDLE;
                upPressed = false;
                break;
            }
            case DOWN:{

                state = State.IDLE;
                downPressed = false;
                break;
            }
        }
    }

    public void onMoveRight(){
        if (rightPressed){
            position.setX(position.getX() + 5);
            System.out.println("X Pos: " + position.getX());
            System.out.println("Y Pos: " + position.getY());
        }
    }

    public void onMoveLeft(){

        if(leftPressed){

            position.setX(position.getX() - 5);

            System.out.println("X Pos: " + position.getX());
            System.out.println("Y Pos: " + position.getY());
        }
    }

    public void onMoveUp(){

        if(upPressed){

            position.setY(position.getY() - 5);

            System.out.println("X Pos: " + position.getX());
            System.out.println("Y Pos: " + position.getY());
        }
    }

    public void onMoveDown(){

        if(downPressed){

            position.setY(position.getY() + 5);

            System.out.println("X Pos: " + position.getX());
            System.out.println("Y Pos: " + position.getY());
        }

    }

    public void stop() {

        double nextX = position.getX();
        double nextY = position.getY();


        if (nextX < 10) {
            position.setX(10);
            rightPressed = false;
        } else if (nextX > 585) {
            position.setX(585);
            leftPressed = false;
        }

        if (nextY < 10) {
            position.setY(10);
            downPressed = false;
        } else if (nextY > 385) {
            position.setY(384);
            upPressed = false;
        }

    }

    public ArrayList<Position> getAllWallsPosition() {
        ArrayList<Position> wallPositions = new ArrayList<>();

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

    public ArrayList<Double> setInitialPositionWithoutWalls() {
        ArrayList<Position> wallPositions = getAllWallsPosition();
        Random random = new Random();
        ArrayList<Double> initialPosition = new ArrayList<>();

        try {
            do {
                // Genera una posición aleatoria
                double randomX = random.nextDouble() * canvas.getWidth();
                double randomY = random.nextDouble() * canvas.getHeight();

                // Verifica si la posición aleatoria no está en una pared
                boolean isPositionValid = !willCollideWithWall(randomX, randomY, wallPositions);

                // Si es válida, establece la posición inicial del personaje
                if (isPositionValid) {
                    initialPosition.add(randomX);
                    initialPosition.add(randomY);
                }
            } while (willCollideWithWall(0, 0, wallPositions)); // Verifica si la posición inicial no está en una pared
        } catch (Exception e) {
            e.printStackTrace();
        }

        return initialPosition;
    }

    private boolean isPositionInsideWall(double x, double y, Position wallPosition) {
        // Verifica si la posición (x, y) está dentro de la pared en wallPosition
        double wallSizeX = maze.getCellSizeX();
        double wallSizeY = maze.getCellSizeY();

        return x >= wallPosition.getX() && x <= wallPosition.getX() + wallSizeX && y >= wallPosition.getY() && y <= wallPosition.getY() + wallSizeY;
    }

    private boolean willCollideWithWall(double randomX, double randomY, ArrayList<Position> wallPositions) {
        for (Position position : wallPositions) {
            if (isPositionInsideWall(randomX, randomY, position)) {
                return true; // Colisión con una pared
            }
        }
        return false; // No hay colisión con ninguna pared
    }
}
