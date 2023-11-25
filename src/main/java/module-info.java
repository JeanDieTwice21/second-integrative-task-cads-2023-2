module com.labirynth{

    requires javafx.controls;
    requires javafx.fxml;


    opens com.labirynth to javafx.fxml;
    exports com.labirynth;
    exports com.labirynth.control;
    opens com.labirynth.control to javafx.fxml;

}