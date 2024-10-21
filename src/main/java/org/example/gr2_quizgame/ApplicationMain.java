package org.example.gr2_quizgame;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Screen;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

public class ApplicationMain extends Application {
    private MediaPlayer mediaPlayer;

    @Override
    public void start(Stage stage) throws IOException
    {
        FXMLLoader fxmlLoader = new FXMLLoader(ApplicationMain.class.getResource("main-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), Screen.getPrimary().getBounds().getWidth(), Screen.getPrimary().getBounds().getHeight());
        stage.setTitle("Helloo!");
        stage.setScene(scene);
        stage.setFullScreen(true);
        stage.setMaximized(true);
        stage.show();

        String musicFile = Objects.requireNonNull(getClass().getResource("intro.mp3")).toExternalForm();
        Media media = new Media(musicFile);
        mediaPlayer = new MediaPlayer(media);
        mediaPlayer.setCycleCount(MediaPlayer.INDEFINITE);
        mediaPlayer.play();
    }

    public static void main(String[] args) {
        launch();
    }
}
