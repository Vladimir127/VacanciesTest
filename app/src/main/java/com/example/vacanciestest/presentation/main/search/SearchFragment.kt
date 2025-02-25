package com.example.vacanciestest.presentation.main.search

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.example.vacanciestest.R
import com.example.vacanciestest.domain.models.Vacancy

class SearchFragment : Fragment() {
    private lateinit var viewModel: SearchViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_search, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(this@SearchFragment)[SearchViewModel::class.java]

        viewModel.vacancies.observe(viewLifecycleOwner) { vacancies ->
            showData(vacancies)
        }
        viewModel.error.observe(viewLifecycleOwner) {
            showError()
        }

        showLoading()
        viewModel.loadVacancies()
    }

    private fun showLoading() {
        Toast.makeText(requireContext(), "Loading", Toast.LENGTH_SHORT).show()
    }

    private fun showData(vacancies: List<Vacancy>) {
        val count = vacancies.count()
        Toast.makeText(requireContext(), "Count of vacancies: $count", Toast.LENGTH_SHORT).show()
    }

    private fun showError() {
        Toast.makeText(requireContext(), "Error", Toast.LENGTH_SHORT).show()
    }
}