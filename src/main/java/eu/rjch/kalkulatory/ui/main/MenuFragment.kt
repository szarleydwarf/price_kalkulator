package eu.rjch.kalkulatory.ui.main

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import eu.rjch.kalkulatory.PriceCalculatorActivity
import eu.rjch.kalkulatory.R
import eu.rjch.kalkulatory.rjutil.AdsHandler
import eu.rjch.kalkulatory.rjutil.AnimationManager
import kotlinx.android.synthetic.main.calculators_menu.view.*
import java.lang.ClassCastException

class MenuFragment : Fragment() {

    companion object {
        fun newInstance() = MenuFragment()
    }
    var actCallback : MenuFragment.btnListener? = null
    interface btnListener {
        fun onBtnClicked(act: Class<*>)
    }

    private val TAG = "MENUFRAGMENT"

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?): View {
        var v = inflater.inflate(R.layout.calculators_menu, container, false)

        v.price_calculator_btn.setOnClickListener{btnClicked(v, R.id.price_calculator_btn)}
        v.other_calculator_btn.setOnClickListener { btnClicked(v, R.id.other_calculator_btn) }
        loadAdds(v)
        return v
    }

    private fun loadAdds(v: View?) {
        AdsHandler().getAds(v?.findViewById(R.id.adViewB)!!)
    }


    override fun onAttach(context: Context) {
        super.onAttach(context)
        try {
            actCallback = context as MenuFragment.btnListener
        } catch (e: ClassCastException){
            Log.d(TAG, "Error with class cast - ${e.message}")
        }
    }

    private fun btnClicked(v: View, id: Int) {
        val amp = 0.2
        val freq = 20.0
        when(id) {
            R.id.price_calculator_btn -> {
                AnimationManager().didTapButonInterpolate(
                        v.price_calculator_btn, context, R.anim.bounce,
                        amp, freq)

                actCallback?.onBtnClicked(PriceCalculatorActivity::class.java)
            }
            R.id.other_calculator_btn -> {
                AnimationManager().didTapButonInterpolate(
                        v.other_calculator_btn, context, R.anim.bounce,
                        amp, freq)
                Toast.makeText(context, "On going :)", Toast.LENGTH_SHORT).show()

            }
        }
    }

}
//PriceCalculatorActivity::class.java