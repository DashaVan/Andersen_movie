package spacekotlin.vaniukova.movies.top_movies_list

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import spacekotlin.vaniukova.movies.movie_list.Movie
import spacekotlin.vaniukova.movies.movie_list.MovieRepository

class TopMoviesViewModel : ViewModel() {

    private val movieIds = TopMoviesList.movieIds

    private val repository = MovieRepository()
    private val topMoviesLiveData = MutableLiveData<List<Movie>?>()

    val topMovies: MutableLiveData<List<Movie>?>
        get() = topMoviesLiveData

    fun requestMovies() {
        repository.fetchTopMovies(movieIds) { listMovies ->
            topMoviesLiveData.postValue(listMovies)
        }
    }
}