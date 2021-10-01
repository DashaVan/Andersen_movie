package spacekotlin.vaniukova.movies.top_movies_list

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import spacekotlin.vaniukova.movies.data.MovieDBRepository
import spacekotlin.vaniukova.movies.data.db.models.TopMovieDB
import spacekotlin.vaniukova.movies.movie.Movie
import spacekotlin.vaniukova.movies.data.MovieRepositoryNetwork

class TopMoviesViewModel : ViewModel() {

    private val movieIds = TopMoviesList.movieIds

    private val repository = MovieRepositoryNetwork()
    private val topMoviesLiveData = MutableLiveData<List<Movie>?>()
    private val isLoadingLiveData = MutableLiveData(false)

    val topMovies: MutableLiveData<List<Movie>?>
        get() = topMoviesLiveData

    val isLoading: LiveData<Boolean>
        get() = isLoadingLiveData

    fun requestMovies() {
        isLoadingLiveData.postValue(true)
        repository.fetchTopMovies(movieIds) { listMovies ->
            topMoviesLiveData.postValue(listMovies)
            listMovies.forEach { saveTopMovies(it) }
            isLoadingLiveData.postValue(false)
        }
    }


    private val movieDBRepository = MovieDBRepository()
    private val dbTopMoviesLiveData = MutableLiveData<List<TopMovieDB>>()

    val dbTopMovies: MutableLiveData<List<TopMovieDB>>
        get() = dbTopMoviesLiveData

    private fun saveTopMovies(
        movie: Movie
    ) {
        val topMovie = TopMovieDB(
            idString = movie.idString,
            title = movie.title,
            year = movie.year,
            type = movie.type,
            poster = movie.poster,
            plot = movie.plot,
        )

        viewModelScope.launch {
            movieDBRepository.saveTopMovie(topMovie)
        }
    }

    fun loadListFromDB() {
        viewModelScope.launch {
            try {
                dbTopMoviesLiveData.postValue(movieDBRepository.getAllTopMovies())
            } catch (t: Throwable) {
                dbTopMoviesLiveData.postValue(emptyList())
            }
        }
    }
}