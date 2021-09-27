package spacekotlin.vaniukova.movies.network

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query
import spacekotlin.vaniukova.movies.movie_list.Movie

interface MovieApi {
    @GET("/")
    fun getSearchMovieList(
        @Query("apikey") apikey: String,
        @Query("s") title: String,
        @Query("y") year: String,
        @Query("type") type: String,
        @Query("page") page: Int
    ): Call<ServerItemsWrapper<Movie>>

    @GET("/")
    fun getMovieById(
        @Query("apikey") apikey: String,
        @Query("i") id: String,
        @Query("plot") plot: String = "full"
    ): Call<Movie>
}