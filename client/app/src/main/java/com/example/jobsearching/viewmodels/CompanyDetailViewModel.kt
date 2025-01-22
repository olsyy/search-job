package com.example.jobsearching.viewmodels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.domain.entity.CompanyItem
import com.example.domain.repository.Repository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

class CompanyDetailViewModel @Inject constructor(
    private val repository: Repository
) : ViewModel() {

    private val _companyItem = MutableLiveData<CompanyItem>()
    val companyItem: LiveData<CompanyItem>
        get() = _companyItem

    private val _error = MutableLiveData<String>()
    val error: LiveData<String>
        get() = _error

    fun loadCompanyDetail(companyId: Int) = CoroutineScope(Dispatchers.IO).launch {
        val data = repository.getCompanyDetail(companyId + 1)
        if (data.isFailure) {
            _error.postValue(data.exceptionOrNull()?.message ?: "Something went wrong")
        } else {
            val value = data.getOrNull() ?: CompanyItem("", "")
            _companyItem.postValue(value)
        }
    }
}