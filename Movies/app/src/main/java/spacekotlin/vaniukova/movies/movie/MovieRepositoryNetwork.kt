package spacekotlin.vaniukova.movies.movie

import android.util.Log
import retrofit2.Call
import retrofit2.Response
import spacekotlin.vaniukova.movies.network.API_KEY
import spacekotlin.vaniukova.movies.network.Network
import spacekotlin.vaniukova.movies.network.ServerItemsWrapper

class MovieRepositoryNetwork {

    var searchMovieList: List<Movie> = emptyList()

    fun searchMovies(
        text: String, year: String, type: String, page: Int,
        onComplete: (List<Movie>) -> Unit,
        onError: (Throwable) -> Unit,
        message: (String) -> Unit
    ) {
        Network.movieApi.getSearchMovieList(API_KEY, text, year, type, page)
            .enqueue(object : retrofit2.Callback<ServerItemsWrapper<Movie>> {
                override fun onResponse(
                    call: retrofit2.Call<ServerItemsWrapper<Movie>>,
                    response: retrofit2.Response<ServerItemsWrapper<Movie>>
                ) {
                    if (response.isSuccessful) {
                        if (response.body()?.Error.orEmpty().isNotEmpty()){
                            message(response.body()?.Error.orEmpty())
                        }

                        searchMovieList = if(page > 1){
                            searchMovieList + response.body()?.Search.orEmpty()
                        }else{
                            response.body()?.Search.orEmpty()
                        }
                        onComplete(searchMovieList)

                    } else {
                        onError(RuntimeException("incorrect status code"))
                    }
                }

                override fun onFailure(
                    call: retrofit2.Call<ServerItemsWrapper<Movie>>,
                    t: Throwable
                ) {
                    onError(t)
                }
            })
    }


    fun searchMovieById(
        idString: String,
        onComplete: (Movie) -> Unit
    ) {
        Network.movieApi.getMovieById(API_KEY, idString)
            .enqueue(object : retrofit2.Callback<Movie> {
                override fun onResponse(
                    call: retrofit2.Call<Movie>,
                    response: retrofit2.Response<Movie>
                ) {
                    if (response.isSuccessful) {
                        val movie = response.body()
                        if (movie != null) {
                            onComplete(movie)
                        } else {
                            val errorMovie = Movie("", "Film not found", "", "", "", "")
                            onComplete(errorMovie)
                        }
                    } else {
                        Log.e("Server", "incorrect status code")
                    }
                }

                override fun onFailure(call: retrofit2.Call<Movie>, t: Throwable) {
                    Log.e("Server", "execute request error = ${t.message}", t)
                }
            })
    }


    var topMoviesList = mutableListOf<Movie>()

    fun fetchTopMovies(
        movieIds: List<String>,
        onMoviesFetched: (listMovies: List<Movie>) -> Unit
    ) {
        movieIds.forEach { movieId ->
            Network.movieApi.getMovieById(API_KEY, movieId)
                .enqueue(object : retrofit2.Callback<Movie> {
                    override fun onResponse(call: Call<Movie>, response: Response<Movie>) {
                        if (response.isSuccessful) {
                            response.body()?.let {
                                topMoviesList += listOf(it)
                                onMoviesFetched(topMoviesList) }
                        } else {
                            Log.e("Server", "incorrect status code")
                        }
                    }

                    override fun onFailure(call: Call<Movie>, t: Throwable) {
                        Log.e("Server", "execute request error = ${t.message}", t)
                    }
                })
        }
    }
}