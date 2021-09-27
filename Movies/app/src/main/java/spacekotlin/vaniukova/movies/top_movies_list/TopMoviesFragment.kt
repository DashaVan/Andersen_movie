package spacekotlin.vaniukova.movies.top_movies_list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import spacekotlin.vaniukova.movies.Navigator
import spacekotlin.vaniukova.movies.R
import spacekotlin.vaniukova.movies.databinding.FragmentTopMoviesBinding
import spacekotlin.vaniukova.movies.detail_movie.DetailFragment
import spacekotlin.vaniukova.movies.movie_list.MovieListAdapter
import spacekotlin.vaniukova.movies.utils.autoCleared

class TopMoviesFragment : Fragment(R.layout.fragment_top_movies) {
    private var _binding: FragmentTopMoviesBinding? = null
    private val binding get() = _binding!!

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

        initList()
        bindViewModel()
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
        (activity as Navigator).navigateTo(DetailFragment.newInstance(id), "detailFragment")
    }

    private fun bindViewModel() {
        topMoviesViewModel.topMovies.observe(viewLifecycleOwner) { topMoviesAdapter.items = it }
        topMoviesViewModel.requestMovies()
    }
}