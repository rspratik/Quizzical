package com.shopify.bootcamp.pratik.quizzical;

import android.content.Context;
import android.content.res.AssetManager;
import android.util.Log;

import com.squareup.moshi.JsonAdapter;
import com.squareup.moshi.Moshi;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Random;

import okio.Okio;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.moshi.MoshiConverterFactory;

/**
 * Created by androidbootcamp3 on 2017-10-19.
 */

public class QuizRepository {
    private final Context context;
    private  static  final  String LOG_TAG = QuizRepository.class.getSimpleName();
    private  static  final  String QUIZ_JSON = "quiz.json";
    private  static  final  String QUIZ_JSON1 = "quiz1.json";
    private  static  final  String BASE_URL ="https://oolong.tahnok.me/";

    public QuizRepository(Context context) {
        this.context = context;
    }

    public Quiz getQuiz() {
        InputStream assetInputStream;
        try {
            Random random = new Random();
            String fileName = random.nextInt(2) == 1 ? QUIZ_JSON: QUIZ_JSON1;
            assetInputStream = context.getAssets().open(QUIZ_JSON);
        } catch (IOException e) {
            Log.e(LOG_TAG, "Could open quiz parse json", e);
            return null;
        }
        Moshi moshi = new Moshi.Builder().build();
        JsonAdapter<Quiz> jsonAdapter = moshi.adapter(Quiz.class);

        try {
            Quiz quiz = jsonAdapter.fromJson(Okio.buffer(Okio.source(assetInputStream)));
            return quiz;
        } catch (IOException e) {
            Log.e(LOG_TAG, "Could not parse json", e);
            return null;
        }

    }

    public void getRemoteQuiz(int id, final Callback callback) {
        QuizService service = getQuizService();

        service.getQuiz(id).enqueue(new retrofit2.Callback<Quiz>() {
            @Override
            public void onResponse(Call<Quiz> call, Response<Quiz> response) {
                if (response.isSuccessful()) {
                    callback.onSuccess(response.body());
                } else {
                    callback.onFailure();
                }
            }

            @Override
            public void onFailure(Call<Quiz> call, Throwable t) {
                callback.onFailure();
            }
        });
    }

/*

    public void getRemoteQuiz(final Callback callback) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(MoshiConverterFactory.create())
                .build();

        //QuizService service = retrofit.create(QuizService.class);
        QuizService service = getQuizService();

*/
/*
        try {
            Response<Quiz> response = service.getQuiz().execute();
            if (!response.isSuccessful()) {
                return null;
            }
            Quiz quiz = response.body();
            return quiz;
        } catch (IOException e) {
            Log.e(LOG_TAG, "Failure to fetch data", e);
            return null;
        }*//*

        service.getQuiz().enqueue(new retrofit2.Callback<Quiz>() {
            @Override
            public void onResponse(Call<Quiz> call, Response<Quiz> response) {
                if (response.isSuccessful()) {
                    callback.onSuccess(response.body());
                } else {
                    callback.onFailure();
                }
            }

            @Override
            public void onFailure(Call<Quiz> call, Throwable t) {
                callback.onFailure();
            }
        });

    }
*/

    public interface  Callback {
        void  onFailure();
        void  onSuccess(Quiz quiz);
    }

    public interface QuizzesCallback {
        void onFailure();
        void onSuccess(List<Quiz> quizzes);
    }


    private QuizService getQuizService() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(MoshiConverterFactory.create())
                .build();
        return retrofit.create(QuizService.class);
    }

    public void getRemoteQuizzes(final QuizzesCallback callback) {
        QuizService service = getQuizService();

        service.getQuizzes().enqueue(new retrofit2.Callback<List<Quiz>>() {
            @Override
            public void onResponse(Call<List<Quiz>> call, Response<List<Quiz>> response) {
                if (response.isSuccessful()) {
                    callback.onSuccess(response.body());
                } else {
                    callback.onFailure();
                }
            }

            @Override
            public void onFailure(Call<List<Quiz>> call, Throwable t) {
                callback.onFailure();
            }
        });
    }



}

