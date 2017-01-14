package com.amdp.android.survey.activities;

import java.util.Comparator;

/**
 * Created by arley on 1/13/17.
 */

public class Question implements Comparator<Question>{
    private int priority;
    private String question;

    public Question(String question, int priority){
        this.question = question;
        this.priority = priority;
    }

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    @Override
    public int compare(Question lhs, Question rhs) {
        int compareQuantity = ((Question) lhs).getPriority();
        int compareQuantity2 = ((Question) rhs).getPriority();

        //ascending order
        return compareQuantity2 - compareQuantity;
    }



}

