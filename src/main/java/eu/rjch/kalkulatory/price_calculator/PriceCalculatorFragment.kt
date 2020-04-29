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
import com.google.android.gms.ads.MobileAds
import eu.rjch.kalkulatory.R
import eu.rjch.kalkulatory.activities.PCSettingsActivity
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

//        AdsHandler().getAds(v?.findViewById(R.id.adViewB)!!)

        runApp()
        return v
    }

    private fun runApp() {
Log.d(TAG, "Running PCCalculatorFragment....")
    }
}