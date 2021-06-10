package com.albo.test.ui.home.repository
import android.content.Context
import androidx.lifecycle.MutableLiveData
import com.albo.test.config.WebConfig
import com.albo.test.database.RoomDataBaseTest
import com.albo.test.model.api.BeerApi
import com.albo.test.model.mapper.toListBeerLocal
import com.albo.test.model.mapper.toListBeerView
import com.albo.test.model.mapper.toListBeerViewTo
import com.albo.test.model.view.Beer
import com.albo.test.retrofit.Api
import com.albo.test.retrofit.build.RetrofitApp
import com.albo.test.retrofit.managercall.ManagerCall
import com.albo.test.retrofit.modelretrofit.dataclass.ResponseData
import com.albo.test.retrofit.modelretrofit.exception.ConnectionException
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Response
import java.lang.Exception

class Repository(val context : Context) : ManagerCall() {
    val beerResponse = MutableLiveData<List<Beer>>()
    val errorResponse = MutableLiveData<String>()

    private val database by lazy {  RoomDataBaseTest.getDatabase(context).BeerDaoImpl() }

    private val retrofitInstance = RetrofitApp.Build<Api>()
        .setHost(WebConfig.HOST)
        .setContext(context)
        .setClass(Api::class.java)
        .builder().instance()

    suspend  fun getDataBeer(page : Int , per_page : Int) {
        managerCallApi(
            call = {
                retrofitInstance.getDataListAsync(page, per_page).await()
            }
        ).let { response ->
            withContext(Dispatchers.Main){
                when {
                    response.sucess -> {
                        database.insertCharacter(response.data!!.toListBeerLocal(page))
                        beerResponse.value = response.data!!.toListBeerView()
                    }
                    response.exception is ConnectionException -> {
                        beerResponse.value = database.getAsync(page, per_page)!!.toListBeerViewTo()
                    }
                    else -> {
                        errorResponse.value = response.exception?.message
                    }
                }
            }
        }
    }
}