package spacekotlin.vaniukova.movies

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import spacekotlin.vaniukova.movies.databinding.FragmentListBinding
import spacekotlin.vaniukova.movies.movie_list.MovieListAdapter
import spacekotlin.vaniukova.movies.movie_list.MovieListViewModel
import spacekotlin.vaniukova.movies.utils.autoCleared

class ListFragment: Fragment(R.layout.fragment_list) {
    private var _binding: FragmentListBinding? = null
    private val binding get() = _binding!!

    private var movieAdapter: MovieListAdapter by autoCleared()
    private val viewModel: MovieListViewModel by viewModels()

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

    private fun searchMovie() {

    }

    private fun bindViewModel() {
        binding.btnSearch.setOnClickListener {
            query()
        }

        viewModel.isLoading.observe(viewLifecycleOwner, ::updateLoadingState)
        viewModel.movieList.observe(viewLifecycleOwner) { movieAdapter.items = it }
    }

    private fun updateLoadingState(isLoading: Boolean) {
        binding.recyclerViewMovies.isVisible = isLoading.not()
        binding.progressBar.isVisible = isLoading
        binding.btnSearch.isEnabled = isLoading.not()
        /*if (errorText.isNotEmpty()) {
            showErrorMessage()
        } else {
            binding.btnRepeat.isVisible = false
            binding.textViewError.isVisible = false
        }*/
    }

    private fun query() {
        val queryText = binding.editTextTitle.text.toString()
        viewModel.search(queryText)
        /*if (binding.recyclerViewMovies.isEmpty()) {
            errorText = "фильм не найден"
                //TODO dialog film not found
        }*/
    }
}