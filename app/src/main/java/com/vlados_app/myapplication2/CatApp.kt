package com.vlados_app.myapplication2

import android.app.Application
import com.vlados_app.myapplication2.di.AppComponent
import com.vlados_app.myapplication2.di.DaggerAppComponent

class CatApp : Application() {

    companion object {
        lateinit var appComponent: AppComponent
    }

    override fun onCreate() {
        appComponent = DaggerAppComponent.builder()
            .application(this)
            .apiKey(BuildConfig.CATS_API_KEY)
            .baseUrl(BuildConfig.CATS_BASE_URL)
            .build()

        super.onCreate()
    }
}