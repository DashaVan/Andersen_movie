package spacekotlin.vaniukova.movies.top_movies_list

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import spacekotlin.vaniukova.movies.MainActivity
import spacekotlin.vaniukova.movies.Navigator
import spacekotlin.vaniukova.movies.R
import spacekotlin.vaniukova.movies.data.db.models.TopMovieDB
import spacekotlin.vaniukova.movies.databinding.FragmentTopMoviesBinding
import spacekotlin.vaniukova.movies.detail_movie.DetailFragment
import spacekotlin.vaniukova.movies.movie.Movie
import spacekotlin.vaniukova.movies.movie.adapter.MovieListAdapter
import spacekotlin.vaniukova.movies.utils.autoCleared

class TopMoviesFragment : Fragment(R.layout.fragment_top_movies) {
    private var _binding: FragmentTopMoviesBinding? = null
    private val binding get() = _binding!!

    private val sharedPrefs by lazy {
        requireContext().getSharedPreferences(SHARED_PREFS_NAME, Context.MODE_PRIVATE)
    }

    var firstRun: Boolean = true

    private var topMoviesAdapter: MovieListAdapter by autoCleared()
    private val topMoviesViewModel: TopMoviesViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentTopMoviesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (activity as MainActivity).setToolbarTitle("TOP-25")

        firstRun = sharedPrefs.getBoolean(FIRST_RUN, true)
        if (firstRun) {
            bindViewModelNetwork()
            sharedPrefs.edit().putBoolean(FIRST_RUN, false).apply()
        } else {
            bindViewModelDB()
        }
        initList()
    }

    private fun initList() {
        topMoviesAdapter = MovieListAdapter { id -> openDetailFragment(id) }
        with(binding.recyclerViewTopMovies) {
            adapter = topMoviesAdapter
            layoutManager = LinearLayoutManager(requireContext())
            setHasFixedSize(true)
        }
    }

    private fun openDetailFragment(id: String) {
        (activity as Navigator).navigateTo(DetailFragment.newInstance(id, false), "detailFragment")
    }

    private fun bindViewModelNetwork() {
        topMoviesViewModel.topMovies.observe(viewLifecycleOwner) { topMoviesAdapter.items = it }
        topMoviesViewModel.requestMovies()
    }

    private fun bindViewModelDB() {
        topMoviesViewModel.dbTopMovies.observe(viewLifecycleOwner) { list ->
            topMoviesAdapter.items = list.map { transformMovieDBtoMovie(it) }
        }
        topMoviesViewModel.loadListFromDB()
    }

    private fun transformMovieDBtoMovie(topMovieDB: TopMovieDB): Movie {
        return Movie(
            topMovieDB.idString,
            topMovieDB.title,
            topMovieDB.year,
            topMovieDB.type,
            topMovieDB.poster,
            topMovieDB.plot
        )
    }

    companion object {
        private const val SHARED_PREFS_NAME = "movies_shared_pref"
        private const val FIRST_RUN = "first_run"
    }
}