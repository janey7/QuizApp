package com.example.csaper6.quizapp;

/**
 * Created by csaper6 on 9/15/16.
 *
 */
public class Question {
    public Question(int questionId, boolean isAnswerTrue) {
        this.questionId = questionId;
        this.isAnswerTrue = isAnswerTrue;
    }

    private int questionId;
    private boolean isAnswerTrue;

    /**
     * If ans & isAnswerTrue match, it returns true
     * @param ans = User's t/f answer
     * @return Whether they got the question correct
     */
    public boolean checkAnswer(boolean ans)//checks if their answer = real answer
    {
        return isAnswerTrue == ans;
    }
    public int getQuestionId() {
        return questionId;
    }

    public void setQuestionId(int questionId) {
        this.questionId = questionId;
    }

    public boolean isAnswerTrue() {
        return isAnswerTrue;
    }

    public void setAnswerTrue(boolean answerTrue) {
        isAnswerTrue = answerTrue;
    }

}