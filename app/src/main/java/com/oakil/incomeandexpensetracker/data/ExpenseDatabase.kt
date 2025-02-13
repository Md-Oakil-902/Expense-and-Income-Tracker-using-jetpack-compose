package com.oakil.incomeandexpensetracker.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.oakil.incomeandexpensetracker.data.Model.ExpenseEntity
import com.oakil.incomeandexpensetracker.data.dao.ExpenseDao
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


@Database(entities = [ExpenseEntity::class], version = 1)
abstract class ExpenseDatabase : RoomDatabase() {
    abstract fun expenseDao(): ExpenseDao

    companion object {
        const val DATABASE_NAME = "expense_database"

        fun getDatabase(context: Context): ExpenseDatabase {
            return Room.databaseBuilder(context, ExpenseDatabase::class.java, DATABASE_NAME).addCallback(object : RoomDatabase.Callback(){
                override fun onCreate(db: SupportSQLiteDatabase) {
                    super.onCreate(db)
                    initBasicData(context)
                }
                fun initBasicData(context: Context){
                    CoroutineScope(Dispatchers.IO).launch {
                        val dao = getDatabase(context).expenseDao()

                        dao.insertExpense(ExpenseEntity(1, "Salary", 500.32, System.currentTimeMillis().toString(), "Salary", "Income" ))
                        dao.insertExpense(ExpenseEntity(2, "Paypal", 506780.32, System.currentTimeMillis().toString(), "Paypal", "Income" ))
                        dao.insertExpense(ExpenseEntity(3, "Netflix", 430000.32, System.currentTimeMillis().toString(), "Netflix", "Expense" ))
                        dao.insertExpense(ExpenseEntity(4, "Starbucks", 50000.32, System.currentTimeMillis().toString(), "Starbucks", "Expense" ))

                    }
                }
            }).build()
        }
    }
}


