package com.example.databinding.koin

import com.example.databinding.PrefHelper
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val appModule = module {
    single { PrefHelper(androidContext()) }
}