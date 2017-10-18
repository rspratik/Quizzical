package com.shopify.bootcamp.pratik.quizzical;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

/**
 * Created by androidbootcamp3 on 2017-10-18.
 */

public class ResultActivity extends AppCompatActivity {

    public  static  String KEY_SCORE = "score";
    public  static  String KEY_TOTAL = "total";

    private  TextView resultText;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        resultText = (TextView) findViewById(R.id.result_text);
        Intent intent = getIntent();
        int score = intent.getIntExtra(KEY_SCORE, -1);
        int total = intent.getIntExtra(KEY_TOTAL, -1);

        String result = String.format("%d / %d", score, total);
        resultText.setText(result);
    }
}
