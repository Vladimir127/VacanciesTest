package com.example.vacanciestest.presentation.main.more

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.vacanciestest.R
import com.example.vacanciestest.databinding.FragmentMoreVacanciesBinding
import com.example.vacanciestest.domain.models.Vacancy
import com.example.vacanciestest.presentation.main.favorites.FavoritesSharedViewModel
import com.example.vacanciestest.presentation.main.search.VacancyAdapter

class MoreVacanciesFragment : Fragment() {
    private var _binding: FragmentMoreVacanciesBinding? = null
    private val binding get() = _binding!!

    private lateinit var moreVacanciesViewModel: MoreVacanciesViewModel
    private lateinit var favoritesSharedViewModel: FavoritesSharedViewModel
    private lateinit var vacanciesAdapter: VacancyAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMoreVacanciesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val viewModelProvider = ViewModelProvider(
            requireActivity(),
            ViewModelProvider.AndroidViewModelFactory.getInstance(requireActivity().application)        )
        favoritesSharedViewModel = viewModelProvider[FavoritesSharedViewModel::class.java]

        moreVacanciesViewModel = ViewModelProvider(this@MoreVacanciesFragment)[MoreVacanciesViewModel::class.java]

        vacanciesAdapter = VacancyAdapter(requireContext())
        binding.recyclerView.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = vacanciesAdapter
        }

        vacanciesAdapter.favoriteItemClickListener =
            object : VacancyAdapter.FavoriteItemClickListener {
                override fun onToggleFavorite(vacancyId: String) {
                    favoritesSharedViewModel.toggleFavorite(vacancyId)
                }
            }

        vacanciesAdapter.onItemClickListener = { link ->
            findNavController().navigate(R.id.action_moreVacanciesFragment_to_vacancyFragment)
        }

        moreVacanciesViewModel.vacancies.observe(viewLifecycleOwner) { vacancies ->
            showData(vacancies)
        }
        moreVacanciesViewModel.error.observe(viewLifecycleOwner) {
            showError()
        }

        binding.searchLayout.setStartIconOnClickListener {
            findNavController().popBackStack()
        }

        showLoading()
        moreVacanciesViewModel.loadVacancies()
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

            val count = vacancies.size
            countTextView.text = resources.getQuantityString(R.plurals.vacancies_count, count, count)
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
                moreVacanciesViewModel.loadVacancies()
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}