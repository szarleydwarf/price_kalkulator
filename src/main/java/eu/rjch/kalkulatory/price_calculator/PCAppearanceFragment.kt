package eu.rjch.kalkulatory.price_calculator

import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import android.widget.Toast
import androidx.core.content.res.ResourcesCompat
import androidx.fragment.app.Fragment
import eu.rjch.kalkulatory.MainActivity
import eu.rjch.kalkulatory.R
import eu.rjch.kalkulatory.oops.PC_ObjectToSave
import eu.rjch.kalkulatory.rjutil.AdsHandler
import eu.rjch.kalkulatory.rjutil.AnimationManager
import eu.rjch.kalkulatory.rjutil.CustomAlertDialogFragment
import eu.rjch.kalkulatory.ui.main.MainFragment
import kotlinx.android.synthetic.main.alert_dialog_layout.view.*
import kotlinx.android.synthetic.main.pc_appearance_fragment.view.*

class PCAppearanceFragment : Fragment() {
    companion object {
        fun newInstance() = PCAppearanceFragment()
    }

    private var taxChecked: Boolean = false
    private var profitChecked: Boolean = false
    private var extraCostChecked: Boolean = false
    private var extraCostNumber : Int = 0

    private val TAG = "PCAF"
    lateinit var pref : SharedPreferences
    var obj = PC_ObjectToSave()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        var v = inflater.inflate(R.layout.pc_appearance_fragment, container, false)

        AdsHandler().getAds(v?.findViewById(R.id.adViewB)!!)


        runApp(v)

        return v
    }

    private fun runApp(v: View) {
        pref = context?.getSharedPreferences(getString(R.string.price_calc_pref), Context.MODE_PRIVATE) as SharedPreferences

        obj.printElementinLog("1. Log.d ")
        obj.loadSPAppearance(context!!)
        obj.printElementinLog("2. Log.d ")

        initFields(v)
        initButtons(v)
        setListeners(v)
    }

    private fun initFields(v: View) {
        v.chkb_profit_field.isChecked = obj.profit_field
        v.chkb_tax_field.isChecked = obj.tax_field
        v.chkb_extra_costs_field.isChecked = obj.extra_cost_field
        v.rd_gr_no_of_extra_costs.findViewById<RadioButton>(R.id.rbtn_five_extra_costs).isChecked = true
    }

    private fun saveVars() {
//todo save into file or shared prefs
        obj.profit_field = profitChecked
        obj.tax_field = taxChecked
        obj.extra_cost_field = extraCostChecked
        obj.number_of_extras = extraCostNumber
        obj.printElementinLog("3. Log.d ")

        obj.saveSPAppearance(context!!)
    }

    private fun compareSavedVars() : Boolean {
// todo load vars and compare them return true if all the same #
//  todo think about changing this to fun checking one var at a time as its skips after first true :/


        return true
    }

    private fun setListeners(v: View) {
        v.chkb_profit_field.setOnClickListener { profitChecked = v.chkb_profit_field.isChecked }
        v.chkb_tax_field.setOnClickListener { taxChecked = v.chkb_tax_field.isChecked }
        v.chkb_extra_costs_field.setOnClickListener { extraCostChecked = v.chkb_extra_costs_field.isChecked }
        v.rd_gr_no_of_extra_costs.setOnCheckedChangeListener { group, checkedId ->
            this.extraCostNumber =  group.findViewById<RadioButton>(checkedId).text.toString().toInt()
        }
    }

    private fun initButtons(v: View) {
        v.btn_go_back.setOnClickListener { AnimationManager().didTapButonInterpolate(
                v.btn_go_back, context, R.anim.bounce, MainActivity.amp, MainActivity.freq)
            // todo ask for saving the changes, check for changes?
            val displayAlert = compareSavedVars()
            if(displayAlert) {
                doSave(v, true)
            } else {
                switchFragment(PriceCalculatorSetupFragment())
            }
        }

        v.btn_home.setOnClickListener { AnimationManager().didTapButonInterpolate(
                v.btn_home, context, R.anim.bounce, MainActivity.amp, MainActivity.freq)
            val displayAlert = compareSavedVars()
            if(displayAlert) {
                doSave(v, false)
            } else {
                switchFragment(MainFragment())
            }
        }
    }

    private fun doSave(v:View, backBtnPressed:Boolean){
        val b = AlertDialog.Builder(context, R.style.MyDialogTheme)
        b.setMessage(getString(R.string.save_request_msg))
                .setIcon(R.drawable.kfloppy)
                .setTitle(getString(R.string.save))
                .setPositiveButton(getString(R.string.save),
                        DialogInterface.OnClickListener (){
                            di: DialogInterface, i: Int ->
                            saveVars()
                            if(backBtnPressed) switchFragment(PriceCalculatorSetupFragment())
                            else switchFragment(MainFragment())
                        })
                .setNegativeButton(getString(R.string.dont_save),
                        DialogInterface.OnClickListener { dialog, which ->
                            dialog.dismiss()
                            if(backBtnPressed) switchFragment(PriceCalculatorSetupFragment())
                            else switchFragment(MainFragment())
                        })
        val ad = b.create()
        ad.setOnShowListener {
            var savebtn = ad.getButton(DialogInterface.BUTTON_POSITIVE)
            savebtn.background = ResourcesCompat.getDrawable(resources,
                    R.drawable.skew_btn, null)

            var dontsavebtn = ad.getButton(DialogInterface.BUTTON_NEGATIVE)
            dontsavebtn.background = ResourcesCompat.getDrawable(resources,
                    R.drawable.skew_btn, null)
        }

        ad.show()
    }

    private fun switchFragment(frag: Fragment) {
        var fragment = frag
        var fragTransaction = activity?.supportFragmentManager?.beginTransaction()
        fragTransaction?.replace(R.id.container_price_calculator, fragment)
        fragTransaction?.addToBackStack(null)
        fragTransaction?.commit()
    }

}