package spacekotlin.vaniukova.movies.detail_movie

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import spacekotlin.vaniukova.movies.data.MovieDBRepository
import spacekotlin.vaniukova.movies.data.db.models.MovieDB
import spacekotlin.vaniukova.movies.movie.Movie
import spacekotlin.vaniukova.movies.movie.MovieRepositoryNetwork

class DetailFragmentViewModel : ViewModel() {

    private val repository = MovieRepositoryNetwork()
    private val detailMovieLiveData = MutableLiveData<Movie>()
    private val isLoadingLiveData = MutableLiveData(false)

    val detailMovie: MutableLiveData<Movie>
        get() = detailMovieLiveData

    val isLoading: LiveData<Boolean>
        get() = isLoadingLiveData

    fun requestMovieById(movieId: String) {
        isLoadingLiveData.postValue(true)
        repository.searchMovieById(movieId) { movie ->
            detailMovieLiveData.postValue(movie)
            isLoadingLiveData.postValue(false)
        }
    }


    private val movieRepository = MovieDBRepository()

    fun save(
        movie: Movie
    ) {
        val favouriteMovie = MovieDB(
            idString = movie.idString,
            title = movie.title,
            year = movie.year,
            type = movie.type,
            poster = movie.poster,
            plot = movie.plot,
        )

        viewModelScope.launch {
            movieRepository.saveMovie(favouriteMovie)
        }
    }

    fun removeFavouriteMovie(movie: Movie) {
        viewModelScope.launch {
            try {
                movieRepository.removeFavouriteMovie(movie.idString)
            } catch (t: Throwable) {
                Log.e("detailFragment", "error remove favouriteMovie")
            }
        }
    }
}