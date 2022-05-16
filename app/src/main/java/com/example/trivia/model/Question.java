package com.example.trivia.model;

public class Question {
    private String answer;
    private boolean anserTrue;

    public Question(String answer, boolean anserTrue) {
        this.answer = answer;
        this.anserTrue = anserTrue;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public boolean isAnserTrue() {
        return anserTrue;
    }

    public void setAnserTrue(boolean anserTrue) {
        this.anserTrue = anserTrue;
    }

    @Override
    public String toString() {
        return "Question{" +
                "answer='" + answer + '\'' +
                ", anserTrue=" + anserTrue +
                '}';
    }
}
