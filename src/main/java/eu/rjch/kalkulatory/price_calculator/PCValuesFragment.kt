package eu.rjch.kalkulatory.price_calculator

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import eu.rjch.kalkulatory.MainActivity
import eu.rjch.kalkulatory.R
import eu.rjch.kalkulatory.rjutil.AdsHandler
import eu.rjch.kalkulatory.rjutil.AnimationManager
import eu.rjch.kalkulatory.ui.main.MainFragment
import eu.rjch.kalkulatory.ui.main.MenuFragment
import kotlinx.android.synthetic.main.pc_appearance_fragment.view.*
import kotlinx.android.synthetic.main.pc_values_frag_layout.view.*
import kotlinx.android.synthetic.main.pc_values_frag_layout.view.btn_go_back
import kotlinx.android.synthetic.main.pc_values_frag_layout.view.btn_home

class PCValuesFragment : Fragment() {

    private val TAG = "PRICE_C_SETUP_FR"
    var profitCheckBox:Boolean = false
    var taxCheckBox:Boolean = false
    var extraCostCheckBox:Boolean = false
    var numberOfExtraCostFragments = 0

    lateinit var pref : SharedPreferences

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        var v = inflater.inflate(R.layout.pc_values_frag_layout, container, false)

        AdsHandler().getAds(v?.findViewById(R.id.adViewB)!!)

        initButtons(v)
        initVars()
        runApp(v)
        return v
    }

    private fun runApp(v: View) {
//        if none of checkboxes is selected show default message
        if(!v.chkb_profit_field.isChecked) {
            Log.d(TAG, "box not checked")
        } else {
            Log.d(TAG, "box checked")

        }
//        else display / add proper fragments
checkSavedVars()
    }

    private fun checkSavedVars() {
        var tProfit = pref.getInt(getString(R.string.profit), 20)
        var tTax = pref.getFloat(getString(R.string.tax), 21f)
        var tExtras = pref.getInt(getString(R.string.extra_costs), 1)

    }

    private fun initVars() {
        pref = context?.getSharedPreferences(getString(R.string.price_calc_pref), Context.MODE_PRIVATE) as SharedPreferences
        var editor = pref?.edit() as SharedPreferences.Editor

    }

    private fun initButtons(v: View) {
        v.btn_go_back.setOnClickListener { AnimationManager().didTapButonInterpolate(
                v.btn_go_back, context, R.anim.bounce, MainActivity.amp, MainActivity.freq)
            switchFragment(PriceCalculatorFragment())
        }

        v.btn_home.setOnClickListener { AnimationManager().didTapButonInterpolate(
                v.btn_home, context, R.anim.bounce, MainActivity.amp, MainActivity.freq)
            switchFragment(MainFragment())
        }
    }

    private fun switchFragment(frag : Fragment) {
        var fragment = frag
        var fragTransaction = activity?.supportFragmentManager?.beginTransaction()
        fragTransaction?.replace(R.id.container_price_calculator, fragment)
        fragTransaction?.addToBackStack(null)
        fragTransaction?.commit()
    }
}