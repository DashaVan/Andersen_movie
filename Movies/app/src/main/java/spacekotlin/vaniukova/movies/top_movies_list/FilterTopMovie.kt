package spacekotlin.vaniukova.movies.top_movies_list

interface FilterTopMovie {
    fun filterWithYear(year: Int)
    fun filterDes()
    fun filterAsc()
}