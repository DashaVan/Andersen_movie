package spacekotlin.vaniukova.movies.top_movies_list

import android.app.AlertDialog
import android.content.Context
import android.opengl.Visibility
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isEmpty
import androidx.core.view.isVisible
import androidx.fragment.app.DialogFragment
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
import spacekotlin.vaniukova.movies.movie_list_search.DialogSearchMovieFragment
import spacekotlin.vaniukova.movies.movie_list_search.QueryMovie
import spacekotlin.vaniukova.movies.movie_list_search.dialogSearchMovieShowed
import spacekotlin.vaniukova.movies.utils.autoCleared

class TopMoviesFragment : Fragment(R.layout.fragment_top_movies), FilterTopMovie {
    private var _binding: FragmentTopMoviesBinding? = null
    private val binding get() = _binding!!

    private val sharedPrefs by lazy {
        requireContext().getSharedPreferences(SHARED_PREFS_NAME, Context.MODE_PRIVATE)
    }

    var firstRun: Boolean = true

    private var topMoviesAdapter: MovieListAdapter by autoCleared()
    private val topMoviesViewModel: TopMoviesViewModel by viewModels()
    private var errorDialog: AlertDialog? = null

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
        errorDialog?.dismiss()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (activity as MainActivity).setToolbarTitle("TOP-25")
        (activity as MainActivity).setToolbarStar()
        firstRun = sharedPrefs.getBoolean(FIRST_RUN, true)
        dataRequest(firstRun)
        initList()

        binding.btnTryAgain.setOnClickListener {
            dataRequest(firstRun)
        }

        binding.filterFab.setOnClickListener {
            showFilterDialog(DialogFilterFragment())
        }
    }

    private fun dataRequest(isFirst: Boolean){
        if (isFirst) {
            if(MainActivity().isOnline(requireContext())){
                bindViewModelNetwork()
                sharedPrefs.edit().putBoolean(FIRST_RUN, false).apply()
                binding.btnTryAgain.visibility = View.GONE
            }else{
                binding.btnTryAgain.visibility = View.VISIBLE
                showErrorDialog("No Internet!", "Please check your internet connection and try again")
            }
        } else {
            binding.btnTryAgain.visibility = View.GONE
            bindViewModelDB()
        }
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
        if(MainActivity().isOnline(requireContext())){
            (activity as Navigator).navigateTo(DetailFragment.newInstance(id, false), "detailFragment")
        }else{
            showErrorDialog("No Internet!", "Please check your internet connection and try again")
        }
    }

    private fun showErrorDialog(title: String, message: String) {
        errorDialog = AlertDialog.Builder(requireContext())
            .setTitle(title)
            .setMessage(message)
            .setPositiveButton(R.string.ok, null)
            .show()
    }

    private fun bindViewModelNetwork() {
        with(topMoviesViewModel){
            isLoading.observe(viewLifecycleOwner, ::updateLoadingState)
            topMovies.observe(viewLifecycleOwner) { topMoviesAdapter.items = it }
            requestMovies()
        }
    }

    private fun updateLoadingState(isLoading: Boolean) {
        binding.progressBar2.isVisible = isLoading
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
            topMovieDB.year.toString(),
            topMovieDB.type,
            topMovieDB.poster,
            topMovieDB.plot
        )
    }

    companion object {
        private const val SHARED_PREFS_NAME = "movies_shared_pref"
        private const val FIRST_RUN = "first_run"
    }

    override fun filterWithYear(year: Int) {
        topMoviesViewModel.loadTopWithYearFromDB(year)
        topMoviesViewModel.dbTopWithYear.observe(viewLifecycleOwner) { list ->
            topMoviesAdapter.items = list.map { transformMovieDBtoMovie(it) }
        }
    }

    override fun filterDes() {
        topMoviesViewModel.loadTopWithDescending()
        topMoviesViewModel.dbTopWithYear.observe(viewLifecycleOwner) { list ->
            topMoviesAdapter.items = list.map { transformMovieDBtoMovie(it) }
        }
    }

    override fun filterAsc() {
        topMoviesViewModel.loadTopWithAscending()
        topMoviesViewModel.dbTopWithYear.observe(viewLifecycleOwner) { list ->
            topMoviesAdapter.items = list.map { transformMovieDBtoMovie(it) }
        }
    }

    private fun showFilterDialog(dialog: DialogFragment) {
        dialog.show(childFragmentManager, "${dialog.tag}")
    }
}