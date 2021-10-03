package spacekotlin.vaniukova.movies

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import spacekotlin.vaniukova.movies.databinding.ActivityMainBinding
import spacekotlin.vaniukova.movies.favourite_movies.FavouriteMoviesFragment
import spacekotlin.vaniukova.movies.movie_list_search.ListSearchMovieFragment
import spacekotlin.vaniukova.movies.top_movies_list.TopMoviesFragment

class MainActivity : AppCompatActivity(R.layout.activity_main), Navigator {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        Thread.sleep(1000)
        setTheme(R.style.Theme_Movies)
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
            if (currentFragment !is TopMoviesFragment) {
                supportFragmentManager.popBackStack()
            }
        }
    }

    fun setToolbarTitle(title: String) {
        binding.toolbar.title = title
    }

    fun isOnline(context: Context): Boolean {
        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val capabilities =
            connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork)
        if (capabilities != null) {
            when {
                capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> {
                    Log.i("Internet", "NetworkCapabilities.TRANSPORT_CELLULAR")
                    return true
                }
                capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> {
                    Log.i("Internet", "NetworkCapabilities.TRANSPORT_WIFI")
                    return true
                }
                capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> {
                    Log.i("Internet", "NetworkCapabilities.TRANSPORT_ETHERNET")
                    return true
                }
            }
        }
        return false
    }

    fun setToolbarArrowBack(){
        binding.toolbar.setNavigationIcon(R.drawable.ic_arrow_back)
    }

    fun setToolbarStar(){
        binding.toolbar.setNavigationIcon(R.drawable.ic_arrow_back_blue)
    }
}