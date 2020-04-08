package eu.rjch.kalkulatory.price_calculator

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import eu.rjch.kalkulatory.R
import eu.rjch.kalkulatory.rjutil.AdsHandler

class PCValuesFragment : Fragment() {

    private val TAG = "PRICE_C_SETUP_FR"

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        var v = inflater.inflate(R.layout.pc_values_frag_layout, container, false)

        AdsHandler().getAds(v?.findViewById(R.id.adViewB)!!)

        runApp(v)
        return v
    }

    private fun runApp(v: View) {

    }

}