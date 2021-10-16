package dev.kdblib.kdbexemple.module

import org.koin.dsl.module
import retrofit2.Retrofit


val networkModule = module {
    single<dev.kdblib.kdbexemple.data.remote.TraktAPI> {
        val retrofit: Retrofit = get()
        retrofit.create(dev.kdblib.kdbexemple.data.remote.TraktAPI::class.java)
    }
}