package com.albo.test.retrofit

import com.albo.test.config.WebConfig
import com.albo.test.model.api.BeerApi
import kotlinx.coroutines.Deferred
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface Api {
    @GET(WebConfig.BEER)
    fun getDataListAsync(
        @Query("page") page: Int,
        @Query("per_page") per_page: Int
    ): Deferred<Response<List<BeerApi>>>
}