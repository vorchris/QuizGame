package org.example.gr2_quizgame;

import java.util.ArrayList;

public class Question
{
        private String questionText;
        private ArrayList<String> options;
        private int correctIndex;

        public Question(String questionText, ArrayList<String> options, int correctIndex)
        {
            this.questionText = questionText;
            this.options = options;
            this.correctIndex = correctIndex;
        }

        public String getQuestionText() {
            return questionText;
        }

        public ArrayList<String> getOptions() {
            return options;
        }

        public int getCorrectIndex() {
            return correctIndex;
        }
}
