package eu.rjch.kalkulatory.price_calculator

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import android.widget.Toast
import androidx.core.view.get
import androidx.fragment.app.Fragment
import eu.rjch.kalkulatory.MainActivity
import eu.rjch.kalkulatory.R
import eu.rjch.kalkulatory.rjutil.AdsHandler
import eu.rjch.kalkulatory.rjutil.AnimationManager
import eu.rjch.kalkulatory.ui.main.MainFragment
import eu.rjch.kalkulatory.ui.main.MenuFragment
import kotlinx.android.synthetic.main.pc_appearance_fragment.view.*

class PCAppearanceFragment : Fragment() {
    companion object {
        fun newInstance() = PCAppearanceFragment()
    }

    private var taxChecked: Boolean = false
    private var profitChecked: Boolean = false
    private var extraCostChecked: Boolean = false
    private var extraCostNumber : Int = 0

    private val TAG = "PRICE_C_SETUP_FR"
    lateinit var pref : SharedPreferences

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        var v = inflater.inflate(R.layout.pc_appearance_fragment, container, false)

        AdsHandler().getAds(v?.findViewById(R.id.adViewB)!!)


        runApp(v)

        return v
    }

    private fun runApp(v: View) {
        pref = context?.getSharedPreferences(getString(R.string.price_calc_pref), Context.MODE_PRIVATE) as SharedPreferences
        var editor = pref?.edit() as SharedPreferences.Editor

        initButtons(v)
        setListeners(v)
    }

    private fun setListeners(v: View) {
        v.chkb_profit_field.setOnClickListener { profitChecked = v.chkb_profit_field.isChecked }
        v.chkb_tax_field.setOnClickListener { taxChecked = v.chkb_tax_field.isChecked }
        v.chkb_extra_costs_field.setOnClickListener { extraCostChecked = v.chkb_extra_costs_field.isChecked }
        v.rd_gr_no_of_extra_costs.setOnCheckedChangeListener { group, checkedId ->
            extraCostNumber =  group.findViewById<RadioButton>(checkedId).text.toString().toInt()
        }
    }

    private fun initButtons(v: View) {

        v.btn_go_back.setOnClickListener { AnimationManager().didTapButonInterpolate(
                v.btn_go_back, context, R.anim.bounce, MainActivity.amp, MainActivity.freq)
            // todo ask for saving the changes, check for changes?
            if(compareSavedVars()) {
                if(doSave()) {
                    saveVars()
                }
            }
            Toast.makeText(context, "back btn !!! $profitChecked / $taxChecked / $extraCostChecked / $extraCostNumber", Toast.LENGTH_SHORT).show()
            switchFragment(PriceCalculatorSetupFragment())
        }

        v.btn_home.setOnClickListener { AnimationManager().didTapButonInterpolate(
                v.btn_home, context, R.anim.bounce, MainActivity.amp, MainActivity.freq)
            switchFragment(MainFragment())
        }
    }

    private fun saveVars() {
//todo save int file or shared prefs
    }

    private fun doSave(): Boolean {
//todo ask for saving changes return true if yes
        return false
    }

    private fun compareSavedVars() : Boolean {
//todo load vars and compare them return true if all the same
        return false
    }

    private fun switchFragment(frag: Fragment) {
        var fragment = frag
        var fragTransaction = activity?.supportFragmentManager?.beginTransaction()
        fragTransaction?.replace(R.id.container_price_calculator, fragment)
        fragTransaction?.addToBackStack(null)
        fragTransaction?.commit()
    }
}