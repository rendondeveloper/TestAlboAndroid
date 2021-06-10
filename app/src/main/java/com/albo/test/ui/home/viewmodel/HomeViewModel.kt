package com.albo.test.ui.home.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.albo.test.ui.home.repository.Repository
import com.albo.test.ui.home.view.HomeFragment.Companion.LOADING
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.internal.wait

class HomeViewModel(private val repository: Repository) : ViewModel() {
    val beerResponse = repository.beerResponse
    val errorResponse = repository.errorResponse
    val statusLoader : MutableLiveData<Int> = MutableLiveData()

    var pageCurrent = 0

    fun nextPageBeer (per_page: Int) {
        addPage()

        loadCurrentPage(per_page)
    }

    private fun addPage(){
        pageCurrent++

        if(pageCurrent > 1) {
            statusLoader.value = LOADING
        }
    }

    fun loadCurrentPage (per_page: Int) {
        GlobalScope.launch {
            repository.getDataBeer(pageCurrent, per_page)
        }
    }
}