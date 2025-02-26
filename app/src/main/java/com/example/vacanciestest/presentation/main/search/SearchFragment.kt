package com.example.vacanciestest.presentation.main.search

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.vacanciestest.R
import com.example.vacanciestest.databinding.FragmentSearchBinding
import com.example.vacanciestest.domain.models.Vacancy

class SearchFragment : Fragment() {
    private var _binding: FragmentSearchBinding? = null
    private val binding get() = _binding!!

    private lateinit var viewModel: SearchViewModel
    private lateinit var vacanciesAdapter: VacancyAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSearchBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(this@SearchFragment)[SearchViewModel::class.java]

        vacanciesAdapter = VacancyAdapter(requireContext())
        binding.recyclerView.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = vacanciesAdapter
        }

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
        with(binding) {
            errorLayout.visibility = View.INVISIBLE
            loadingLayout.visibility = View.VISIBLE
            dataLayout.visibility = View.INVISIBLE
        }
    }

    private fun showData(vacancies: List<Vacancy>) {
        with(binding) {
            errorLayout.visibility = View.INVISIBLE
            loadingLayout.visibility = View.INVISIBLE
            dataLayout.visibility = View.VISIBLE
        }

        vacanciesAdapter.setData(vacancies)
    }

    private fun showError() {
        with(binding) {
            errorLayout.visibility = View.VISIBLE
            loadingLayout.visibility = View.INVISIBLE
            dataLayout.visibility = View.INVISIBLE

            retryButton.setOnClickListener {
                showLoading()
                viewModel.loadVacancies()
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}