package com.shopify.bootcamp.pratik.quizzical;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by androidbootcamp3 on 2017-10-18.
 */

public class Quiz {

    private List<Question> questions = new ArrayList<>();
    private static Quiz quiz;

    public List<Question> getQuestions() {
        return questions;
    }

    public void setQuestions(List<Question> questions) {
        this.questions = questions;
    }

    public void addQuestion(Question question) {
        questions.add(question);
    }

    public static Quiz getInstance(){
        if (quiz == null) {
            quiz = new Quiz();
            quiz.addQuestion(new Question("The moon is made of cheese", false));
            quiz.addQuestion(new Question("The sum of the internal angles of a triangle is 180", true));
        }
        return quiz;
    }
}
