package com.byteslabs.bookshop.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.byteslabs.bookshop.db.dao.AuthorDao
import com.byteslabs.bookshop.db.dao.BookDao
import com.byteslabs.bookshop.db.model.AuthorEntity
import com.byteslabs.bookshop.db.model.BookEntity


@Database(entities = [AuthorEntity::class,BookEntity::class], version = 1)
abstract class AppDatabase : RoomDatabase() {

    abstract fun authorDao(): AuthorDao
    abstract fun bookDao() : BookDao

    companion object {

        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            val tempInstance = INSTANCE
            if (tempInstance != null) {
                return tempInstance
            }
            synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "bookshop_database"
                ).build()
                INSTANCE = instance
                return instance
            }
        }
    }
}