package eu.rjch.kalkulatory.price_calculator

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import eu.rjch.kalkulatory.R

class PriceCalculatorSetupFragment : Fragment(){

    companion object {
        fun newInstance() = PriceCalculatorSetupFragment
    }

    private val TAG = "PRICE_C_SETUP_FR"

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.price_calc_setup_frag, container, false)
    }
}