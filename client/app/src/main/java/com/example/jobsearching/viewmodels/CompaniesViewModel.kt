package com.example.jobsearching.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.domain.entity.CompaniesList
import com.example.domain.repository.Repository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

class CompaniesViewModel @Inject constructor(
    private val repository: Repository
) : ViewModel() {

    private val _companiesList = MutableLiveData<CompaniesList>()
    val companiesList: LiveData<CompaniesList>
        get() = _companiesList

    private val _error = MutableLiveData<String>()
    val error: LiveData<String>
        get() = _error

    fun loadCompaniesList() = CoroutineScope(Dispatchers.IO).launch {
        val data = repository.getCompaniesList()
        if (data.isFailure) {
            _error.postValue(data.exceptionOrNull()?.message ?: "Something went wrong")
        } else {
            val value = data.getOrNull() ?: CompaniesList(emptyList())
            _companiesList.postValue(value)
        }
    }
}