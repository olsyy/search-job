package com.example.data.db.converter

import androidx.room.TypeConverter
import java.time.LocalDate
import java.time.YearMonth

class Converters {

    @TypeConverter
    fun fromLocalDate(date: LocalDate?): String? {
        return date?.toString()
    }

    @TypeConverter
    fun toLocalDate(dateString: String?): LocalDate? {
        return dateString?.let { LocalDate.parse(it) }
    }

    @TypeConverter
    fun fromYearMonth(date: YearMonth?): String? {
        return date?.toString()
    }

    @TypeConverter
    fun toYearMonth(dateString: String?): YearMonth? {
        return dateString?.let { YearMonth.parse(it) }
    }
}