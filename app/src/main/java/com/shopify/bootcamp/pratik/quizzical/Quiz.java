package com.shopify.bootcamp.pratik.quizzical;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by androidbootcamp3 on 2017-10-18.
 */

/*
public class Quiz implements Serializable {

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
            quiz.addQuestion(new Question("The moon is made of matter", true));
            quiz.addQuestion(new Question("Windows is better than mac", false));
            quiz.addQuestion(new Question("The sum of the internal angles of a triangle is 180", true));
        }
        return quiz;
    }

    @Override
    public String toString() {
        return "Quiz{" +
                "questions=" + questions +
                '}';
    }
}
*/


public class Quiz implements  Serializable {

    private String title;
    private int id;
    private List<Question> questions = new ArrayList<>();

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public List<Question> getQuestions() {
        return questions;
    }

    public void addQuestion(Question question) {
        questions.add(question);
    }

    @Override
    public String toString() {
        return "Quiz{" +
                "title='" + title + '\'' +
                ", id=" + id +
                ", questions=" + questions +
                '}';
    }

}
