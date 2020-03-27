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
        Log.d(TAG, "1 Running Menu Fragment")

        v.price_calculator_btn.setOnClickListener{btnClicked(R.id.price_calculator_btn)}
        v.other_calculator_btn.setOnClickListener { btnClicked(R.id.other_calculator_btn) }

        return v
    }


    override fun onAttach(context: Context?) {
        super.onAttach(context)
        try {
            actCallback = context as MenuFragment.btnListener
        } catch (e: ClassCastException){
            Log.d(TAG, "Error with class cast - ${e.message}")
        }
    }

    private fun btnClicked(id: Int) {
        Log.d(TAG, "btnCliced $id + ${R.id.price_calculator_btn}")

        when(id) {
            R.id.price_calculator_btn -> actCallback?.onBtnClicked(PriceCalculatorActivity::class.java)
            R.id.other_calculator_btn -> Toast.makeText(context, "On going :)", Toast.LENGTH_SHORT).show()
        }
    }

}
//PriceCalculatorActivity::class.java