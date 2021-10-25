package dev.kdblib.kdbexemple.module

import dev.kdblib.kdbexemple.usecase.show.SearchShows
import org.koin.dsl.module

val useCaseModule = module {
    factory {
        SearchShows(get())
    }
}