package com.ainrom.cocktail

import android.app.Application
import com.ainrom.cocktail.di.AppComponent
import com.ainrom.cocktail.di.DaggerAppComponent

class App: Application() {

    companion object {
        lateinit var instance: App
            private set
        lateinit var component: AppComponent
            private set
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
        component = DaggerAppComponent.builder().application(this).build()
    }
}