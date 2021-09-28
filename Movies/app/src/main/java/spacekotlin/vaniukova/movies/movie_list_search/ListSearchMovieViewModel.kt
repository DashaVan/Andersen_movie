package spacekotlin.vaniukova.movies.movie_list_search

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import spacekotlin.vaniukova.movies.movie.Movie
import spacekotlin.vaniukova.movies.movie.MovieRepositoryNetwork

class ListSearchMovieViewModel : ViewModel() {

    private val repository = MovieRepositoryNetwork()
    private val movieListLiveData = MutableLiveData<List<Movie>?>()
    private val isLoadingLiveData = MutableLiveData(false)
    private val isShowingErrorLiveData = MutableLiveData<String>()

    val movieList: MutableLiveData<List<Movie>?>
        get() = movieListLiveData

    val isLoading: LiveData<Boolean>
        get() = isLoadingLiveData

    val showError: LiveData<String>
        get() = isShowingErrorLiveData

    fun search(text: String, year: String, type: String, page: Int) {
        isLoadingLiveData.postValue(true)
        repository.searchMovies(
            text = text, year = year, type = type, page = page,
            onComplete = { movies ->
                isLoadingLiveData.postValue(false)
                movieListLiveData.postValue(movies)
            },
            onError = {
                isLoadingLiveData.postValue(false)
                movieListLiveData.postValue(null)
            },
            message = { message ->
                isShowingErrorLiveData.postValue(message)
            }
        )
    }
}