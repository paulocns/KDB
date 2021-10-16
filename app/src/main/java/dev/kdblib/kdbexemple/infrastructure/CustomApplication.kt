package dev.kdblib.kdbexemple.infrastructure

import android.app.Application
import dev.kdblib.kdbexemple.module.*
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

/**
 * Created by psato on 29/10/16.
 */

class CustomApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        initializeInjector()
    }

    private fun initializeInjector() {
        startKoin{
            androidContext(this@CustomApplication)
            modules(listOf(appModule, networkModule, resourceModule, repositoryModule, viewModelModule, useCaseModule))
        }
    }
}
