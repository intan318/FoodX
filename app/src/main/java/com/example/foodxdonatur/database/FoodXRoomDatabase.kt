package com.example.foodxdonatur.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Database(
    version = 1, exportSchema = false,
    entities = [
        UserDB::class
    ]
)

abstract class FoodXRoomDatabase: RoomDatabase() {

    abstract fun userDao(): UserDao

    companion object{
        @Volatile
        internal var INSTANCE: FoodXRoomDatabase? = null

        fun getDatabase(context: Context, scope: CoroutineScope): FoodXRoomDatabase{

            return INSTANCE ?: synchronized(this){
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    FoodXRoomDatabase::class.java,
                    "foodx_database.db"
                )
                    .addCallback(FoodXDatabaseCallback(scope))
                    .build()
                INSTANCE = instance
                instance
            }
        }

        private class FoodXDatabaseCallback(
            private val scope: CoroutineScope
        ) : RoomDatabase.Callback(){

            override fun onOpen(db: SupportSQLiteDatabase) {
                super.onOpen(db)

                INSTANCE?.let {
                    scope.launch(Dispatchers.IO){

                    }
                }
            }
        }
    }
}