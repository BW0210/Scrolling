package com.example.scrolling.core

import androidx.multidex.MultiDexApplication
import com.example.scrolling.core.di.ViewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.loadKoinModules
import org.koin.core.context.startKoin
import org.koin.core.logger.Level
import timber.log.Timber

class MainApplication : MultiDexApplication() {

    override fun onCreate() {
        super.onCreate()

        Timber.plant(Timber.DebugTree())
        Timber.tag("amir").e("APP Started")


        startKoin {
            androidLogger(Level.ERROR)
            androidContext(this@MainApplication)
            loadKoinModules(listOf(
                ViewModelModule
            ))
            koin.createRootScope()
        }
    }

}