package spacekotlin.vaniukova.movies.top_movies_list

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import spacekotlin.vaniukova.movies.data.MovieDBRepository
import spacekotlin.vaniukova.movies.data.db.models.TopMovieDB
import spacekotlin.vaniukova.movies.movie.Movie
import spacekotlin.vaniukova.movies.movie.MovieRepositoryNetwork

class TopMoviesViewModel : ViewModel() {

    private val movieIds = TopMoviesList.movieIds

    private val repository = MovieRepositoryNetwork()
    private val topMoviesLiveData = MutableLiveData<List<Movie>?>()

    val topMovies: MutableLiveData<List<Movie>?>
        get() = topMoviesLiveData

    fun requestMovies() {
        repository.fetchTopMovies(movieIds) { listMovies ->
            topMoviesLiveData.postValue(listMovies)
            listMovies.forEach { saveTopMovies(it) }
        }
    }


    private val movieDBRepository = MovieDBRepository()
    private val dbTopMoviesLiveData = MutableLiveData<List<TopMovieDB>>()
    private val dbIsNotEmptyLiveData = MutableLiveData<Boolean>()

    val dbTopMovies: MutableLiveData<List<TopMovieDB>>
        get() = dbTopMoviesLiveData

    val dbIsNotEmpty: MutableLiveData<Boolean>
        get() = dbIsNotEmptyLiveData

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
            dbIsNotEmptyLiveData.postValue(true)
        }
    }

    fun loadListFromDB() {
        viewModelScope.launch {
            try {
                dbTopMoviesLiveData.postValue(movieDBRepository.getAllTopMovies())
                dbIsNotEmptyLiveData.postValue(true)
            } catch (t: Throwable) {
                dbTopMoviesLiveData.postValue(emptyList())
                dbIsNotEmptyLiveData.postValue(false)
            }
        }
    }
}