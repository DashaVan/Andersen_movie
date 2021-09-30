package spacekotlin.vaniukova.movies

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import spacekotlin.vaniukova.movies.Navigator
import spacekotlin.vaniukova.movies.R
import spacekotlin.vaniukova.movies.databinding.ActivityMainBinding
import spacekotlin.vaniukova.movies.favourite_movies.FavouriteMoviesFragment
import spacekotlin.vaniukova.movies.movie_list_search.ListSearchMovieFragment
import spacekotlin.vaniukova.movies.top_movies_list.TopMoviesFragment

class MainActivity : AppCompatActivity(R.layout.activity_main), Navigator {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initToolbar()

        val alreadyHasFragment = supportFragmentManager.findFragmentById(R.id.container) != null
        if (!alreadyHasFragment) {
            supportFragmentManager.beginTransaction()
                .add(R.id.container, TopMoviesFragment())
                .commit()
        }
    }

    override fun navigateTo(fragment: Fragment, name: String) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.container, fragment)
            .addToBackStack(name)
            .commit()
    }

    private fun initToolbar() {
        binding.toolbar.setOnMenuItemClickListener { menuItem ->
            when (menuItem.itemId) {
                R.id.action_top -> {
                    navigateTo(TopMoviesFragment(), "topMoviesFragment")
                    true
                }
                R.id.action_favourites -> {
                    navigateTo(FavouriteMoviesFragment(), "favouriteMoviesFragment")
                    true
                }
                R.id.action_search -> {
                    navigateTo(ListSearchMovieFragment(), "listSearchMovieFragment")
                    true
                }
                else -> false
            }
        }

        binding.toolbar.setNavigationOnClickListener {
            val currentFragment = supportFragmentManager.findFragmentById(R.id.container)
            if (currentFragment is TopMoviesFragment) {
                Toast.makeText(applicationContext, R.string.click_again, Toast.LENGTH_SHORT).show()
                binding.toolbar.setNavigationOnClickListener {
                    finish()
                }
            } else {
                supportFragmentManager.popBackStack()
            }
        }
    }

    fun setToolbarTitle(title:String){
        binding.toolbar.title = title
    }
}