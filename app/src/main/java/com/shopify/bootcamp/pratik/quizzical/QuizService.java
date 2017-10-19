package com.shopify.bootcamp.pratik.quizzical;

import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by androidbootcamp3 on 2017-10-19.
 */
public interface QuizService {

    @GET("cdn/quiz.json")
    Call<Quiz> getQuiz();
}


