package spacekotlin.vaniukova.movies.network

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query
import spacekotlin.vaniukova.movies.movie_list.Movie

interface MovieApi {
    @GET("/")
    fun getSearchMovieList(
        @Query("apikey") apikey: String,
        @Query("s") title: String
    ): Call<ServerItemsWrapper<Movie>>
}