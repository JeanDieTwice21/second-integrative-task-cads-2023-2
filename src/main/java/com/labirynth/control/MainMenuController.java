package com.labirynth.control;

import com.labirynth.GameApplication;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class MainMenuController {

    @FXML
    private Button playBt;
    @FXML
    private Button exitBt;

    /**
     * Opens a new game window and closes the current window when the play button is clicked.
     * This method calls the openGameWindow() method to open a new game window and then calls
     * the closeCurrentWindow() method to close the current window.
     * 
     * Example Usage:
     * MainMenuController controller = new MainMenuController();
     * controller.onPlay();
     * 
     * Inputs: None
     * Flow:
     * 1. The onPlay() method is called.
     * 2. It calls the openGameWindow() method to open a new game window.
     * 3. It calls the closeCurrentWindow() method to close the current window.
     * Outputs: None
     */
    public void onPlay() {
        openGameWindow();
        closeCurrentWindow(playBt);
    }

    /**
     * Closes the current window when the exit button is clicked.
     * 
     * @param button The button that triggers the window closing action.
     */
    public void onExit() {
        closeCurrentWindow(exitBt);
    }

    /**
     * Opens a new game window.
     * 
     * This method is responsible for opening a new game window by calling the openWindow() method from the GameApplication class.
     * 
     * Example Usage:
     * MainMenuController controller = new MainMenuController();
     * controller.openGameWindow();
     * 
     * Inputs:
     * None
     * 
     * Flow:
     * 1. The openGameWindow() method is called.
     * 2. It calls the openWindow() method from the GameApplication class, passing the argument "game-view.fxml".
     * 3. The openWindow() method in the GameApplication class opens a new window with the specified FXML file.
     * 
     * Outputs:
     * None
     */
    private void openGameWindow() {
        GameApplication.openWindow("game-view.fxml");
    }

    /**
     * Closes the current window in a JavaFX application.
     *
     * @param button The button that triggers the window closing action.
     */
    private void closeCurrentWindow(Button button) {
        Stage stage = (Stage) button.getParent().getScene().getWindow();
        stage.close();
    }
}
