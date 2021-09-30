package spacekotlin.vaniukova.movies.favourite_movies

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import spacekotlin.vaniukova.movies.data.MovieDBRepository
import spacekotlin.vaniukova.movies.data.db.models.MovieDB

class FavouriteMoviesViewModel : ViewModel() {

    private val repository = MovieDBRepository()
    private val favouriteMoviesLiveData = MutableLiveData<List<MovieDB>>()

    val favouriteMovies: MutableLiveData<List<MovieDB>>
        get() = favouriteMoviesLiveData

    fun loadList() {
        viewModelScope.launch {
            try {
                favouriteMoviesLiveData.postValue(repository.getAllFavouriteMovies())
            } catch (t: Throwable) {
                favouriteMoviesLiveData.postValue(emptyList())
            }
        }
    }
}