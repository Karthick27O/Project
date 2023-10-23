package com.example.databinding.koin

import android.content.Context
import com.example.databinding.PrefHelper
import com.example.databinding.room_db.AppDataBase
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val appModule = module {
    single { PrefHelper(androidContext()) }
    single { AppDataBase }
}