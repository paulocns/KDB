package com.psato.kdbexemple.di.module

import com.psato.kdbexemple.data.repository.resource.ResourceRepository
import com.psato.kdbexemple.data.repository.resource.ResourceRepositoryImpl
import com.psato.kdbexemple.presentation.home.HomeFragmentViewModel
import com.psato.kdbexemple.presentation.search.QueryViewModelArc
import org.koin.android.ext.koin.androidApplication
import org.koin.android.viewmodel.ext.koin.viewModel
import org.koin.dsl.module.module

val viewModelModule = module {
    viewModel{
        HomeFragmentViewModel()
    }

    viewModel{
        QueryViewModelArc(get())
    }
}