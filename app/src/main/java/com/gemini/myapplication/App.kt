package com.gemini.myapplication

import android.app.Application
import com.gemini.myapplication.di.DI
import com.gemini.myapplication.presentation.presenter.AuthPresenter
import timber.log.Timber
import toothpick.Toothpick
import toothpick.config.Module
import toothpick.configuration.Configuration

class App: Application() {
    override fun onCreate() {
        super.onCreate()
        initToothpick()
        Timber.plant(Timber.DebugTree())
    }

    private fun initToothpick() {
        Toothpick.setConfiguration(Configuration().preventMultipleRootScopes())
        Toothpick.openScope(DI.APP_SCOPE).installModules(object: Module(){
            init {
                bind(AuthPresenter::class.java).singletonInScope()
            }
        })
    }


}