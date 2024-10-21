package org.example.gr2_quizgame;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class StageSwitcher {
    protected static Difficulty currentDifficulty;
    protected static int points;

    public static void switchDifficulty(Stage stage, Difficulty difficulty) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(StageSwitcher.class.getResource("question-view.fxml"));
            Scene scene = new Scene(fxmlLoader.load());
            stage.setScene(scene);
            stage.show();
            StageSwitcher.currentDifficulty = difficulty;
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }
    }

    public static void switchEndScreen(Stage stage, int points) {
        try {
            StageSwitcher.points = points;
            FXMLLoader fxmlLoader = new FXMLLoader(StageSwitcher.class.getResource("end-view.fxml"));
            Scene scene = new Scene(fxmlLoader.load());
            stage.setScene(scene);
            stage.show();
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }
    }

    public static void switchStartScreen(Stage stage) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(StageSwitcher.class.getResource("main-view.fxml"));
            Scene scene = new Scene(fxmlLoader.load());
            stage.setScene(scene);
            stage.show();
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }
    }
}
