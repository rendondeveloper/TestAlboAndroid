package com.albo.test.ui.home.view

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.albo.test.R
import com.albo.test.adapter.BeerAdapter
import com.albo.test.databinding.FragmentHomeBinding
import com.albo.test.ui.detail.DetailFragment
import com.albo.test.ui.home.repository.Repository
import com.albo.test.ui.home.viewmodel.HomeViewModel
import com.albo.test.ui.home.viewmodel.HomeViewModelFactory

class HomeFragment: Fragment() {

    companion object{
        const val PER_PAGE = 20
        const val LOADING = 0
        const val LOADING_END = 1
        const val ITEM_BEER = "ItemBeer"
        const val HERO = "HERO"
    }

    private val factory by lazy { HomeViewModelFactory(Repository(context = requireContext())) }
    private val viewModel: HomeViewModel by viewModels { factory }

    private lateinit var binding: FragmentHomeBinding

    private val beerAdapter by lazy {
        BeerAdapter(){ item ->
            val bundle = Bundle().apply {
                putParcelable(ITEM_BEER, item)
            }

            findNavController().navigate(R.id.action_homeFragment_to_detailFragment, bundle)
        }
    }

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initObserver()
        initView()
        viewModel.nextPageBeer(PER_PAGE)
    }

    private fun initObserver(){
        viewModel.beerResponse.observe(viewLifecycleOwner){ items ->
            viewModel.statusLoader.value = LOADING_END
            if(items.isEmpty() && beerAdapter.itemCount == 0){
                binding.apply {
                    clEmpty.visibility = View.VISIBLE
                    rvBeers.visibility = View.GONE
                }
            }else {
                binding.apply {
                    clEmpty.visibility = View.GONE
                    rvBeers.visibility = View.VISIBLE
                }
                beerAdapter.setList(items)
            }
        }

        viewModel.statusLoader.observe(viewLifecycleOwner){ status ->
            when(status){
                LOADING -> {
                    binding.llLoader.visibility = View.VISIBLE
                }
                LOADING_END -> {
                    binding.apply {
                        llLoader.visibility = View.GONE
                        if(srBeers.isRefreshing){
                            srBeers.isRefreshing = false
                        }
                    }
                }
            }
        }

        viewModel.errorResponse.observe(viewLifecycleOwner){
            Toast.makeText(context, it, Toast.LENGTH_SHORT).show()
        }
    }

    private fun initView() {
        binding.apply {

            srBeers.apply {
                setOnRefreshListener {
                    viewModel.loadCurrentPage(PER_PAGE)
                }
                setColorSchemeColors(Color.GREEN)
            }

            rvBeers.apply {
                layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
                adapter = beerAdapter
                addOnScrollListener(object : RecyclerView.OnScrollListener() {

                    override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                        super.onScrollStateChanged(recyclerView, newState)

                        if (!recyclerView.canScrollVertically(1)) {
                            viewModel.nextPageBeer(PER_PAGE)
                        }
                    }
                })
            }
        }
    }
}