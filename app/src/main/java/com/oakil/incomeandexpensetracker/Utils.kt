package com.oakil.incomeandexpensetracker

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

object Utils{
    fun formatDateToHumanReadableForm(dateInMills: Long): String{
        val dateFormatter = SimpleDateFormat("dd/MM/YYYY", Locale.getDefault())
        return dateFormatter.format(dateInMills)
    }

    fun formatDecimalValue(d:Double) : String{
        return String.format("%.2f", d)
    }

    fun getMilliFromDate(dateFormat: String?) : Long{
        var date = Date()vval
    }ddddddd
}