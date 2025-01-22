package com.example.data.db.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.example.data.db.converter.Converters

@Entity(
    tableName = "education",
//    foreignKeys = [ForeignKey(
//        CandidateInfoEntity::class,
//        parentColumns = ["resumeId"],
//        childColumns = ["candidate_id"],
//        onDelete = ForeignKey.CASCADE
//    )]
)
@TypeConverters(Converters::class)
data class EducationEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "educationId")
    val id: Int = 0,
    @ColumnInfo(name = "education_type") val educationType: String,
    @ColumnInfo(name = "start_year") val startYear: Int,
    @ColumnInfo(name = "end_year") val endYear: Int,
    val description: String,
//    @ColumnInfo(name = "candidate_id") val candidateId: Int,
)
