package com.example.tinderdog.api;

import com.example.tinderdog.model.Dog;

import retrofit2.Call;
import retrofit2.http.GET;

public interface DogService {

    @GET("retriever-golden/images/random")
    Call <Dog> recuperarDog();
}
