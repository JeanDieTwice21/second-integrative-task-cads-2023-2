package com.labirynth.control;
import com.labirynth.screens.ScreenA;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import java.net.URL;
import java.util.ResourceBundle;

public class GameController implements Initializable{

    @FXML
    private Canvas canvas;
    private GraphicsContext graphicsContext;
    private ScreenA screenA;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle){

        this.graphicsContext = canvas.getGraphicsContext2D();
        this.screenA = new ScreenA(this.canvas);

        this.canvas.setOnKeyPressed(event -> {
            screenA.onKeyPressed(event);
        });

        canvas.setOnKeyReleased(event -> {
            screenA.onKeyReleased(event);
        });

        // Hilo de Java
        new Thread(
                () -> {
                    while (true){

                        Platform.runLater( () -> {
                            screenA.paint();
                        });

                        try {
                            Thread.sleep(80);
                        }catch (InterruptedException e){
                            e.printStackTrace();
                        }
                    }
                }
        ).start();
    }
}
