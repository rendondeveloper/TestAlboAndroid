package com.albo.test.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.albo.test.R
import com.albo.test.databinding.ItemBeerBinding
import com.albo.test.model.view.Beer
import com.albo.test.ui.home.view.HomeFragment.Companion.HERO
import com.albo.test.utils.bindImageUrl

class BeerAdapter(
    private val listener: (Beer) -> Unit
) :
    RecyclerView.Adapter<BeerAdapter.ChurchViewHolder>() {
    private val beerApiList: MutableList<Beer> = mutableListOf()


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChurchViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemBeerBinding.inflate(inflater, parent, false)
        return ChurchViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ChurchViewHolder, position: Int) {
        holder.bind(beerApiList[position], listener)
    }

    override fun getItemCount(): Int = beerApiList?.size

    fun setList(items: List<Beer>) {
        beerApiList.addAll(items)
        notifyDataSetChanged();
    }

    class ChurchViewHolder(private val binding: ItemBeerBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(
            item: Beer,
            listener: (Beer) -> Unit,
        ) {
            binding.apply {
                itemBeer = item
                ivBeer.apply {
                    bindImageUrl(
                        url = item.imageUrl,
                        errorPlaceholder = R.drawable.error_image,
                        loader =  R.drawable.loader
                    )
                    transitionName = HERO
                }
                root.setOnClickListener {
                    listener(item)
                }
            }
        }
    }
}