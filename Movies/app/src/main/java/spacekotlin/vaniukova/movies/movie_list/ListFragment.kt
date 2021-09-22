package spacekotlin.vaniukova.movies.movie_list

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import spacekotlin.vaniukova.movies.R
import spacekotlin.vaniukova.movies.databinding.FragmentListBinding
import spacekotlin.vaniukova.movies.utils.autoCleared

class ListFragment: Fragment(R.layout.fragment_list) {
    private var _binding: FragmentListBinding? = null
    private val binding get() = _binding!!

    private var movieAdapter: MovieListAdapter by autoCleared()
    private val viewModel: MovieListViewModel by viewModels()

    private var errorDialog: AlertDialog? = null
    private var errorText: String = ""

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

        bindViewModel()
        initList()
    }

    private fun initList() {
        movieAdapter = MovieListAdapter { id -> openDetailFragment(id) }
        with(binding.recyclerViewMovies) {
            adapter = movieAdapter
            layoutManager = LinearLayoutManager(requireContext())
            setHasFixedSize(true)
        }
    }

    private fun openDetailFragment(id: Long) {

    }

    private fun bindViewModel() {
        binding.btnSearch.setOnClickListener {
            query()
        }

        with(viewModel){
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
        }

        if (errorText.isNotEmpty()) {
            showErrorDialog(errorText)
        }
    }

    private fun query() {
        val queryText = binding.editTextTitle.text.toString()
        viewModel.search(queryText)
        errorText = ""
    }

    private fun showErrorDialog(text: String) {
        errorDialog = AlertDialog.Builder(requireContext())
            .setMessage(text)
            .setNegativeButton("Cancel", null)
            .show()
    }
}