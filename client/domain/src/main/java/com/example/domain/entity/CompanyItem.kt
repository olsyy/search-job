package com.example.domain.entity

import android.os.Parcel
import android.os.Parcelable

data class CompanyItem(
    val name: String,
    val activityField: String,
//    val vacancies: List<VacancyItem>,
//    val contacts: String,
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString()!!,
        parcel.readString()!!
    ) {
    }

    override fun describeContents() = 0

    override fun writeToParcel(p0: Parcel, p1: Int) {
        p0.writeString(name)
        p0.writeString(activityField)
    }

    companion object CREATOR : Parcelable.Creator<CompanyItem> {
        override fun createFromParcel(parcel: Parcel): CompanyItem {
            return CompanyItem(parcel)
        }

        override fun newArray(size: Int): Array<CompanyItem?> {
            return arrayOfNulls(size)
        }
    }
}