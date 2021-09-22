package spacekotlin.vaniukova.movies

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import spacekotlin.vaniukova.movies.movie_list.ListFragment

class MainActivity : AppCompatActivity(), Navigator {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val alreadyHasFragment = supportFragmentManager.findFragmentById(R.id.container) != null

        if (!alreadyHasFragment) {
            supportFragmentManager.beginTransaction()
                .add(R.id.container, ListFragment())
                //.addToBackStack("listFragment")
                .commit()
        }


        //navigateTo(ListFragment(), "listFragment")
    }

    override fun navigateTo(fragment: Fragment, name: String) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.container, fragment)
            .addToBackStack(name)
            .commit()
    }
}