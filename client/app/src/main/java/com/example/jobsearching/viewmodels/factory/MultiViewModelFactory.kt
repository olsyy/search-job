package com.example.jobsearching.viewmodels.factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import javax.inject.Inject

class MultiViewModelFactory @Inject constructor(
    private val viewModels: @JvmSuppressWildcards Map<String, ViewModel>
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        val viewModel = viewModels[modelClass.simpleName]
            ?: throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.simpleName}")
        return viewModel as T
    }
}