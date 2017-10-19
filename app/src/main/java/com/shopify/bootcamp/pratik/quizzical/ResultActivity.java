package com.shopify.bootcamp.pratik.quizzical;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

/**
 * Created by androidbootcamp3 on 2017-10-18.
 */

public class ResultActivity extends AppCompatActivity {

    public  static  String KEY_SCORE = "score";
    public  static  String KEY_TOTAL = "total";
    private static final String KEY_LAST_ANSWER = "lastAnswer";
    private static final String KEY_QUESTION_ANSWERED = "questionAnswered";
    private static final String KEY_QUESTION_INDEX = "questionIndex";
    private static final String KEY_QUIZ = "quiz";


    private Button try_button, logout_button;

    private  TextView resultText;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        resultText = (TextView) findViewById(R.id.result_text);
        try_button = (Button) findViewById(R.id.try_button);
        logout_button = (Button) findViewById(R.id.logout_button);


        Intent intent = getIntent();
        int score = intent.getIntExtra(KEY_SCORE, -1);
        int total = intent.getIntExtra(KEY_TOTAL, -1);

        String result = String.format("%d / %d", score, total);
        resultText.setText(result);

        try_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SwitchActivity(0);
                Log.d("HI", "Retry button clicked");
            }
        });

        logout_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SwitchActivity(1);
                Log.d("HI", "Logout button clicked");
            }
        });
    }

    protected  void SwitchActivity(int i)
    {

        Intent resultIntent = null;
        if(i==0)
        {
            resultIntent = new Intent(this, ListActivity.class);
        }else if (i == 1)
        {
            resultIntent = new Intent(this, LoginActivity.class);
        } else {
            resultIntent = new Intent(this, ResultActivity.class);

        }
        //resultIntent.removeExtra(KEY_SCORE);
/*

        SharedPreferences preferences = getSharedPreferences("sharedPrefs", 0);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putBoolean(KEY_LAST_ANSWER,false );
        editor.putBoolean(KEY_QUESTION_ANSWERED,false);
        editor.putInt(KEY_QUESTION_INDEX, 0);
        editor.putInt(KEY_SCORE, score);
*/

        startActivity(resultIntent);
    }
}
