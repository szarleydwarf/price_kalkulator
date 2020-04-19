package eu.rjch.kalkulatory.oops

import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import eu.rjch.kalkulatory.R

class PC_ObjectToSave {
    val TAG = "PC_OBJECTTOSAVE"
    lateinit var pref:SharedPreferences
    lateinit var editor:SharedPreferences.Editor

    var profit_field:Boolean = false
    var tax_field:Boolean = false
    var extra_cost_field:Boolean = false
    var profit_percentage:Int = 0
    var max_profit_percentage:Int = 0
    var tax_in_percent:Float = 0.0f
    var number_of_extras:Int = 0
    lateinit var array_of_extras:Array<ExtraCost>

    fun loadSharedPrefs(ctx : Context){
        pref = ctx.getSharedPreferences(ctx.getString(R.string.price_calc_pref), Context.MODE_PRIVATE) as SharedPreferences
        // apearence
        profit_field = pref.getBoolean(ctx.getString(R.string.show_profit), false)
        tax_field = pref.getBoolean(ctx.getString(R.string.show_tax), false)
        extra_cost_field = pref.getBoolean(ctx.getString(R.string.show_extra_costs), false)
        number_of_extras = pref.getInt(ctx.getString(R.string.no_of_extras), 0)
        // values
        profit_percentage = pref.getInt(ctx.getString(R.string.profit), 20)
        max_profit_percentage = pref.getInt(ctx.getString(R.string.max_profit), 100)
        tax_in_percent = pref.getFloat(ctx.getString(R.string.tax), 21.0f)
        if(number_of_extras > 0){
            array_of_extras =  Array(number_of_extras){ ExtraCost() }
            var n = 0
            while (n < number_of_extras) {
                var e = ExtraCost().load(pref, ctx) as ExtraCost
                array_of_extras[n] = e
                n++
            }
        }
        Log.d(TAG, "Printing values to Log.d.......")
        printElementinLog()
        Log.d(TAG, "End Printing values to Log.d.......")
    }

    fun saveSharedPrefs(ctx: Context) {

    }

    fun printElementinLog() {
        Log.d(TAG, "\nprofit:\n" +
                "- $profit_field - $profit_percentage - $max_profit_percentage" +
                "\n" +
                "tax\n" +
                " - $tax_field - $tax_in_percent\n" +
                "extras\n" +
                "- $extra_cost_field - $number_of_extras")
        //todo lateinit property array_of_extras has not been initialized
        for(ex in array_of_extras)
            Log.d(TAG, "ex - ${ex.extra_cost_name} - ${ex.extra_cost_value}")
    }

    /*
BOOLEANS
- profit field visibility
- tax field visibility
- extras field visibility

VALUES
- profit (Int) - max value for profit?
- VAT value(Double/Float)
- number of extras(Int)

OTHER
prefs - SharedPreferences - will need Context
editor - SharedPreferences.Editor

FUNCTIONS
Load/Save prefs
Load/Save file
*/
}
class ExtraCost {
    lateinit var extra_cost_name:String
    var extra_cost_value:Float = 0.0f

    fun load(pref : SharedPreferences, ctx: Context) {
        extra_cost_name = pref.getString(ctx.getString(R.string.et_extra_cost_name), ctx.getString(R.string.et_extra_cost_name)).toString()
        extra_cost_value = pref.getFloat(ctx.getString(R.string.et_extra_cost_value), 0.00f)
    }

}