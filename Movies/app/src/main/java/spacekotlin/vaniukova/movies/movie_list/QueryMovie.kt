package spacekotlin.vaniukova.movies.movie_list

interface QueryMovie {
    fun query(title: String, year: String, type: String)
}