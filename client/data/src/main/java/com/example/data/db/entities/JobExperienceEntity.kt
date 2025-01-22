package com.example.data.db.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.example.data.db.converter.Converters
import java.time.YearMonth

@Entity(
    tableName = "job_experience",
//    foreignKeys = [ForeignKey(
//        entity = CandidateInfoEntity::class,
//        parentColumns = ["resumeId"],
//        childColumns = ["candidate_id"],
//        onDelete = ForeignKey.CASCADE
//    )]
)
@TypeConverters(Converters::class)
data class JobExperienceEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "experienceId") val id: Int = 0,
    @ColumnInfo(name = "start_date") val startDate: YearMonth,
    @ColumnInfo(name = "endDate")  val endDate: YearMonth,
    @ColumnInfo(name = "company_name") val companyName: String,
    val description: String,
//    @ColumnInfo(name = "candidate_id") val candidateId: Int,
    )