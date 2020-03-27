package eu.rjch.kalkulatory.price_calculator

import android.content.Context.MODE_PRIVATE
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import eu.rjch.kalkulatory.R


class PriceCalculatorFragment : Fragment() {
    companion object {
        fun newInstance() = PriceCalculatorFragment()
    }

    private val TAG = "PRICE_CALCULATOR_FRAG"

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        runApp()
        return inflater.inflate(R.layout.price_calculator_fragment, container, false)
    }

    private fun runApp() {
        var pref = context?.getSharedPreferences(getString(R.string.price_calc_pref), MODE_PRIVATE)
        var editor = pref?.edit() as SharedPreferences.Editor
        var firstUseS = resources.getString(R.string.first_use)
        var firstUseB = pref.getBoolean(firstUseS, true)
        Log.d(TAG, "Running Price Fragment $firstUseS / $firstUseB")
        if(firstUseB) {
            editor.putBoolean(firstUseS, false)
            editor.commit()

            var i = Intent(context, PriceCalculatorSetup::class.java)
            startActivity(i)
        } else {
//            TODO the app activity intent
        }

    }
}