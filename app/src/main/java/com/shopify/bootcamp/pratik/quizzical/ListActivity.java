package com.shopify.bootcamp.pratik.quizzical;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Arrays;
import java.util.List;

public class ListActivity extends AppCompatActivity implements QuizRepository.QuizzesCallback {

    private ListView listView;
    private QuizAdapter quizAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
        listView = (ListView) findViewById(R.id.list_view);
        /*List<String> strings = Arrays.asList("First", "Second", "Third");
        //adapter is to put strings into view
        ArrayAdapter adapter = new ArrayAdapter(this,android.R.layout.simple_list_item_1,strings);
        listView.setAdapter(adapter);*/
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Quiz quiz = quizAdapter.getItem(position);
                Intent intent = new Intent(ListActivity.this, MainActivity.class);
                intent.putExtra("quiz_id", quiz.getId());
                startActivity(intent);
            }
        });


        new QuizRepository(this).getRemoteQuizzes(this);
    }

    @Override
    public void onFailure() {
        Toast.makeText(this, "Could not retrieve quiz list", Toast.LENGTH_LONG).show();
    }

    @Override
    public void onSuccess(List<Quiz> quizzes) {
        //ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, quizzes);
        quizAdapter = new QuizAdapter(this, quizzes);
        listView.setAdapter(quizAdapter);
    }

    class QuizAdapter extends ArrayAdapter<Quiz> {

        public QuizAdapter(@NonNull Context context, @NonNull List<Quiz> quizzes) {
            //0 to layout
            super(context, 0, quizzes);
        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            if (convertView == null) {
                // recycler view so use cyclic queue while scrolling up the list of items
                convertView = LayoutInflater.from(getContext()).inflate(android.R.layout.simple_list_item_1, parent, false);
            }
            TextView text = (TextView) convertView;

            Quiz quiz = getItem(position);

            text.setText(quiz.getTitle());

            return convertView;
        }

    }

}
