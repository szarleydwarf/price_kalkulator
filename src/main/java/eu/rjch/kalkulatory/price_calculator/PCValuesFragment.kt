package eu.rjch.kalkulatory.price_calculator

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import eu.rjch.kalkulatory.MainActivity
import eu.rjch.kalkulatory.R
import eu.rjch.kalkulatory.oops.PC_ObjectToSave
import eu.rjch.kalkulatory.rjutil.AdsHandler
import eu.rjch.kalkulatory.rjutil.AnimationManager
import eu.rjch.kalkulatory.ui.main.MainFragment
import eu.rjch.kalkulatory.ui.main.MenuFragment
import kotlinx.android.synthetic.main.pc_appearance_fragment.view.*
import kotlinx.android.synthetic.main.pc_values_frag_layout.view.*
import kotlinx.android.synthetic.main.pc_values_frag_layout.view.btn_go_back
import kotlinx.android.synthetic.main.pc_values_frag_layout.view.btn_home

class PCValuesFragment : Fragment() {

    companion object{
        fun newInstance() = PCValuesFragment()
    }
    var actCallback : btnListener? = null
    interface btnListener{
        fun switchFragment(frag: Fragment)
        fun switchActivity(act : Class<*>)
    }

    private val TAG = "PC_VAL_FR"
    var obj : PC_ObjectToSave? = null
    var profitCheckBox:Boolean = false
    var taxCheckBox:Boolean = false
    var extraCostCheckBox:Boolean = false
    var numberOfExtraCostFragments = 0

    override fun onAttach(context: Context) {
        super.onAttach(context)
        try{
            actCallback = context as btnListener
        } catch (e : ClassCastException) {
            Log.d(TAG, "Error with class cast: ${e.message}")
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        var v = inflater.inflate(R.layout.pc_values_frag_layout, container, false)

        initButtons(v)
        initVars()
        runApp(v)
        return v
    }

    private fun runApp(v: View) {
//        check if there is any of the fragments checked/true from appearance sharedPrefs
        if(isAnyModuleVisible()) {
            Log.d(TAG, "yes")
        } else {
            Log.d(TAG, "no")

        }
//        else display / add proper fragments
    }

    private fun isAnyModuleVisible(): Boolean {
        obj?.printElementinLog(TAG)


        return false
    }

    private fun initVars() {
        obj = PC_ObjectToSave()
        obj?.loadSPAppearance(context!!)
    }

    private fun initButtons(v: View) {
        v.btn_go_back.setOnClickListener { btnClicked(v, R.id.btn_go_back) }
        v.btn_home.setOnClickListener { btnClicked(v, R.id.btn_home)}
    }

    private fun btnClicked(v: View, id: Int) {
        when (id) {
            R.id.btn_go_back->{
                AnimationManager().didTapButonInterpolate(
                        v.btn_go_back, context, R.anim.bounce, MainActivity.amp, MainActivity.freq)
                actCallback?.switchFragment(PriceCalculatorSetupFragment.newInstance())
            }
            R.id.btn_home -> {
                AnimationManager().didTapButonInterpolate(
                        v.btn_home, context, R.anim.bounce, MainActivity.amp, MainActivity.freq)
                actCallback?.switchActivity(MainActivity::class.java)
            }
        }
    }

}