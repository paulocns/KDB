package dev.kdblib.kdbexemple.module

import org.koin.dsl.module

val repositoryModule = module {
    single<dev.kdblib.kdbexemple.data.repository.show.ShowRepository> {
        dev.kdblib.kdbexemple.data.repository.show.ShowRepositoryImpl(get())
    }
}
