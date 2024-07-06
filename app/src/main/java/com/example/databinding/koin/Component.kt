package com.example.databinding.koin

import com.example.databinding.PrefHelper
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject


class Component: KoinComponent {
     val prefHelper: PrefHelper by inject()
}