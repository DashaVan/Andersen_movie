package spacekotlin.vaniukova.movies.movie_list

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import spacekotlin.vaniukova.movies.DialogSearchMovieFragment
import spacekotlin.vaniukova.movies.Navigator
import spacekotlin.vaniukova.movies.R
import spacekotlin.vaniukova.movies.databinding.FragmentListBinding
import spacekotlin.vaniukova.movies.detail_movie.DetailFragment
import spacekotlin.vaniukova.movies.utils.autoCleared

var dialogSearchMovieShowed = false

class ListFragment : Fragment(R.layout.fragment_list), QueryMovie {
    private var _binding: FragmentListBinding? = null
    private val binding get() = _binding!!

    private var movieAdapter: MovieListAdapter by autoCleared()
    private val viewModel: MovieListViewModel by viewModels()

    private var errorDialog: AlertDialog? = null
    private var errorText: String = ""

    private var lastQueryTitle = ""
    private var lastQueryYear = ""
    private var lastQueryType = ""
    private var lastPage = 1

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
        errorDialog?.dismiss()
        errorDialog = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initList()
        bindViewModel()

        if(!dialogSearchMovieShowed){
            showDialog(DialogSearchMovieFragment())
        }

        binding.btnSearch.setOnClickListener {
            showDialog(DialogSearchMovieFragment())
        }

        binding.btnMore.setOnClickListener {
            lastPage++
            query(lastQueryTitle, lastQueryYear, lastQueryType, lastPage)
        }
    }

    private fun initList() {
        movieAdapter = MovieListAdapter { id -> openDetailFragment(id) }
        with(binding.recyclerViewMovies) {
            adapter = movieAdapter
            layoutManager = LinearLayoutManager(requireContext())
            setHasFixedSize(true)
        }
    }

    private fun openDetailFragment(id: String) {
        (activity as Navigator).navigateTo(DetailFragment.newInstance(id), "detailFragment")
    }

    private fun bindViewModel() {
        with(viewModel) {
            isLoading.observe(viewLifecycleOwner, ::updateLoadingState)
            movieList.observe(viewLifecycleOwner) { movieAdapter.items = it }
            showError.observe(viewLifecycleOwner) { errorText = it }
        }
    }

    private fun updateLoadingState(isLoading: Boolean) {
        with(binding){
            recyclerViewMovies.isVisible = isLoading.not()
            progressBar.isVisible = isLoading
            btnSearch.isEnabled = isLoading.not()
            btnMore.isEnabled = isLoading.not()
        }

        if (errorText.isNotEmpty()) {
            showErrorDialog(errorText)
        }
    }

    private fun showErrorDialog(text: String) {
        binding.btnMore.visibility = View.GONE
        errorDialog = AlertDialog.Builder(requireContext())
            .setMessage(text)
            .setNegativeButton("Cancel", null)
            .show()
    }

    private fun showDialog(dialog: DialogFragment) {
        dialog.show(childFragmentManager, "${dialog.tag}")
        dialogSearchMovieShowed = true
    }

    override fun query(title: String, year: String, type: String, page: Int) {
        lastQueryTitle = title
        lastQueryYear = year
        lastQueryType = type
        lastPage = page
        viewModel.search(lastQueryTitle, lastQueryYear, lastQueryType, page)
        errorText = ""

        binding.btnMore.visibility = View.VISIBLE
    }
}