package org.example.gr2_quizgame;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.util.Objects;

public class MainController {
    @FXML
    private Button bt_exit;
    @FXML
    private Button bt_easy;
    @FXML
    private Button bt_normal;
    @FXML
    private Button bt_hard;

    @FXML
    public void onDifficultyButtonPress(ActionEvent actionEvent) {
        Difficulty difficulty = null;
        if (actionEvent.getSource() == bt_easy) {
            difficulty = Difficulty.EASY;
        } else if (actionEvent.getSource() == bt_normal) {
            difficulty = Difficulty.NORMAL;
        } else if (actionEvent.getSource() == bt_hard) {
            difficulty = Difficulty.HARD;
        }

        Stage stage = (Stage) bt_exit.getScene().getWindow();
        StageSwitcher.switchDifficulty(stage, difficulty);
        Scene scene = stage.getScene();
    }

    @FXML
    public void onExitButtonPress() {
        Platform.exit();
    }
}