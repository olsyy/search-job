package com.example.data.db.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.example.data.db.converter.Converters
import java.time.LocalDate

@Entity(tableName = "candidate_info")
@TypeConverters(Converters::class)
data class CandidateInfoEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "resumeId")
    val id: Int = 0,
    @ColumnInfo(name = "candidate_name") val candidateName: String,
    val profession: String,
    val gender: String,
    @ColumnInfo(name = "birth_date") val birthDate: LocalDate,
    val phone: String,
    val email: String,
    val relocation: Boolean,
    val freeForm: String,
)