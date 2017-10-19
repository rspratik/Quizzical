package com.shopify.bootcamp.pratik.quizzical;

import java.io.Serializable;

/**
 * Created by androidbootcamp3 on 2017-10-18.
 */

public class Question implements Serializable {

    private  String statement;
    private  boolean answer;


    public boolean getAnswer() {
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

    @Override
    public String toString() {
        return "Question{" +
                "statement='" + statement + '\'' +
                ", answer=" + answer +
                '}';
    }
}
