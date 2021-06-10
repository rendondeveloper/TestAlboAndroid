package com.albo.test.ui.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.albo.test.R
import com.albo.test.databinding.FragmentDetailBinding
import com.albo.test.ui.home.view.HomeFragment.Companion.HERO
import com.albo.test.ui.home.view.HomeFragment.Companion.ITEM_BEER
import com.albo.test.utils.bindImageUrl

class DetailFragment: Fragment() {

    private lateinit var binding: FragmentDetailBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentDetailBinding.inflate(inflater, container, false)
        binding.itemBeerDetail = arguments?.getParcelable(ITEM_BEER)
        binding.apply {
            ivBeerDetail.apply {
                bindImageUrl(
                    url = itemBeerDetail?.imageUrl,
                    errorPlaceholder = R.drawable.error_image,
                    loader =  R.drawable.loader
                )
                transitionName = HERO
            }
        }
        return binding.root
    }
}