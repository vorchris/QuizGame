package org.example.gr2_quizgame;

import org.json.JSONArray;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.assertEquals;

class QuestionControllerTest {

    private QuestionController questionController;

    @BeforeEach
    void setUp() {
        questionController = new QuestionController();
    }

    @Test
    void loadQuestions() throws IOException {
        Difficulty difficulty = Difficulty.EASY;

        String jsonString = """
        [
            {
                "question": "What is 2 + 2?",
                "options": ["1", "2", "3", "4"],
                "answer": 4
            },
            {
                "question": "What is the capital of France?",
                "options": ["Berlin", "London", "Madrid", "Paris"],
                "answer": 4
            }
        ]
        """;

        Path path = Path.of("src/main/java/Questions/easytest.json");
        Files.createDirectories(path.getParent());
        Files.writeString(path, jsonString);

        JSONArray result = questionController.loadQuestions(difficulty);

        Files.deleteIfExists(path);

        assertEquals(2, result.length());
        assertEquals("What is 2 + 2?", result.getJSONObject(0).getString("question"));
        assertEquals("4", result.getJSONObject(0).getJSONArray("options").getString(3));
        assertEquals(4, result.getJSONObject(0).getInt("answer"));
        assertEquals("What is the capital of France?", result.getJSONObject(1).getString("question"));
        assertEquals("Paris", result.getJSONObject(1).getJSONArray("options").getString(3));
        assertEquals(4, result.getJSONObject(1).getInt("answer"));
    }
}
