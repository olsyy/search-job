package com.example.data.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.TypeConverters
import com.example.data.db.converter.Converters
import com.example.data.db.entities.CandidateInfoEntity
import com.example.data.db.entities.EducationEntity
import com.example.data.db.entities.JobExperienceEntity

@Dao
@TypeConverters(Converters::class)
interface ResumeDao {
    @Insert(
        entity = CandidateInfoEntity::class,
        onConflict = OnConflictStrategy.REPLACE
    )
    suspend fun insertCandidateInfo(candidateInfo: CandidateInfoEntity): Long

    @Insert(
        entity = EducationEntity::class,
        onConflict = OnConflictStrategy.REPLACE
    )
    suspend fun insertEducation(vararg education: EducationEntity)

    @Insert(
        entity = JobExperienceEntity::class,
        onConflict = OnConflictStrategy.REPLACE
    )
    suspend fun insertJobExperience(vararg jobExperience: JobExperienceEntity)

    @Query("SELECT * FROM candidate_info")
    suspend fun getCandidateInfo(): List<CandidateInfoEntity>

    @Query("SELECT * FROM education")
    suspend fun getEducationList(): List<EducationEntity>

    @Query("SELECT * FROM job_experience")
    suspend fun getJobExperienceList(): List<JobExperienceEntity>

    @Query("DELETE FROM candidate_info")
    suspend fun clearCandidateInfo()

    @Query("DELETE FROM education")
    suspend fun clearEducation()

    @Query("DELETE FROM job_experience")
    suspend fun clearJobExperience()

    @Transaction
    suspend fun clearAllTables() {
        clearCandidateInfo()
        clearEducation()
        clearJobExperience()
    }
}