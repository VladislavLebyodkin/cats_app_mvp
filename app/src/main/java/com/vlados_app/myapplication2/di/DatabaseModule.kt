package com.vlados_app.myapplication2.di

import android.app.Application
import androidx.room.Room
import com.vlados_app.myapplication2.db.CatDao
import com.vlados_app.myapplication2.db.CatDatabase
import dagger.Module
import dagger.Provides

@Module
interface DatabaseModule {

    companion object {
        @AppScope
        @Provides
        fun provideDatabase(application: Application): CatDatabase {
            return Room.databaseBuilder(application, CatDatabase::class.java, "cat_database")
                .fallbackToDestructiveMigration()
                .build()
        }

        @AppScope
        @Provides
        fun provideCatDao(catDatabase: CatDatabase): CatDao = catDatabase.catDao()
    }
}