package com.oakil.incomeandexpensetracker.data.Model

import androidx.room.Entity


@Entity(tableName = "expense_table")
data class ExpenseEntity(

    val id: Int,
    val title : String,
    val amount: Double,
    val date: Long,
    val category: String,
    val type: String
)
