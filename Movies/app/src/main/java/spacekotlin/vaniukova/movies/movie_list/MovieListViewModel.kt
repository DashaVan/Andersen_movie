package spacekotlin.vaniukova.movies.movie_list

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MovieListViewModel : ViewModel() {

    private val repository = MovieRepository()
    private val movieListLiveData = MutableLiveData<List<Movie>?>()
    private val isLoadingLiveData = MutableLiveData<Boolean>(false)
    private val isShowingErrorLiveData = MutableLiveData<String>()

    val movieList: MutableLiveData<List<Movie>?>
        get() = movieListLiveData

    val isLoading: LiveData<Boolean>
        get() = isLoadingLiveData

    val showError: LiveData<String>
        get() = isShowingErrorLiveData

    fun search(text: String, year: String, type: String) {
        isLoadingLiveData.postValue(true)
        repository.searchMovies(
            text = text, year = year, type = type,
            onComplete = { movies ->
                isLoadingLiveData.postValue(false)
                movieListLiveData.postValue(movies)
            },
            onError = { throwable ->
                isLoadingLiveData.postValue(false)
                movieListLiveData.postValue(null)
            },
            message = { message ->
                isShowingErrorLiveData.postValue(message)
            }
        )
    }
}