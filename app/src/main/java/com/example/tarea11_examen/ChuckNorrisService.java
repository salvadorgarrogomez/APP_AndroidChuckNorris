package com.example.tarea11_examen;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

// En esta clase Interface, se puede apreciar los distintos campos utilizados para la practica,
// por un lado tenemos el getRandomJoke() con el que obtenemos una broma aleatoria
// por otro tenemos el getCategories() que nos muestra el conjunto de opciones y contectos de las bromas
// y por ultimo, un nuevo campo, en el cual deberia de implemetarse la realizacion de bromas segun su contexto
// segun la opcion escogida en getCategories()
public interface ChuckNorrisService {

    @GET("jokes/random")
    Call<ChuckNorrisJoke> getRandomJoke();

    @GET("jokes/categories")
    Call<List<String>> getCategories();

    @GET("jokes/random")
    Call<ChuckNorrisJoke> getJokeByCategory(@Query("category") String category);
}






