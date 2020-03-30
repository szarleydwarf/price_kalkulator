package eu.rjch.kalkulatory.price_calculator

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import eu.rjch.kalkulatory.R
import eu.rjch.kalkulatory.rjutil.AnimationManager
import kotlinx.android.synthetic.main.price_calc_setup_frag.*
import kotlinx.android.synthetic.main.price_calc_setup_frag.view.*

class PriceCalculatorSetupFragment : Fragment(){

    companion object {
        fun newInstance() = PriceCalculatorSetupFragment
    }

    private val TAG = "PRICE_C_SETUP_FR"

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        var v = inflater.inflate(R.layout.price_calc_setup_frag, container, false)
        runApp(v)
        return v
    }

    private fun runApp(v: View) {
        val amp = 0.2
        val freq = 20.0
        v.btn_price_calculator_values.setOnClickListener { AnimationManager().didTapButonInterpolate(
                v.btn_price_calculator_values, context, R.anim.bounce,
                amp, freq) }
        v.btn_price_calculator_appearance.setOnClickListener { AnimationManager().didTapButonInterpolate(
                v.btn_price_calculator_appearance, context, R.anim.bounce, amp, freq) }
    }
}