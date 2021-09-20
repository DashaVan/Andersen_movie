package spacekotlin.vaniukova.movies

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import spacekotlin.vaniukova.movies.databinding.FragmentListBinding
import spacekotlin.vaniukova.movies.movie_list.MovieList
import spacekotlin.vaniukova.movies.movie_list.MovieListAdapter

class ListFragment: Fragment(R.layout.fragment_list) {
    private var _binding: FragmentListBinding? = null
    private val binding get() = _binding!!

    private var movies = MovieList.list
    private var movieAdapter: MovieListAdapter? = null

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
        movieAdapter = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initList()
        movieAdapter?.updateMovie(movies)
    }

    private fun initList() {
        movieAdapter = MovieListAdapter { id -> openDetailFragment(id) }
        with(binding.recyclerViewMovies) {
            adapter = movieAdapter
            layoutManager = LinearLayoutManager(requireContext())
            setHasFixedSize(true)
        }
    }

    private fun openDetailFragment(id: Long){

    }
}