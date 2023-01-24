package com.vlados_app.myapplication2.di

import android.app.Application
import com.vlados_app.myapplication2.cat_list.data.CatRepositoryImpl
import com.vlados_app.myapplication2.cat_list.domain.CatRepository
import com.vlados_app.myapplication2.cat_list.presentation.CatListFragment
import com.vlados_app.myapplication2.favourite.data.FavouriteRepositoryImpl
import com.vlados_app.myapplication2.favourite.domain.FavouriteRepository
import com.vlados_app.myapplication2.favourite.presentation.FavouriteFragment
import com.vlados_app.myapplication2.network.CatService
import dagger.*
import javax.inject.Qualifier
import javax.inject.Scope

@AppScope
@Component(modules = [AppModule::class])
interface AppComponent {

    fun inject(catListFragment: CatListFragment)
    fun inject(favouriteFragment: FavouriteFragment)

    @Component.Builder
    interface Builder {

        @BindsInstance
        fun application(application: Application): Builder

        @BindsInstance
        fun apiKey(@ApiKeyQualifier apiKey: String): Builder

        @BindsInstance
        fun baseUrl(@BaseUrlQualifier baseUrl: String): Builder

        fun build(): AppComponent
    }
}

@Module(includes = [AppModule.Bindings::class])
class AppModule {

    @AppScope
    @Provides
    fun provideCatService(
        @ApiKeyQualifier apiKey: String,
        @BaseUrlQualifier baseUrl: String
    ): CatService {
        return CatService(apiKey, baseUrl)
    }

    @Module
    interface Bindings {

        @AppScope
        @Binds
        fun bindsCatRepository(impl: CatRepositoryImpl): CatRepository

        @AppScope
        @Binds
        fun bindsFavouriteRepository(impl: FavouriteRepositoryImpl): FavouriteRepository
    }
}

@Scope
annotation class AppScope

@Qualifier
annotation class ApiKeyQualifier

@Qualifier
annotation class BaseUrlQualifier