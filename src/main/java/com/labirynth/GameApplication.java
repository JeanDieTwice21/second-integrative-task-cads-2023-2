package com.labirynth;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;

public class GameApplication extends Application{


        @Override
        public void start(Stage stage) throws IOException {

            openWindow("main-menu.fxml");

        }

        public static void openWindow(String fxml){
            try{
                FXMLLoader fxmlLoader = new FXMLLoader(GameApplication.class.getResource(fxml));
                Scene scene = new Scene(fxmlLoader.load(), 600, 400);
                Stage stage = new Stage();
                stage.setTitle("Nombre pendiente :D");
                stage.setScene(scene);
                stage.show();
            }catch (Exception e){
                System.out.println("Error: " + e.getMessage());
            }

        }

        public static void main(String[] args) {
            launch();
        }

}
