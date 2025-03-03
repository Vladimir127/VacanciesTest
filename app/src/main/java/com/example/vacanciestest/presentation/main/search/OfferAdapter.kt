package com.example.vacanciestest.presentation.main.search

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.vacanciestest.R
import com.example.vacanciestest.databinding.ItemOfferBinding
import com.example.vacanciestest.domain.models.Offer

class OfferAdapter : RecyclerView.Adapter<OfferAdapter.OfferViewHolder>() {
    private var offers : List<Offer> = emptyList()
    var onItemClickListener: ((String) -> Unit)? = null

    fun setData(offers: List<Offer>) {
        this.offers = offers
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OfferViewHolder {
        return OfferViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.item_offer, parent, false
            )
        )
    }

    override fun getItemCount() = offers.size

    override fun onBindViewHolder(holder: OfferViewHolder, position: Int) {
        val offer = offers[position]

        holder.bind(offer)
    }

    inner class OfferViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val binding = ItemOfferBinding.bind(view)
        private lateinit var offer: Offer

        init {
            itemView.setOnClickListener {
                onItemClickListener?.invoke(offer.link)
            }
        }

        fun bind(offer: Offer) {
            this.offer = offer

            with(binding) {
                if (offer.id == null) {
                    iconImageView.visibility = View.GONE
                } else {
                    val imageResource = when(offer.id) {
                        "near_vacancies" -> R.drawable.ic_offer_location
                        "level_up_resume" -> R.drawable.ic_offer_star
                        "temporary_job" -> R.drawable.ic_offer_list
                        else -> null
                    }

                    imageResource?.let {
                        iconImageView.visibility = View.VISIBLE
                        iconImageView.setImageResource(imageResource)
                    }
                }

                titleTextView.text = offer.title

                if (offer.button == null) {
                    linkTextView.visibility = View.GONE
                } else {
                    linkTextView.visibility = View.VISIBLE
                    linkTextView.text = offer.button?.text
                }
            }
        }
    }
}