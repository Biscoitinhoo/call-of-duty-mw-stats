package com.example.callofdutymw_stats.database.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.callofdutymw_stats.database.dao.UserDAO
import com.example.callofdutymw_stats.model.multiplayer.lifetime.all.properties.UserInformationMultiplayer

class RoomDatabaseImpl {

    @Database(entities = [UserInformationMultiplayer::class], version = 1)
    abstract class AppDatabase : RoomDatabase() {
        abstract fun userDAO(): UserDAO
        object DatabaseBuilder {
            private var INSTANCE: AppDatabase? = null
            fun getInstance(context: Context): AppDatabase {
                if (INSTANCE == null) {
                    synchronized(AppDatabase::class) {
                        INSTANCE = buildRoomDB(context)
                    }
                }
                return INSTANCE!!
            }
            private fun buildRoomDB(context: Context) =
                Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "mindorks-example-coroutines"
                ).fallbackToDestructiveMigration().build()

        }

    }

}