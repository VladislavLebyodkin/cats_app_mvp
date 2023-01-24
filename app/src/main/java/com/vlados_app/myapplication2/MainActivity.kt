package com.vlados_app.myapplication2

import android.os.Bundle
import com.vlados_app.myapplication2.cat_list.presentation.CatListFragment
import com.vlados_app.myapplication2.favourite.presentation.FavouriteFragment
import moxy.MvpActivity

class MainActivity : MvpActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val fragment = FavouriteFragment.newInstance()

        fragmentManager.beginTransaction()
            .add(R.id.fragment_container, fragment)
            .commit()
    }
}