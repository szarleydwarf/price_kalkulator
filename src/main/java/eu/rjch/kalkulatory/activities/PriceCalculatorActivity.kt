package eu.rjch.kalkulatory.activities

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import eu.rjch.kalkulatory.R
import eu.rjch.kalkulatory.price_calculator.PriceCalculatorFragment

class PriceCalculatorActivity : AppCompatActivity() {
    val TAG:String = "PCActivity"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.price_calculator_activity)

        firstUse()

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.container_price_calculator,
                    PriceCalculatorFragment.newInstance()
                )
                .commitNow()
        }
    }

    private fun firstUse() {
        var pref = getSharedPreferences(getString(R.string.price_calc_pref), Context.MODE_PRIVATE)
        var editor = pref?.edit() as SharedPreferences.Editor
        var firstUseS = resources.getString(R.string.first_use)
        var firstUseB = pref.getBoolean(firstUseS, true)
        editor.putBoolean(firstUseS, true)
        editor.commit()
        Log.d(TAG, "Running Price Fragment $firstUseS / $firstUseB")
        if(firstUseB) {
            editor.putBoolean(firstUseS, false)
            var i = Intent(this, PCSettingsActivity::class.java)
            startActivity(i)


//            var fragTrans = activity?.supportFragmentManager?.beginTransaction()
//            fragTrans?.replace(R.id.container_price_calculator, PriceCalculatorSetupFragment())
//            fragTrans?.addToBackStack(null)?.commit()

        } else {
//            TODO the app activity fragment
            supportFragmentManager.beginTransaction()
                    .replace(R.id.container_price_calculator,
                            PriceCalculatorFragment.newInstance()
                    )
                    .commitNow()
        }

    }
}