package com.psato.kdbexemple.di.module

import com.psato.kdbexemple.data.remote.TraktAPI
import org.koin.dsl.module.module
import retrofit2.Retrofit


val networkModule = module {
    single<TraktAPI> {
        val retrofit: Retrofit = get()
        retrofit.create(TraktAPI::class.java)
    }
}