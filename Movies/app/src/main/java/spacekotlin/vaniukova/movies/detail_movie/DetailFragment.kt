package spacekotlin.vaniukova.movies.detail_movie

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.bumptech.glide.Glide
import spacekotlin.vaniukova.movies.R
import spacekotlin.vaniukova.movies.databinding.FragmentDetailBinding
import spacekotlin.vaniukova.movies.movie_list.Movie
import spacekotlin.vaniukova.movies.withArguments

class DetailFragment : Fragment(R.layout.fragment_detail) {

    private var _binding: FragmentDetailBinding? = null
    private val binding get() = _binding!!

    private val detailFragmentViewModel: DetailFragmentViewModel by viewModels()
    private var dMovie: Movie? = null

    companion object {
        private const val KEY = "key"
        fun newInstance(id: String): DetailFragment {
            return DetailFragment().withArguments {
                putString(KEY, id)
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val idString = requireArguments().getString(KEY)
        bindViewModel()
        queryById(idString!!)
    }

    private fun bindViewModel() {
        with(detailFragmentViewModel) {
            isLoading.observe(viewLifecycleOwner, ::updateLoadingState)
            detailMovie.observe(viewLifecycleOwner) { dMovie = it }
        }
    }

    private fun updateLoadingState(isLoading: Boolean) {
        with(binding) {
            ivDetailPoster.isVisible = isLoading.not()
            progressBar.isVisible = isLoading
        }
        if (!isLoading) {
            dMovie?.let { addInfo(it) }
        }
    }

    private fun queryById(id: String) {
        detailFragmentViewModel.requestMovieById(id)
    }

    private fun addInfo(movie: Movie) {
        with(binding) {
            tvDetailTitle.text = movie.title
            tvDetailType.text = movie.type
            tvDetailYear.text = movie.year
            tvPlot.text = movie.plot
        }

        Glide.with(this)
            .load(movie.poster)
            .placeholder(R.drawable.noposter)
            .into(binding.ivDetailPoster)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}