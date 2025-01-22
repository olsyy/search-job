package com.example.jobsearching.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.domain.entity.VacanciesList
import com.example.domain.entity.VacancyItem
import com.example.domain.repository.Repository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

class VacanciesViewModel @Inject constructor(
    private val repository: Repository
) : ViewModel() {

    private var vacancies = listOf<VacancyItem>()

    private val _vacanciesList = MutableLiveData<VacanciesList>()
    val vacanciesList: LiveData<VacanciesList>
        get() = _vacanciesList

    private val _error = MutableLiveData<String>()
    val error: LiveData<String>
        get() = _error

    fun loadVacanciesList(filterById: Int? = null) = CoroutineScope(Dispatchers.IO).launch {
        val data = repository.getVacanciesList()
        if (data.isFailure) {
            _error.postValue(data.exceptionOrNull()?.message ?: "Something went wrong")
        } else {
            vacancies = data.getOrNull()?.list ?: emptyList()
            if (filterById == null) {
                _vacanciesList.postValue(VacanciesList(vacancies))
            } else {
                val name = repository.getCompanyName(filterById).getOrNull()
                val filteredList = vacancies.filter { it.companyName == name }
                _vacanciesList.postValue(VacanciesList(filteredList))
            }
        }
    }

    fun getVacancyId(vacancy: VacancyItem): Int = vacancies.indexOf(vacancy)
}
