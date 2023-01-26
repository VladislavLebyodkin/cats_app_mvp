package com.vlados_app.myapplication2

import android.os.Bundle
import androidx.appcompat.widget.Toolbar
import com.vlados_app.myapplication2.cat_list.presentation.CatListFragment
import com.vlados_app.myapplication2.favourite.presentation.FavouriteFragment
import moxy.MvpActivity


class MainActivity : MvpActivity() {

    companion object {
        private const val CAT_LIST_TAG = "cat_list"
        private const val FAVOURITE_TAG = "favourite"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val catListFragment = CatListFragment.newInstance()
        val favouriteFragment = FavouriteFragment.newInstance()

        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        toolbar.inflateMenu(R.menu.main_nav_menu)
        toolbar.setOnMenuItemClickListener { item ->
            if (item.itemId == R.id.cat_list) {
                fragmentManager.beginTransaction()
                    .hide(favouriteFragment)
                    .show(catListFragment)
                    .commit()
                toolbar.menu.findItem(R.id.cat_list).isVisible = false
                toolbar.menu.findItem(R.id.favourite).isVisible = true
            } else if (item.itemId == R.id.favourite) {
                fragmentManager.beginTransaction()
                    .hide(catListFragment)
                    .show(favouriteFragment)
                    .commit()
                toolbar.menu.findItem(R.id.cat_list).isVisible = true
                toolbar.menu.findItem(R.id.favourite).isVisible = false
            }
            false
        }

        if (fragmentManager.findFragmentByTag(CAT_LIST_TAG) == null) {
            fragmentManager.beginTransaction()
                .add(R.id.fragment_container, catListFragment, CAT_LIST_TAG)
                .add(R.id.fragment_container, favouriteFragment, FAVOURITE_TAG)
                .hide(favouriteFragment)
                .show(catListFragment)
                .commit()

            toolbar.menu.findItem(R.id.cat_list).isVisible = false
            toolbar.menu.findItem(R.id.favourite).isVisible = true
        }
    }
}