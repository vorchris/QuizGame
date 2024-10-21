package org.example.gr2_quizgame;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.Duration;
import org.json.JSONArray;

import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Objects;
import java.util.Random;
import java.util.ResourceBundle;
import java.util.concurrent.atomic.AtomicInteger;

import static java.util.Collections.shuffle;

public class QuestionController implements Initializable {
    @FXML
    private GridPane rb_grid;
    @FXML
    private Label l_Qu;
    @FXML
    private Label l_timer;

    private Difficulty difficulty = null;
    private ToggleGroup buttonGroup = new ToggleGroup();
    private JSONArray jsonContents = null;
    private ArrayList<String> questions = new ArrayList<>();
    private ArrayList<JSONArray> options = new ArrayList<>();
    private ArrayList<Integer> correctAnswers = new ArrayList<>();
    private Random random = new Random();
    private int questionIndex = 0;
    private int points = 0;
    private Timeline timeline;
    private int timeLeft = 30;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        rb_grid.getChildren().forEach(node -> {
            if (node instanceof RadioButton rb) {
                rb.setToggleGroup(buttonGroup);
            }
        });
        Platform.runLater(() -> {
            rb_grid.getChildren().forEach(node -> {
                if (node instanceof RadioButton rb) {
                    rb.removeEventHandler(KeyEvent.ANY, keyEvent -> {});
                    rb.addEventHandler(KeyEvent.KEY_PRESSED, keyEvent -> {
                        if (Objects.requireNonNull(keyEvent.getCode()) == KeyCode.ENTER) {
                            onAnswerButtonPress();
                        }
                    });
                }
            });
            difficulty = StageSwitcher.currentDifficulty;
            jsonContents = loadQuestions(difficulty);
            rb_grid.getScene().setOnKeyPressed(keyEvent -> {
                switch (keyEvent.getCode()) {
                    case DIGIT1:
                        ((RadioButton) rb_grid.getChildren().get(3)).setSelected(true);
                        break;
                    case DIGIT2:
                        ((RadioButton) rb_grid.getChildren().get(1)).setSelected(true);
                        break;
                    case DIGIT3:
                        ((RadioButton) rb_grid.getChildren().get(2)).setSelected(true);
                        break;
                    case DIGIT4:
                        ((RadioButton) rb_grid.getChildren().get(0)).setSelected(true);
                        break;
                    case ENTER:
                        onAnswerButtonPress();
                        break;
                }
            });
            for (int i = 0; i < jsonContents.length(); i++) {
                questions.add(jsonContents.getJSONObject(i).getString("question"));
                options.add(jsonContents.getJSONObject(i).getJSONArray("options"));
                correctAnswers.add(jsonContents.getJSONObject(i).getInt("answer"));
            }
            setQuestion();
            startTimer();
        });
    }

    public void onAnswerButtonPress() {
        if (questions.isEmpty()) {
            StageSwitcher.switchEndScreen((Stage) rb_grid.getScene().getWindow(), points);
        } else {
            int selectedAnswer = 0;
            for (int i = 0; i < rb_grid.getChildren().size(); i++) {
                if (((RadioButton) rb_grid.getChildren().get(i)).isSelected()) {
                    selectedAnswer = i;
                    break;
                }
            }
            selectedAnswer++;
            if (selectedAnswer == correctAnswers.get(questionIndex)) {
                points++;
            } else {
                StageSwitcher.switchEndScreen((Stage) rb_grid.getScene().getWindow(), points);
            }
            options.remove(questionIndex);
            questions.remove(questionIndex);
            correctAnswers.remove(questionIndex);
            setQuestion();
            resetTimer();
        }
    }

    public JSONArray loadQuestions(Difficulty difficulty) {
        String jsonString = "";
        try {
            jsonString = new String(Files.readAllBytes(Path.of("src/main/java/Questions/" + difficulty.getDifficulty() + ".json")));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new JSONArray(jsonString);
    }

    public void setQuestion() {
        ArrayList<String> currentOptions = new ArrayList<>();
        questionIndex = random.nextInt(0,questions.size());
        l_Qu.setText(questions.get(questionIndex));
        l_Qu.setTextFill(Color.WHITE);
        String currentCorrectAnswer = options.get(questionIndex).get(correctAnswers.get(questionIndex )-1).toString();
        for (int i = 0; i < rb_grid.getChildren().size(); i++) {
            currentOptions.add(options.get(questionIndex).get(i).toString());
        }
        shuffle(currentOptions);
        for (int i = 0; i < rb_grid.getChildren().size(); i++) {
            if (currentOptions.get(i).equals(currentCorrectAnswer)) {
                correctAnswers.set(questionIndex, i+1 );
            }
        }
        AtomicInteger index = new AtomicInteger(0);
        rb_grid.getChildren().forEach(node -> {
            if (node instanceof RadioButton) {
                ((RadioButton) node).setText(currentOptions.get(index.get()));
                index.getAndIncrement();
            }
        });

    }

    private void startTimer() {
        timeline = new Timeline(new KeyFrame(Duration.seconds(1), event -> {
            timeLeft--;
            l_timer.setText(String.valueOf(timeLeft));
            if (timeLeft <= 0) {
                timeline.stop();
                StageSwitcher.switchEndScreen((Stage) rb_grid.getScene().getWindow(), points);
            }
        }));
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();
    }

    private void resetTimer() {
        if (timeline != null) {
            timeline.stop();
        }
        timeLeft = 30;
        l_timer.setText(String.valueOf(timeLeft));
        startTimer();
    }
}
