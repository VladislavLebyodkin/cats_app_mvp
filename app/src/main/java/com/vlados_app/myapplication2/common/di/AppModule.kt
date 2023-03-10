package com.vlados_app.myapplication2.common.di

import android.app.Application
import android.app.DownloadManager
import android.content.Context
import android.content.Context.DOWNLOAD_SERVICE
import com.vlados_app.myapplication2.cat_list.data.CatRepositoryImpl
import com.vlados_app.myapplication2.cat_list.domain.CatRepository
import com.vlados_app.myapplication2.common.network.CatService
import com.vlados_app.myapplication2.common.network.createCatService
import com.vlados_app.myapplication2.favourite.data.FavouriteRepositoryImpl
import com.vlados_app.myapplication2.favourite.domain.FavouriteRepository
import dagger.Binds
import dagger.Module
import dagger.Provides

@Module
interface AppModule {

    @AppScope
    @Binds
    fun bindsCatRepository(impl: CatRepositoryImpl): CatRepository

    @AppScope
    @Binds
    fun bindsFavouriteRepository(impl: FavouriteRepositoryImpl): FavouriteRepository

    @AppScope
    @Binds
    fun bindsContext(application: Application): Context

    companion object {
        @AppScope
        @Provides
        fun provideCatService(): CatService {
            return createCatService()
        }

        @AppScope
        @Provides
        fun provideDownloadManager(context: Context): DownloadManager {
            return context.getSystemService(DOWNLOAD_SERVICE) as DownloadManager
        }
    }
}

