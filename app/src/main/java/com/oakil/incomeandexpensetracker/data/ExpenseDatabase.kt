package com.oakil.incomeandexpensetracker.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.oakil.incomeandexpensetracker.data.Model.ExpenseEntity
import com.oakil.incomeandexpensetracker.data.dao.ExpenseDao


@Database(entities = [ExpenseEntity::class], version = 1)
abstract class ExpenseDatabase : RoomDatabase() {
    abstract fun expenseDao(): ExpenseDao

    companion object {
        const val DATABASE_NAME = "expense_database"

        fun getDatabase(context: Context): ExpenseDatabase {
            return Room.databaseBuilder(context, ExpenseDatabase::class.java, DATABASE_NAME).build()
        }


    }
}