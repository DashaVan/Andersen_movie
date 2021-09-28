package spacekotlin.vaniukova.movies.detail_movie

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
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
}