package com.psato.kdbexemple.infrastructure

import android.app.Application
import com.psato.kdbexemple.di.module.*
import org.koin.android.ext.android.startKoin

/**
 * Created by psato on 29/10/16.
 */

class CustomApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        initializeInjector()
    }

    private fun initializeInjector() {
        startKoin(this, listOf(appModule, networkModule, resourceModule, repositoryModule, viewModelModule, useCaseModule))
    }
}
