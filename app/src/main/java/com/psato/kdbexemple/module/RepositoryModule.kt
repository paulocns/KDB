package com.psato.kdbexemple.di.module

import com.psato.kdbexemple.data.repository.show.ShowRepository
import com.psato.kdbexemple.data.repository.show.ShowRepositoryImpl
import org.koin.dsl.module.module

val repositoryModule = module {
    single<ShowRepository> {
        ShowRepositoryImpl(get())
    }
}
