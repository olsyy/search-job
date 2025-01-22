package com.example.jobsearching.utils

import android.app.Application
import com.example.jobsearching.di.AppComponent
import com.example.jobsearching.di.DaggerAppComponent

class App : Application() {

    val appComponent: AppComponent by lazy {
        DaggerAppComponent.factory()
            .create(this)
    }


//    override fun onCreate() {
//        super.onCreate()
////        appComponent = DaggerAppComponent.factory().create(R)
//    }
}