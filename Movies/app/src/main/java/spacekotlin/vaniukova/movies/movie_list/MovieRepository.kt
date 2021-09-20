package spacekotlin.vaniukova.movies.movie_list

import android.util.Log
import spacekotlin.vaniukova.movies.network.API_KEY
import spacekotlin.vaniukova.movies.network.ServerItemsWrapper
import spacekotlin.vaniukova.movies.network.Network

class MovieRepository {

    fun searchMovies(
        text: String,
        onComplete: (List<Movie>) -> Unit,
        onError: (Throwable) -> Unit
    ) {
        Network.movieApi.getSearchMovieList(API_KEY, text)
            .enqueue(object : retrofit2.Callback<ServerItemsWrapper<Movie>> {
                override fun onResponse(
                    call: retrofit2.Call<ServerItemsWrapper<Movie>>,
                    response: retrofit2.Response<ServerItemsWrapper<Movie>>
                ) {
                    if (response.isSuccessful) {
                        onComplete(response.body()?.Search.orEmpty())
                    } else {
                        onError(RuntimeException("incorrect status code"))
                    }
                }

                override fun onFailure(
                    call: retrofit2.Call<ServerItemsWrapper<Movie>>,
                    t: Throwable
                ) {
                    Log.e("Server", "execute request error = ${t.message}", t)
                }
            })
    }


    /*fun searchMovie1(
        text: String,
        onComplete: (List<Movie>) -> Unit,
        onError: (Throwable) -> Unit
    ) {
        Network.movieApi.getSearchMovieList(API_KEY, text)
            .enqueue(object : retrofit2.Callback<Movie> {
                override fun onResponse(
                    call: retrofit2.Call<Movie>,
                    response: retrofit2.Response<Movie>
                ) {
                    Log.d("listFragment", "response")
                    if (response.isSuccessful) {
                        Log.d("Server", "response is successful")



                        val list = mutableListOf<Movie>()
                        *//*val idString = response.body()?.idString.orEmpty()
                        val title = response.body()?.title.orEmpty()
                        val year = response.body()?.year.orEmpty()
                        val poster = response.body()?.poster.orEmpty()*//*

                        //val movie = Movie(idString, title, year, poster)
                        val movie1 = response.body()

                        if (movie1 != null) {
                            list.add(movie1)
                        }
                        onComplete(list)
                    } else {
                        onError(RuntimeException("incorrect status code"))
                    }
                }

                override fun onFailure(call: retrofit2.Call<Movie>, t: Throwable) {
                    Log.e("Server", "execute request error = ${t.message}", t)
                }
            })
    }
*/
}