package com.labirynth.model;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.input.KeyEvent;
import java.util.ArrayList;
public class MainCharacter{

    public static final String PATH_RUN= "/animations/walk/char-walk-0";
    public static final String PATH_IDLE= "/animations/idle/char-idle-0";
    private Canvas canvas;
    private GraphicsContext graphicsContext;
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

    public MainCharacter(Canvas canvas){
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

        this.position = new Position(100,100);

        for(int i = 0; i <=1; i++ ) {
            Image image1 = new Image(getClass().getResourceAsStream(PATH_IDLE+i+".png"), 50, 50, false, false);
            this.idles.add(image1);
        }


        for (int i = 0; i <= 3 ; i++) {
            Image image = new Image(getClass().getResourceAsStream(PATH_RUN+i+".png"), 50, 50, false, false);
            this.runLeft.add(image);
        }

        for(int i = 4; i <= 7; i++){

            Image img = new Image(getClass().getResourceAsStream(PATH_RUN+i+".png"),50,50,false,false);
            this.runRight.add(img);
        }

        for(int i = 8; i <= 10; i++){

            Image img = new Image(getClass().getResourceAsStream(PATH_RUN+i+".png"),50,50,false,false);
            this.runDown.add(img);
        }


        for(int i = 11; i <= 13; i++){
            Image img = new Image(getClass().getResourceAsStream(PATH_RUN+i+".png"),50,50,false,false);
            this.runUpper.add(img);
        }


    }

    public void paint(){

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
            position.setX(position.getX() + 10);
        }
    }

    public void onMoveLeft(){

        if(leftPressed){

            position.setX(position.getX() - 10);
        }
    }

    public void onMoveUp(){

        if(upPressed){

            position.setY(position.getY() - 10);
        }
    }

    public void onMoveDown(){

        if(downPressed){

            position.setY(position.getY() + 10);
        }

    }

}
