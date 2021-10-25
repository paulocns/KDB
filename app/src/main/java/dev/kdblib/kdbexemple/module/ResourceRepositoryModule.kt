package dev.kdblib.kdbexemple.module

import org.koin.android.ext.koin.androidApplication
import org.koin.dsl.module


val resourceModule = module {
    single<dev.kdblib.kdbexemple.data.repository.resource.ResourceRepository> {
        dev.kdblib.kdbexemple.data.repository.resource.ResourceRepositoryImpl(androidApplication())
    }
}
