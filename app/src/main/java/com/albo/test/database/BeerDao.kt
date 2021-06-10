package com.albo.test.database

import androidx.room.*
import com.albo.test.model.local.BeerLocal
import kotlinx.coroutines.Deferred
import retrofit2.Response

@Dao
interface BeerDao {
    @Query("SELECT * FROM BeerLocal WHERE page = :page limit :limitRows")
    fun getAsync(page: Int, limitRows: Int): List<BeerLocal>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertCharacter(beers : List<BeerLocal>)
}
