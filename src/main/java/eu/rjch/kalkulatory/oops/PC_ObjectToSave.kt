package eu.rjch.kalkulatory.oops

import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import eu.rjch.kalkulatory.R

class PC_ObjectToSave {
    val TAG = "PCO2S"
    lateinit var pref:SharedPreferences
    lateinit var editor:SharedPreferences.Editor

    var profit_field:Boolean = false
    var tax_field:Boolean = false
    var extra_cost_field:Boolean = false
    var number_of_extras:Int = 0

    var profit_percentage:Int = 0
    var max_profit_percentage:Int = 0
    var tax_in_percent:Float = 0.0f
    var array_of_extras:Array<ExtraCost> = Array(0){ ExtraCost() }

    fun loadSPAppearance(ctx : Context){
        pref = ctx.getSharedPreferences(ctx.getString(R.string.price_calc_pref), Context.MODE_PRIVATE) as SharedPreferences
        // apearence
        profit_field = pref.getBoolean(ctx.getString(R.string.show_profit), false)
        tax_field = pref.getBoolean(ctx.getString(R.string.show_tax), false)
        extra_cost_field = pref.getBoolean(ctx.getString(R.string.show_extra_costs), false)
        number_of_extras = pref.getInt(ctx.getString(R.string.no_of_extras), 0)

//        Log.d(TAG, "End Printing values to Log.d.......")
    }

    fun loadSPValues(ctx : Context) {
        pref = ctx.getSharedPreferences(ctx.getString(R.string.price_calc_pref), Context.MODE_PRIVATE) as SharedPreferences
        // values
        profit_percentage = pref.getInt(ctx.getString(R.string.profit), 20)
        max_profit_percentage = pref.getInt(ctx.getString(R.string.max_profit), 100)
        tax_in_percent = pref.getFloat(ctx.getString(R.string.tax), 21.0f)
    }

    fun loadSPExtraCosts(ctx : Context) {
        pref = ctx.getSharedPreferences(ctx.getString(R.string.price_calc_pref), Context.MODE_PRIVATE) as SharedPreferences
        if(number_of_extras > 0){
            array_of_extras =  Array(number_of_extras){ ExtraCost() }
            var n = 0
            while (n < number_of_extras) {
                var e = ExtraCost().loadMe(pref, ctx) as ExtraCost
                array_of_extras[n] = e
                Log.d(TAG, "ex - ${e.extra_cost_name} - ${e.extra_cost_value}")

                n++
            }
        }
    }

    fun saveSPAppearance(ctx: Context) {
        pref = ctx.getSharedPreferences(ctx.getString(R.string.price_calc_pref), Context.MODE_PRIVATE) as SharedPreferences
        editor = pref.edit() as SharedPreferences.Editor
        // appearnace
        editor.putBoolean(ctx.getString(R.string.show_profit), profit_field)
        editor.putBoolean(ctx.getString(R.string.show_tax), tax_field)
        editor.putBoolean(ctx.getString(R.string.show_extra_costs), extra_cost_field)
        editor.putInt(ctx.getString(R.string.no_of_extras), number_of_extras)

        editor.commit()
    }

    fun saveSPValues(ctx: Context) {
        pref = ctx.getSharedPreferences(ctx.getString(R.string.price_calc_pref), Context.MODE_PRIVATE) as SharedPreferences
        editor = pref.edit() as SharedPreferences.Editor
        // values
        editor.putInt(ctx.getString(R.string.profit), profit_percentage)
        editor.putInt(ctx.getString(R.string.max_profit), max_profit_percentage)
        editor.putFloat(ctx.getString(R.string.tax), tax_in_percent)

        editor.commit()
    }

    fun saveSPExtraCosts(ctx:Context){
        pref = ctx.getSharedPreferences(ctx.getString(R.string.price_calc_pref), Context.MODE_PRIVATE) as SharedPreferences
        editor = pref.edit() as SharedPreferences.Editor

        if(!array_of_extras.isNullOrEmpty()) {
            for(e in array_of_extras){
                e.saveMe(editor, ctx)
            }
        }
    }


    fun printElementinLog(tag:String) {
        Log.d(tag, "\nprofit:" +
                "- $profit_field - $profit_percentage - $max_profit_percentage" +
                "\n" +
                "tax" +
                " - $tax_field - $tax_in_percent\n" +
                "extras" +
                "- $extra_cost_field - $number_of_extras")
        //todo lateinit property  has not been initialized
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

    fun loadMe(pref : SharedPreferences, ctx: Context) :ExtraCost{
        extra_cost_name = pref.getString(ctx.getString(R.string.et_extra_cost_name), ctx.getString(R.string.et_extra_cost_name)).toString()
        extra_cost_value = pref.getFloat(ctx.getString(R.string.et_extra_cost_value), 0.00f)
        return this
    }

    fun saveMe(editor:SharedPreferences.Editor, ctx: Context) {
        editor.putString(ctx.getString(R.string.et_extra_cost_name), extra_cost_name)
        editor.putFloat(ctx.getString(R.string.et_extra_cost_value), extra_cost_value)
        editor.commit()
    }

}