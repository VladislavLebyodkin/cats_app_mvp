package com.vlados_app.myapplication2

import android.app.Application
import com.vlados_app.myapplication2.common.di.AppComponent
import com.vlados_app.myapplication2.common.di.DaggerAppComponent

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