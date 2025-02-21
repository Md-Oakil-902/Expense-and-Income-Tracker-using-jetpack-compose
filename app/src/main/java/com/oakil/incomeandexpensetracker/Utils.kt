package com.oakil.incomeandexpensetracker

import java.text.ParseException
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

    fun formatDayMonth(dateInMills: Long):String{
        val dateFormatter = SimpleDateFormat("dd/MMM", Locale.getDefault())
        return dateFormatter.format(dateInMills)
    }

    fun getMilliFromDate(dateFormat: String?) : Long{
        var date = Date()
        val formatter = SimpleDateFormat("dd/MM/YYYY" , Locale.getDefault())

        try{
            date = formatter.parse(dateFormat)
        }catch (e:ParseException){
            e.printStackTrace()
        }
        println("Today is $date")
        return date.time
        
    }
}