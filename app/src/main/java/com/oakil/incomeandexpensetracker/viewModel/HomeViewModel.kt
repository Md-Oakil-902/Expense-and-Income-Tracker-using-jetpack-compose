package com.oakil.incomeandexpensetracker.viewModel

import androidx.lifecycle.ViewModel
import com.oakil.incomeandexpensetracker.data.Model.ExpenseEntity
import com.oakil.incomeandexpensetracker.data.dao.ExpenseDao

class HomeViewModel(dao:ExpenseDao): ViewModel() {

    val expense = dao.getAllExpense()

    fun getBalance(list: List<ExpenseEntity>) : String{
        var total  = 0.0
        list.forEach{
            if(it.type == "Income")
            {
                total += it.amount
            }else{
                total -= it.amount
            }
        }
        return "$ ${total}"


    }

    fun getTotalExpense(list: List<ExpenseEntity>): String{
        var total = 0.0
        list.forEach{
            if(it.type == "Expense"){
                total += it.amount
            }
        }
        return "$ ${total}"

    }

    fun getTotalIncome(list: List<ExpenseEntity>): String{
        var total = 0.0
        list.forEach{
            if(it.type == "Income"){
                total += it.amount
            }
        }
        return "$ ${total}"

    }


}