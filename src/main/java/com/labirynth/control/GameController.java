/**
 * This class represents a game controller that implements the Initializable interface.
 * It initializes a canvas and a ScreenA object, sets up event handlers for key presses and releases,
 * starts an update thread, and updates the screen at regular intervals.
 */
package com.labirynth.control;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyEvent;

import java.net.URL;
import java.util.ResourceBundle;
import com.labirynth.screens.ScreenA;

public class GameController implements Initializable {

    @FXML
    private Canvas canvas;
    private GraphicsContext graphicsContext;
    private ScreenA screenA;

    /**
     * Initializes the GameController by setting up the graphics context, creating an instance of the ScreenA class,
     * setting event handlers for key presses and releases on the canvas, and starting a separate thread to continuously
     * update the screen.
     *
     * @param url the URL of the resource
     * @param resourceBundle the resource bundle containing localized resources
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        graphicsContext = canvas.getGraphicsContext2D();
        screenA = new ScreenA(canvas);

        canvas.setOnKeyPressed(this::handleKeyPressed);
        canvas.setOnKeyReleased(this::handleKeyReleased);

        startUpdateThread();
    }

    /**
     * Handles the key pressed events.
     * Calls the onKeyPressed method of the ScreenA object, passing the KeyEvent as a parameter.
     *
     /**
      * Private method to handle a key pressed event.
      * 
      * @param event the key pressed event
      */
     private void handleKeyPressed(KeyEvent event) {
        screenA.onKeyPressed(event);
    }

    private void handleKeyReleased(KeyEvent event) {
        screenA.onKeyReleased(event);
    }

    /**
     * Starts a new thread that continuously updates the screen by calling the {@link #updateScreen()} method every 80 milliseconds.
     * <p>
     * Example Usage:
     * <pre>{@code
     * GameController gameController = new GameController();
     * gameController.startUpdateThread();
     * }</pre>
     * 
     * @throws InterruptedException if the thread is interrupted while sleeping
     */
    private void startUpdateThread() {
        Thread updateThread = new Thread(() -> {
            while (true) {
                Platform.runLater(this::updateScreen);
                sleep(80);
            }
        });
        updateThread.setDaemon(true);
        updateThread.start();
    }

    /**
     * Updates the screen by calling the paint method of the ScreenA object.
     * 
     * Example Usage:
     * GameController gameController = new GameController();
     * gameController.updateScreen();
     * 
     * Inputs: None
     * 
     * Flow:
     * 1. The updateScreen method is called.
     * 2. The paint method of the ScreenA object is called.
     * 
     * Outputs: None
     */
    private void updateScreen() {
        screenA.paint();
    }

    /**
     * Pauses the execution of the current thread for a specified number of milliseconds.
     *
     * @param milliseconds the number of milliseconds to pause the execution of the thread
     */
    private void sleep(long milliseconds) {
        try {
            Thread.sleep(milliseconds);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
