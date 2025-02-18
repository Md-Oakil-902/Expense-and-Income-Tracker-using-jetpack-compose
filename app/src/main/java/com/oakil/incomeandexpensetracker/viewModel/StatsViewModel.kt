package com.oakil.incomeandexpensetracker.viewModel

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.oakil.incomeandexpensetracker.data.ExpenseDatabase
import com.oakil.incomeandexpensetracker.data.Model.ExpenseEntity
import com.oakil.incomeandexpensetracker.data.dao.ExpenseDao
import java.security.KeyStore.Entry

class StatsViewModel(val dao: ExpenseDao): ViewModel() {
    val entries = dao.getAllExpenseByDate()

    fun getEntriesForChart(entries: List<ExpenseEntity>): List<Entry>{
        return entries
    }




}

class StatsViewModelFactory(private val context : Context) : ViewModelProvider.Factory{
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(StatsViewModel::class.java)){
            val dao = ExpenseDatabase.getDatabase(context).expenseDao()
            @Suppress("UNCHECKED_CAST")
            return StatsViewModel(dao) as T
        }
        throw IllegalArgumentException("Unknown ViewModel Class")
    }
}
