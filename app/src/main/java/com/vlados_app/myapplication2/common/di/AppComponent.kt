package com.vlados_app.myapplication2.common.di

import android.app.Application
import com.vlados_app.myapplication2.cat_list.presentation.CatListFragment
import com.vlados_app.myapplication2.favourite.presentation.FavouriteFragment
import dagger.BindsInstance
import dagger.Component

@AppScope
@Component(modules = [AppModule::class])
interface AppComponent {
    fun inject(catListFragment: CatListFragment)
    fun inject(favouriteFragment: FavouriteFragment)

    @Component.Builder
    interface Builder {

        @BindsInstance
        fun application(application: Application): Builder

        fun build(): AppComponent
    }
}