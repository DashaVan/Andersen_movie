package spacekotlin.vaniukova.movies.movie_list

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MovieListViewModel : ViewModel() {

    private val repository = MovieRepository()
    private val movieListLiveData = MutableLiveData<List<Movie>?>()
    private val isLoadingLiveData = MutableLiveData<Boolean>(false)

    val movieList: MutableLiveData<List<Movie>?>
        get() = movieListLiveData

    val isLoading: LiveData<Boolean>
        get() = isLoadingLiveData

    fun search(text: String) {
        isLoadingLiveData.postValue(true)
        repository.searchMovies(
            text = text,
            onComplete = { movies ->
                isLoadingLiveData.postValue(false)
                movieListLiveData.postValue(movies)
            },
            onError = {
                isLoadingLiveData.postValue(false)
                movieListLiveData.postValue(null)
            }
        )
    }
}