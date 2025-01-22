package com.example.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.data.db.dao.ResumeDao
import com.example.data.db.entities.CandidateInfoEntity
import com.example.data.db.entities.EducationEntity
import com.example.data.db.entities.JobExperienceEntity

private const val DB_VERSION = 1
@Database(
    entities = [
        CandidateInfoEntity::class,
        JobExperienceEntity::class,
        EducationEntity::class
    ],
    version = DB_VERSION,
    exportSchema = false
)
abstract class ResumeDatabase : RoomDatabase() {
    abstract fun resumeDao(): ResumeDao
}