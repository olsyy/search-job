package com.example.data.repository

import android.util.Log
import com.example.data.mapper.toDto
import com.example.data.mapper.toItem
import com.example.data.mapper.toItemList
import com.example.data.sources.LocalDataSource
import com.example.data.sources.RemoteDataSource
import com.example.domain.entity.CompaniesList
import com.example.domain.entity.CompanyItem
import com.example.domain.entity.ResumeItem
import com.example.domain.entity.VacanciesList
import com.example.domain.entity.VacancyItem
import com.example.domain.repository.Repository
import javax.inject.Inject

class RepositoryImpl @Inject constructor(
    private val remoteDataSource: RemoteDataSource,
    private val localDataSource: LocalDataSource
) : Repository {

    override suspend fun getCompaniesList(): Result<CompaniesList> {
        return remoteDataSource.fetchCompaniesList().mapCatching { value ->
            value?.toItemList() ?: throw Exception("Companies list is null")
        }
    }

    override suspend fun getCompanyDetail(companyId: Int): Result<CompanyItem> {
        return remoteDataSource.fetchCompanyDetail(companyId).mapCatching { value ->
            value?.toItem() ?: throw Exception("Company detail is null")
        }
    }

    override suspend fun getVacanciesList(): Result<VacanciesList> {
        return remoteDataSource.fetchVacanciesList().mapCatching { value ->
            value?.toItemList() ?: throw Exception("Vacancies list is null")
        }
    }

    override suspend fun getVacancyDetail(vacancyId: Int): Result<VacancyItem> {
        return remoteDataSource.fetchVacancyDetail(vacancyId).mapCatching { value ->
            value?.toItem() ?: throw Exception("Vacancy detail is null")
        }
    }

    override suspend fun getCompanyName(companyId: Int): Result<String> {
        return remoteDataSource.fetchCompaniesList().mapCatching { value ->
            value?.companies?.getOrNull(companyId)?.name
                ?: throw Exception("Invalid index of company")
        }
    }

    override suspend fun getCompanyId(companyName: String): Result<Int> {
        return remoteDataSource.fetchCompaniesList().mapCatching { value ->
            value?.companies?.indexOfFirst { it.name == companyName }?.takeIf { it >= 0 }
                ?: throw Exception("Company not found")
        }
    }

    override suspend fun getResume(): Result<ResumeItem> {
        return remoteDataSource.fetchResume().mapCatching { value ->
            value?.toItem() ?: throw Exception("Resume not found")
        }
    }

    override suspend fun updateResume(newResume: ResumeItem) : List<String> {
        val resumeDto = newResume.toDto()
        val tags = remoteDataSource.updateResume(resumeDto)
        return tags
    }

    override suspend fun saveResumeLocally(resumeItem: ResumeItem) {
        return localDataSource.insertResume(resumeItem)
    }

    override suspend fun getResumeLocally(): ResumeItem? {
        return localDataSource.loadResume()
    }

    override suspend fun deleteResumeLocally() {
        localDataSource.clearResumeLocally()
    }
}