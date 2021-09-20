package spacekotlin.vaniukova.movies.movie_list

import spacekotlin.vaniukova.movies.movie_list.Movie

object MovieList {
    val list = arrayListOf<Movie>(
        Movie(
            id = 123,
            title = "MyFilm",
            year = "2021",
            poster = "https://picsum.photos/id/237/200/300"
        )
    )
}