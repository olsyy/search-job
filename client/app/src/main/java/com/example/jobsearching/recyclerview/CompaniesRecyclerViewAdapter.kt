package com.example.jobsearching.recyclerview

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.domain.entity.CompanyItem
import com.example.jobsearching.R

class CompaniesRecyclerViewAdapter :
    RecyclerView.Adapter<CompaniesRecyclerViewAdapter.ViewHolder>() {

    var companiesList = listOf<CompanyItem>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    var onCompanyItemClickListener: OnCompanyItemClickListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(
            R.layout.item_company,
            parent,
            false
        )
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val company = companiesList[position]
        with(holder) {
            textViewCompanyName.text = company.name
            textViewActivityField.text = modifyActivityField(company.activityField)
            itemView.setOnClickListener {
                onCompanyItemClickListener?.onCompanyItemClick(position)
            }
        }
    }

    private fun modifyActivityField(field: String): String {
        return field.lowercase()
            .replace("_", " ")
            .replaceFirstChar { it.uppercase() }
    }

    override fun getItemCount(): Int = companiesList.size

    interface OnCompanyItemClickListener {

        fun onCompanyItemClick(companyId: Int)
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        val textViewCompanyName: TextView = view.findViewById(R.id.textViewCompanyName)
        val textViewActivityField: TextView = view.findViewById(R.id.textViewActivityField)
    }
}