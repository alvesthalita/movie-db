package com.thalita.movie_db_app.core.plataform

object Constants {
    const val urlGetFilms = "https://api.themoviedb.org/3/discover/movie?api_key=6d9583667c5dfe1cebfc99d3b6819c6b&language=pt-BR&sort_by=popularity.desc&include_adult=false&include_video=false&page=1"
    const val urlMovieDetails = "https://api.themoviedb.org/3/movie/" + "movieID.toString()" +"?api_key=6d9583667c5dfe1cebfc99d3b6819c6b&language=pt-BR"
}