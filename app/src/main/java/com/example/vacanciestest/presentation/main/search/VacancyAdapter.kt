package com.example.vacanciestest.presentation.main.search

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.vacanciestest.R
import com.example.vacanciestest.databinding.ItemVacancyBinding
import com.example.vacanciestest.domain.models.Vacancy
import com.example.vacanciestest.utils.formatPublishedDate

class VacancyAdapter(val context: Context) : RecyclerView.Adapter<VacancyAdapter.VacancyViewHolder>() {
    private var vacancies : List<Vacancy> = emptyList()

    fun setData(vacancies: List<Vacancy>) {
        this.vacancies = vacancies
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VacancyViewHolder {
        return VacancyViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.item_vacancy, parent, false
            )
        )
    }

    override fun getItemCount() = vacancies.size

    override fun onBindViewHolder(holder: VacancyViewHolder, position: Int) {
        val vacancy = vacancies[position]

        holder.bind(vacancy)
    }

    inner class VacancyViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val binding = ItemVacancyBinding.bind(view)
        private lateinit var vacancy: Vacancy

        fun bind(vacancy: Vacancy) {
            this.vacancy = vacancy

            with(binding) {
                // Сейчас просматривает
                val lookingNow = vacancy.lookingNumber ?: 0
                if (lookingNow == 0) {
                    lookingNowTextView.visibility = View.GONE
                } else {
                    lookingNowTextView.visibility = View.VISIBLE
                    lookingNowTextView.text = context.resources.getQuantityString(
                        R.plurals.looking_now_persons,
                        lookingNow,
                        lookingNow
                    )
                }

                // Название
                titleTextView.text = vacancy.title
                salaryTextView.text = vacancy.salary?.full
                cityTextView.text = vacancy.address?.town
                companyTextView.text = vacancy.company
                experienceTextView.text = vacancy.experience?.previewText

                val formattedDate = formatPublishedDate(vacancy.publishedDate)
                publishedTextView.text = context.resources.getString(R.string.published, formattedDate)
            }
        }
    }
}