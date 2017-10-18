package com.shopify.bootcamp.pratik.quizzical;

import android.content.SharedPreferences;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.ButtonBarLayout;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private static final String KEY_LAST_ANSWER = "lastAnswer";
    private static final String KEY_QUESTION_ANSWERED = "questionAnswered";
    private static final String KEY_QUESTION_INDEX = "questionIndex";


    private Button trueButton, falseButton, nextButton;

    private RadioButton trueRadioButton, falseRadioButton;
    private TextView questionText, resultText;
    private  Quiz quiz;

    private boolean lastAnswer;
    private boolean questionAnswered = false;
    private int questionIndex = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        questionText = (TextView) findViewById(R.id.question);
        nextButton = (Button) findViewById(R.id.next_button);
        resultText = (TextView) findViewById(R.id.result);
        quiz = Quiz.getInstance();
        /*questionText.setText("Two plus two is equal to four?");*/


        trueButton = (Button) findViewById(R.id.true_button);
        trueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkAnswer(true);
                Log.e("bootcamp", "true button clicked");
            }
        });

        falseButton = (Button) findViewById(R.id.false_button);
        falseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkAnswer(false);
                Log.e("bootcamp", "false button clicked");
            }
        });

        trueRadioButton = (RadioButton) findViewById(R.id.radio_true);
        trueRadioButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkAnswer(true);
                Log.d("HI", "True button clicked");
            }
        });

        falseRadioButton = (RadioButton) findViewById(R.id.radio_false);
        falseRadioButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("HI", "False button clicked");
                checkAnswer(false);
            }
        });

        trueButton = (Button) findViewById(R.id.true_button);
        trueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkAnswer(true);
                Log.e("bootcamp", "true button clicked");
            }
        });

        falseButton = (Button) findViewById(R.id.false_button);
        falseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkAnswer(false);
                Log.e("bootcamp", "false button clicked");
            }
        });

        nextButton = (Button) findViewById(R.id.next_button);
        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                nextQuestion();
            }
        });

        //questionText.setText(quiz.getQuestions().get(0).getStatement());

        if(savedInstanceState!=null){
            questionAnswered = savedInstanceState.getBoolean(KEY_QUESTION_ANSWERED, false);
            questionIndex = savedInstanceState.getInt(KEY_QUESTION_INDEX, -1);
            if (questionAnswered) {
                lastAnswer = savedInstanceState.getBoolean(KEY_LAST_ANSWER);
                checkAnswer(lastAnswer);
            }
        }else{
            questionIndex = 0;
        }

        showQuestion();

    }

    private void checkAnswer(boolean answer) {
        questionAnswered = true;
        lastAnswer = answer;
        if (answer == getCurrentQuestion().getAnswer()) {
            resultText.setText("Correct!");
        } else {
            resultText.setText("Incorrect!");
        }
        nextButton.setEnabled(true);
        //nextQuestion();
    }

    private void showQuestion(){

        questionText.setText(getCurrentQuestion().getStatement());
        resultText.setText("");
        nextButton.setEnabled(false);
    }


    private Question getCurrentQuestion(){

        return quiz.getQuestions().get(questionIndex);
    }

    private void nextQuestion(){
        questionAnswered = false;
        questionIndex = (questionIndex + 1)% quiz.getQuestions().size();
        showQuestion();
    }

    @Override
    protected void onPause() {
        super.onPause();
        SharedPreferences preferences = getSharedPreferences("sharedPrefs", 0);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putBoolean(KEY_LAST_ANSWER,lastAnswer );
        editor.putBoolean(KEY_QUESTION_ANSWERED,questionAnswered);
        editor.putInt(KEY_QUESTION_INDEX, questionIndex);

        // Commit to storage
        editor.commit();

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // Store values between instances here
        SharedPreferences preferences = getSharedPreferences("sharedPrefs", 0);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putBoolean("KEY_LAST_ANSWER",lastAnswer );
        editor.putBoolean("KEY_QUESTION_ANSWERED",questionAnswered);
        editor.putInt(KEY_QUESTION_INDEX, questionIndex);

        // Commit to storage
        editor.commit();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putBoolean(KEY_LAST_ANSWER, lastAnswer);
        outState.putBoolean(KEY_QUESTION_ANSWERED, questionAnswered);
        outState.putInt(KEY_QUESTION_INDEX, questionIndex);

    }
}
