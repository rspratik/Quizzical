package com.shopify.bootcamp.pratik.quizzical;

/**
 * Created by androidbootcamp3 on 2017-10-18.
 */

public class Question {

    private  String statement;
    private  boolean answer;


    public boolean isAnswer() {
        return answer;
    }

    public void setAnswer(boolean answer) {
        this.answer = answer;
    }


    public String getStatement() {
        return statement;
    }

    public void setStatement(String statement) {
        this.statement = statement;
    }

    public Question(String statement, boolean answer) {
        this.statement = statement;
        this.answer = answer;
    }

}
