package com.amdp.android.survey.activities;

import java.util.Comparator;

/**
 * Created by arley on 1/13/17.
 */

public class PriorityQuestionComparator implements Comparator<Question> {
    @Override
    public int compare(Question o1, Question o2) {
        return o1.getPriority() - (o2.getPriority());
    }
}
