package dev.kdblib.kdbexemple.module

import dev.kdblib.kdbexemple.presentation.home.HomeFragmentViewModel
import dev.kdblib.kdbexemple.presentation.search.QueryViewModelArc
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module


val viewModelModule = module {
    viewModel {
        HomeFragmentViewModel()
    }

    viewModel {
        QueryViewModelArc(get())
    }
}