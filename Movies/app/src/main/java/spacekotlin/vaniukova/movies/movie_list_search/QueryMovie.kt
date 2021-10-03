package spacekotlin.vaniukova.movies.movie_list_search

interface QueryMovie {
    fun query(title: String, year: String, type: String, page: Int)
}