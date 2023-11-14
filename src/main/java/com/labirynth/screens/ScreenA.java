package com.labirynth.screens;

import com.labirynth.model.MainCharacter;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;

public class ScreenA{

    private Canvas canvas;
    private GraphicsContext graphicsContext;
    public MainCharacter avatar;

    public ScreenA(Canvas canvas){

        this.canvas = canvas;
        this.graphicsContext = this.canvas.getGraphicsContext2D();
        this.avatar = new MainCharacter(this.canvas);

    }

    public void paint() {
        graphicsContext.setFill(Color.DARKGRAY);
        graphicsContext.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());
        avatar.paint();

    }

    public void onKeyPressed(KeyEvent event){
        this.avatar.onKeyPressed(event);
    }

    public void onKeyReleased(KeyEvent event){
        this.avatar.onKeyReleased(event);
    }

}
