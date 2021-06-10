package com.albo.test.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.albo.test.model.local.BeerLocal

@Database(entities = [BeerLocal::class], version = 2)
abstract class RoomDataBaseTest : RoomDatabase() {

    abstract fun BeerDaoImpl(): BeerDao

    companion object {
        private const val DATABASE_NAME = "TestAlbo"
        @Synchronized
        fun getDatabase(context: Context) : RoomDataBaseTest = Room.databaseBuilder(
            context.applicationContext,
            RoomDataBaseTest::class.java,
            DATABASE_NAME
        ).allowMainThreadQueries().build()
    }

    //endregion

}
