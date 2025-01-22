package com.example.data.sources

import android.util.Log
import com.example.data.network.Api
import com.example.data.network.dto.CompaniesListDto
import com.example.data.network.dto.CompanyDto
import com.example.data.network.dto.ResumeDto
import com.example.data.network.dto.VacanciesListDto
import com.example.data.network.dto.VacancyDto
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Response
import javax.inject.Inject

class RemoteDataSource @Inject constructor(
    private val api: Api
) {

    private suspend fun <T> fetchData(apiCall: suspend () -> Response<T>): Result<T?> {
        return withContext(Dispatchers.IO) {
            try {
                val response = apiCall()
                if (response.isSuccessful) {
                    Result.success(response.body())
                } else {
                    val errorMessage = response.errorBody()?.string()
                    Result.failure((Exception("$errorMessage. Error code: {${response.code()}")))
                }
            } catch (e: Exception) {
                Result.failure(e)
            }
        }
    }

    suspend fun fetchCompaniesList(): Result<CompaniesListDto?> {
        return fetchData { api.fetchCompaniesList() }
    }

    suspend fun fetchCompanyDetail(companyId: Int): Result<CompanyDto?> {
        return fetchData { api.fetchCompanyDetails(companyId) }
    }

    suspend fun fetchVacanciesList(): Result<VacanciesListDto?> {
        return fetchData { api.fetchVacanciesList() }
    }

    suspend fun fetchVacancyDetail(vacancyId: Int): Result<VacancyDto?> {
        return fetchData { api.fetchVacancyDetails(vacancyId) }
    }

    suspend fun fetchResume(): Result<ResumeDto?> {
        return fetchData {
            api.fetchResume()
        }
    }

    suspend fun updateResume(newResume: ResumeDto) : List<String> {
        return api.updateResume(newResume)
    }
}