package com.psato.kdbexemple.di.module

import com.psato.kdbexemple.data.repository.resource.ResourceRepository
import com.psato.kdbexemple.data.repository.resource.ResourceRepositoryImpl
import org.koin.android.ext.koin.androidApplication
import org.koin.dsl.module.module

val resourceModule = module {
    single<ResourceRepository> {
        ResourceRepositoryImpl(androidApplication())
    }
}
