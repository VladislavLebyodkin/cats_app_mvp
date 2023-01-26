package com.vlados_app.myapplication2

import android.app.Application
import com.vlados_app.myapplication2.di.AppComponent
import com.vlados_app.myapplication2.di.DaggerAppComponent
import com.vlados_app.myapplication2.di.DatabaseModule

class CatApp : Application() {

    companion object {
        lateinit var appComponent: AppComponent
    }

    override fun onCreate() {
        appComponent = DaggerAppComponent.builder()
            .application(this)
            .build()

        super.onCreate()
    }
}