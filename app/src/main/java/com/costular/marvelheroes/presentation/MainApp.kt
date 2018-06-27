package com.costular.marvelheroes.presentation

import android.app.Application
import com.costular.marvelheroes.di.components.ApplicationComponent
import com.costular.marvelheroes.di.components.DaggerApplicationComponent
import com.costular.marvelheroes.di.modules.ApplicationModule
import com.facebook.stetho.Stetho

class MainApp : Application() {

    val component: ApplicationComponent by lazy {
        DaggerApplicationComponent.builder()
                .applicationModule(ApplicationModule(this))
                .build()
    }

    // TODO: remove onCreate if it only runs stetho.
    override fun onCreate() {
        super.onCreate()

        Stetho.initializeWithDefaults(this)
    }

}