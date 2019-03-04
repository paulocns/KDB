package com.psato.kdbexemple.di.module

import com.psato.kdbexemple.data.repository.resource.ResourceRepository
import com.psato.kdbexemple.data.repository.resource.ResourceRepositoryImpl
import com.psato.kdbexemple.interactor.usecase.show.SearchShows
import org.koin.android.ext.koin.androidApplication
import org.koin.dsl.module.module

val useCaseModule = module {
    factory {
        SearchShows(get())
    }
}