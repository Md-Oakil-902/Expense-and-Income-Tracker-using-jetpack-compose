package com.oakil.incomeandexpensetracker

import java.text.SimpleDateFormat
import java.util.Locale

object Utils{
    fun formatDateToHumanReadableForm(dateInMills: Long): String{
        val dateFormatter = SimpleDateFormat("dd/MM/YYYY", Locale.getDefault())
        return dateFormatter.format(dateInMills)
    }
}