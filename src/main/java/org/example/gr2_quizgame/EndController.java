package org.example.gr2_quizgame;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class EndController implements Initializable {
    @FXML
    private Label l_points;
    @FXML
    private Label l_result;

    @FXML
    public void onExitButtonPress() {
        StageSwitcher.switchStartScreen((Stage) l_points.getScene().getWindow());
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        l_points.setText("Points: " + StageSwitcher.points);
    }
}
