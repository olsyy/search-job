package com.example.jobsearching.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.domain.entity.VacancyItem
import com.example.domain.repository.Repository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

class VacancyDetailViewModel @Inject constructor(
    private val repository: Repository
) : ViewModel() {

    private val _vacancyItem = MutableLiveData<VacancyItem>()
    val vacancyItem: LiveData<VacancyItem>
        get() = _vacancyItem

    var companyId: Int? = null

    private val _error = MutableLiveData<String>()
    val error: LiveData<String>
        get() = _error

    fun loadVacancyDetail(vacancyId: Int) = CoroutineScope(Dispatchers.IO).launch {
        val data = repository.getVacancyDetail(vacancyId + 1)
        if (data.isFailure) {
            _error.postValue(data.exceptionOrNull()?.message ?: "Something went wrong")
        } else {
            val value = data.getOrNull() ?: VacancyItem("", "", 0, "")
            _vacancyItem.postValue(value)
        }
    }

    fun loadCompanyId(companyName: String) = CoroutineScope(Dispatchers.IO).launch {
        companyId = repository.getCompanyId(companyName).getOrNull()
    }
}