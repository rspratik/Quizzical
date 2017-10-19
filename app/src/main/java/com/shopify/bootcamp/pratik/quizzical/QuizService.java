package com.shopify.bootcamp.pratik.quizzical;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by androidbootcamp3 on 2017-10-19.
 */
public interface QuizService {

    @GET("cdn/quiz.json")
    Call<Quiz> getQuiz();


    @GET("cdn/quizzes.json")
    Call<List<Quiz>> getQuizzes();

    @GET("cdn/quizzes/{id}.json")
    Call<Quiz> getQuiz(@Path("id") int id);

}


