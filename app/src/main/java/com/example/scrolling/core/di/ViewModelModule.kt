package com.example.scrolling.core.di

import com.example.scrolling.viewModel.ImageViewModel
import org.koin.dsl.module


val ViewModelModule = module {
    single {
        ImageViewModel()
    }


}