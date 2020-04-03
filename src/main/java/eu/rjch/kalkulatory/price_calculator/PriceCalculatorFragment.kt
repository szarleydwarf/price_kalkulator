package eu.rjch.kalkulatory.price_calculator

import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.google.android.gms.ads.MobileAds
import eu.rjch.kalkulatory.R
import eu.rjch.kalkulatory.rjutil.AdsHandler


class PriceCalculatorFragment : Fragment() {
    companion object {
        fun newInstance() = PriceCalculatorFragment()
    }

    private val TAG = "PRICE_CALCULATOR_FRAG"

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        var v = inflater.inflate(R.layout.price_calculator_fragment, container, false)

        AdsHandler().getAds(v?.findViewById(R.id.adViewB)!!)

        runApp()
        return v
    }

    private fun runApp() {
        var pref = context?.getSharedPreferences(getString(R.string.price_calc_pref), MODE_PRIVATE)
        var editor = pref?.edit() as SharedPreferences.Editor
        var firstUseS = resources.getString(R.string.first_use)
        var firstUseB = pref.getBoolean(firstUseS, true)
        editor.putBoolean(firstUseS, true)
        editor.commit()
        Log.d(TAG, "Running Price Fragment $firstUseS / $firstUseB")
        if(firstUseB) {
            editor.putBoolean(firstUseS, false)

            var fragTrans = activity?.supportFragmentManager?.beginTransaction()
            fragTrans?.replace(R.id.container_price_calculator, PriceCalculatorSetupFragment())
            fragTrans?.addToBackStack(null)?.commit()

        } else {
//            TODO the app activity fragment
        }

    }
}