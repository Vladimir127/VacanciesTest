package com.example.vacanciestest.presentation.main.search

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.vacanciestest.R
import com.example.vacanciestest.databinding.FragmentSearchBinding
import com.example.vacanciestest.domain.models.Offer
import com.example.vacanciestest.domain.models.Vacancy
import com.example.vacanciestest.presentation.main.favorites.FavoritesSharedViewModel
import com.example.vacanciestest.utils.openWebSite

class SearchFragment : Fragment() {
    private var _binding: FragmentSearchBinding? = null
    private val binding get() = _binding!!

    private lateinit var searchViewModel: SearchViewModel
    private lateinit var favoritesSharedViewModel: FavoritesSharedViewModel
    private lateinit var vacanciesAdapter: VacancyAdapter
    private lateinit var offersAdapter: OfferAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSearchBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val viewModelProvider = ViewModelProvider(
            requireActivity(),
            ViewModelProvider.AndroidViewModelFactory.getInstance(requireActivity().application)        )
        favoritesSharedViewModel = viewModelProvider[FavoritesSharedViewModel::class.java]

        searchViewModel = ViewModelProvider(this@SearchFragment)[SearchViewModel::class.java]

        vacanciesAdapter = VacancyAdapter(requireContext())
        binding.recyclerView.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = vacanciesAdapter
        }

        vacanciesAdapter.onItemClickListener = {
            findNavController().navigate(R.id.action_searchFragment_to_vacancyFragment)
        }

        vacanciesAdapter.favoriteItemClickListener =
            object : VacancyAdapter.FavoriteItemClickListener {
                override fun onToggleFavorite(vacancyId: String) {
                    favoritesSharedViewModel.toggleFavorite(vacancyId)
                }
            }

        offersAdapter = OfferAdapter()
        binding.offersRecyclerView.apply {
            layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
            adapter = offersAdapter
        }

        offersAdapter.onItemClickListener = { link ->
            openWebSite(requireContext(), link)
        }

        binding.moreVacanciesButton.setOnClickListener {
            it.findNavController().navigate(R.id.action_searchFragment_to_moreVacanciesFragment)
        }

        searchViewModel.vacancies.observe(viewLifecycleOwner) { vacancies ->
            showData(vacancies)
        }
        searchViewModel.offers.observe(viewLifecycleOwner) { offers ->
            showOffers(offers)
        }
        searchViewModel.vacanciesCount.observe(viewLifecycleOwner) { count ->
            binding.moreVacanciesButton.apply {
                text = resources.getQuantityString(R.plurals.more_vacancies_count, count, count)
                visibility = View.VISIBLE
            }
        }
        searchViewModel.error.observe(viewLifecycleOwner) {
            showError()
        }

        showLoading()
        searchViewModel.loadVacancies()
        searchViewModel.loadOffers()
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
        searchViewModel.loadVacanciesCount()
    }

    private fun showOffers(offers: List<Offer>) {
        offersAdapter.setData(offers)
    }

    private fun showError() {
        with(binding) {
            errorLayout.visibility = View.VISIBLE
            loadingLayout.visibility = View.INVISIBLE
            dataLayout.visibility = View.INVISIBLE

            retryButton.setOnClickListener {
                showLoading()
                searchViewModel.loadVacancies()
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}