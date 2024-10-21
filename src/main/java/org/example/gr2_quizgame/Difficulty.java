package org.example.gr2_quizgame;

public enum Difficulty {
    EASY("easy"),
    NORMAL("normal"),
    HARD("hard");

    private final String difficulty;

    Difficulty (String difficulty) {
        this.difficulty = difficulty;
    }

    public String getDifficulty() {
        return difficulty;
    }
}
