package eu.rjch.kalkulatory.rjutil

import android.util.Log
import java.text.SimpleDateFormat
import java.util.*

class DateHandler {

    fun getToday() : String{
        val sdf = SimpleDateFormat("dd_M_yyyy")
        return sdf.format(Date())
    }
    fun compareTodays(today:String, otherToday:String) : Boolean{
        if(today.equals(otherToday, true))
            return true
        return false
    }

}