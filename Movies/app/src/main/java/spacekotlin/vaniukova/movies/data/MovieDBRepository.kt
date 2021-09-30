package spacekotlin.vaniukova.movies.data

import spacekotlin.vaniukova.movies.data.db.Database
import spacekotlin.vaniukova.movies.data.db.models.MovieDB
import spacekotlin.vaniukova.movies.data.db.models.TopMovieDB

class MovieDBRepository {

    private val movieDao = Database.instance.movieDao()
    private val topMovieDao = Database.instance.topMovieDao()


    suspend fun saveMovie(movie: MovieDB) {
        movieDao.insertMovies(listOf(movie))
    }

    suspend fun getAllFavouriteMovies(): List<MovieDB> {
        return movieDao.getAllFavouriteMovies()
    }

    /*suspend fun getMovieById(id: String): MovieDB? {
        return movieDao.getMovieById(id)
    }*/

    suspend fun removeFavouriteMovie(id: String) {
        movieDao.removeFavouriteMovieById(id)
    }

    suspend fun saveTopMovie(movie: TopMovieDB) {
        topMovieDao.insertMovies(listOf(movie))
    }

    suspend fun getAllTopMovies(): List<TopMovieDB> {
        return topMovieDao.getAllTopMovies()
    }
}