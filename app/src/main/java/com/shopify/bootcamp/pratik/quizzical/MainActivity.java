package com.shopify.bootcamp.pratik.quizzical;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.ButtonBarLayout;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;

import static android.R.attr.id;
import static android.R.attr.type;
import static android.R.id.list;

public class MainActivity extends AppCompatActivity implements QuizRepository.Callback {

    private static final String KEY_LAST_ANSWER = "lastAnswer";
    private static final String KEY_QUESTION_ANSWERED = "questionAnswered";
    private static final String KEY_QUESTION_INDEX = "questionIndex";
    private static final String KEY_SCORE = "score";
    private static final String KEY_QUIZ = "quiz";
    private static final String KEY_QUIZ_ID = "quiz_id";



    //private Button trueButton, falseButton;
    private Button nextButton;
    private RadioButton trueRadioButton, falseRadioButton;
    private RadioGroup radiogroup;
    private TextView questionText, resultText;
    private  Quiz quiz;
    private boolean lastAnswer;
    private boolean questionAnswered = false;
    private int questionIndex = 0;
    private  int score = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        questionText = (TextView) findViewById(R.id.question);
        nextButton = (Button) findViewById(R.id.next_button);
        resultText = (TextView) findViewById(R.id.result);
        //quiz = Quiz.getInstance();
        //quiz = new QuizRepository(this).getQuiz();
        //quiz = new QuizRepository(this).getRemoteQuiz();
        //new QuizRepository(this).getRemoteQuiz(this);

        int id = getIntent().getIntExtra("quiz_id", -1);
        new QuizRepository(this).getRemoteQuiz(id, this);

        radiogroup = (RadioGroup) findViewById(R.id.radiogroup);

        /*trueButton = (Button) findViewById(R.id.true_button);
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
        });*/

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

        nextButton = (Button) findViewById(R.id.next_button);
        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                nextQuestion();
            }
        });

        if(savedInstanceState!=null){
            questionAnswered = savedInstanceState.getBoolean(KEY_QUESTION_ANSWERED, false);
            questionIndex = savedInstanceState.getInt(KEY_QUESTION_INDEX, -1);
            lastAnswer = savedInstanceState.getBoolean(KEY_LAST_ANSWER);
            score = savedInstanceState.getInt(KEY_SCORE, -1);

        }else{
            questionIndex = 0;
            /*SharedPreferences preferences = getSharedPreferences("sharedPrefs", 0);

            questionAnswered = preferences.getBoolean(KEY_QUESTION_ANSWERED, false);
            questionIndex = preferences.getInt(KEY_QUESTION_INDEX, 0);
            lastAnswer = preferences.getBoolean(KEY_LAST_ANSWER, false);
            score = preferences.getInt(KEY_SCORE, 0);
            String quizJson = preferences.getString(KEY_QUIZ, "");

            //quiz instance
            Gson gson = new Gson();
            Type type = new TypeToken<Quiz>() {}.getType();
            System.out.println(quizJson);
            quiz = gson.fromJson(quizJson, type);*/
        }

        /*showQuestion();

        if(questionAnswered){
            checkAnswer(lastAnswer);
        }*/

    }

    private void checkAnswer(boolean answer) {
        questionAnswered = true;
        lastAnswer = answer;
        if (answer == getCurrentQuestion().getAnswer()) {
            score++;
            resultText.setText("Correct!");
        } else {
            resultText.setText("Incorrect!");
        }
        nextButton.setEnabled(true);
    }

    private void showQuestion(){

        questionText.setText(getCurrentQuestion().getStatement());
        resultText.setText("");
        radiogroup.clearCheck();
        nextButton.setEnabled(false);
    }


    private Question getCurrentQuestion(){

        return quiz.getQuestions().get(questionIndex);
    }

    private void nextQuestion(){
        questionIndex = (questionIndex + 1);
        if(questionIndex == quiz.getQuestions().size())
        {
            questionIndex = 0;
            Intent resultIntent = new Intent(this, ResultActivity.class);
            resultIntent.putExtra(ResultActivity.KEY_SCORE,score);
            resultIntent.putExtra(ResultActivity.KEY_TOTAL,quiz.getQuestions().size());
            startActivity(resultIntent);
        } else{
        //questionIndex = (questionIndex + 1)% quiz.getQuestions().size();
            questionAnswered = false;
            showQuestion();
        }
    }


    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putBoolean(KEY_LAST_ANSWER, lastAnswer);
        outState.putBoolean(KEY_QUESTION_ANSWERED, questionAnswered);
        outState.putInt(KEY_QUESTION_INDEX, questionIndex);
        outState.putInt(KEY_SCORE, score);

    }

    @Override
    protected void onPause() {
        super.onPause();
        SharedPreferences preferences = getSharedPreferences("sharedPrefs", 0);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putBoolean(KEY_LAST_ANSWER,lastAnswer );
        editor.putBoolean(KEY_QUESTION_ANSWERED,questionAnswered);
        editor.putInt(KEY_QUESTION_INDEX, questionIndex);
        editor.putInt(KEY_SCORE, score);
        //quiz instance
        Gson gson = new Gson();
        Type type = new TypeToken<Quiz>() {}.getType();
        String json = gson.toJson(quiz, type);
        System.out.println(json);
        editor.putString(KEY_QUIZ, json);

        // Commit to storage
        editor.apply();

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onFailure() {
        Toast.makeText(this, "Unable to retrieve quiz", Toast.LENGTH_LONG).show();
    }

    @Override
    public void onSuccess(Quiz quiz) {
        this.quiz = quiz;
        showQuestion();

        if (questionAnswered) {
            checkAnswer(lastAnswer);
        }

        //// TODO: 2017-10-19  // list to json --
    }
}
