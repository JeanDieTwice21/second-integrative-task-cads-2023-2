package com.labirynth.control;
import com.labirynth.GameApplication;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class MainMenuController{

    @FXML
    private Button playBt;
    @FXML
    private Button exitBt;

    public void onPlay(){

        GameApplication.openWindow("game-view.fxml");
        Stage stage = (Stage) playBt.getParent().getScene().getWindow();
        stage.close();

    }

    public void onExit(){

        Stage stage = (Stage) exitBt.getParent().getScene().getWindow();
        stage.close();

    }
}
