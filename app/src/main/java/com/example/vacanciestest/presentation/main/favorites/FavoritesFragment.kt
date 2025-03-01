package com.example.vacanciestest.presentation.main.favorites

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.vacanciestest.R
import com.example.vacanciestest.databinding.FragmentFavoritesBinding
import com.example.vacanciestest.domain.models.Vacancy
import com.example.vacanciestest.presentation.main.search.VacancyAdapter

class FavoritesFragment : Fragment() {
    private var _binding: FragmentFavoritesBinding? = null
    private val binding get() = _binding!!

    private lateinit var viewModel: FavoritesSharedViewModel
    private lateinit var vacanciesAdapter: VacancyAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFavoritesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val viewModelProvider = ViewModelProvider(
            requireActivity(),
            ViewModelProvider.AndroidViewModelFactory.getInstance(requireActivity().application)
        )
        viewModel = viewModelProvider[FavoritesSharedViewModel::class.java]

        vacanciesAdapter = VacancyAdapter(requireContext())
        binding.recyclerView.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = vacanciesAdapter
        }

        vacanciesAdapter.favoriteItemClickListener =
            object : VacancyAdapter.FavoriteItemClickListener {
                override fun onToggleFavorite(vacancyId: String) {
                    viewModel.toggleFavorite(vacancyId)
                }
            }

        viewModel.favoritesCount.observe(viewLifecycleOwner) { count ->
            binding.countTextView.text = requireContext().resources.getQuantityString(R.plurals.favorites_count, count, count)
        }
        viewModel.favoriteVacancies.observe(viewLifecycleOwner) { vacancies ->
            showData(vacancies)
        }
        viewModel.error.observe(viewLifecycleOwner) {
            showError()
        }

        showLoading()
        viewModel.loadFavorites()
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
                viewModel.loadFavorites()
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}