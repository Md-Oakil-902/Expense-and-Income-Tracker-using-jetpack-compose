package com.oakil.incomeandexpensetracker.viewModel

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.github.mikephil.charting.data.Entry
import com.oakil.incomeandexpensetracker.Utils
import com.oakil.incomeandexpensetracker.data.ExpenseDatabase
import com.oakil.incomeandexpensetracker.data.Model.ExpenseEntity
import com.oakil.incomeandexpensetracker.data.Model.ExpenseSummary
import com.oakil.incomeandexpensetracker.data.dao.ExpenseDao

class StatsViewModel(val dao: ExpenseDao): ViewModel() {
    val entries = dao.getAllExpenseByDate()

    fun getEntriesForChart(entries: List<ExpenseSummary>): List<Entry>{
        val list = mutableListOf<Entry>()
        for (entry in entries){
            val formattedDate = Utils.getMilliFromDate(entry.date)
            list.add(Entry(formattedDate.toFloat(), entry.total_amount.toFloat()))
        }
        return list
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
