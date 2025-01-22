package com.example.jobsearching.recyclerview

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.domain.entity.VacancyItem
import com.example.jobsearching.R

class VacanciesRecyclerViewAdapter :
    RecyclerView.Adapter<VacanciesRecyclerViewAdapter.ViewHolder>() {

    var vacanciesList = listOf<VacancyItem>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    var onVacancyItemClickListener: OnVacancyItemClickListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(
            R.layout.item_vacancy,
            parent,
            false
        )
        return ViewHolder(view)
    }

    override fun getItemCount(): Int = vacanciesList.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val vacancy = vacanciesList[position]
        with(holder) {
            textViewVacancyTitle.text = vacancy.profession
            textViewCompanyName.text = vacancy.companyName
            textViewSalary.text = vacancy.salary.toString()
            textViewLevel.text = vacancy.level

            itemView.setOnClickListener {
                onVacancyItemClickListener?.onVacancyItemClick(vacancy)
            }
        }
    }

    interface OnVacancyItemClickListener {

        fun onVacancyItemClick(vacancy: VacancyItem)
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        val textViewVacancyTitle: TextView = view.findViewById(R.id.textViewVacancyTitle)
        val textViewCompanyName: TextView = view.findViewById(R.id.textViewCompanyName)
        val textViewSalary: TextView = view.findViewById(R.id.textViewSalaryLevel)
        val textViewLevel: TextView = view.findViewById(R.id.textViewCandidateLevel)
    }
}