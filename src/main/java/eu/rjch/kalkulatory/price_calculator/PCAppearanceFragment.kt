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
            val displayAlert = compareSavedVars()
            Toast.makeText(context, "back btn !!! $profitChecked / $taxChecked / $extraCostChecked " +
                    "/ $extraCostNumber", Toast.LENGTH_SHORT).show()
            if(displayAlert) {
                doSave(v, true)
            } else {
                switchFragment(PriceCalculatorSetupFragment())
            }
        }

        v.btn_home.setOnClickListener { AnimationManager().didTapButonInterpolate(
                v.btn_home, context, R.anim.bounce, MainActivity.amp, MainActivity.freq)
            val displayAlert = compareSavedVars()
            Toast.makeText(context, "back btn !!! $profitChecked / $taxChecked / $extraCostChecked " +
                    "/ $extraCostNumber", Toast.LENGTH_SHORT).show()
            if(displayAlert) {
                doSave(v, false)
            } else {
                switchFragment(MainFragment())
            }
        }
    }

    private fun saveVars() {
//todo save into file or shared prefs
        Log.d(TAG, "SAVING... ")
        var editor = pref?.edit() as SharedPreferences.Editor
        editor.putBoolean(getString(R.string.show_profit), profitChecked)
        editor.putBoolean(getString(R.string.show_tax), taxChecked)
        editor.putBoolean(getString(R.string.show_extra_costs), extraCostChecked)
        editor.putInt(getString(R.string.no_of_extras), extraCostNumber)
    }

    private fun doSave(v:View, backBtnPressed:Boolean){
        val b = AlertDialog.Builder(context, R.style.MyDialogTheme)
        b.setMessage(getString(R.string.save_request_msg))
                .setIcon(R.drawable.kfloppy)
                .setTitle(getString(R.string.save))
                .setPositiveButton(getString(R.string.save),
                        DialogInterface.OnClickListener (){
                            di: DialogInterface, i: Int ->
                            Log.d(TAG, "SAVE ")
                            saveVars()
                            if(backBtnPressed) switchFragment(PriceCalculatorSetupFragment())
                            else switchFragment(MainFragment())
                        })
                .setNegativeButton(getString(R.string.dont_save),
                DialogInterface.OnClickListener { dialog, which ->
                    Log.d(TAG, "Dontsave $which ")
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

    private fun compareSavedVars() : Boolean {
//todo load vars and compare them return true if all the same
        var obj = PC_ObjectToSave()
        obj.loadSharedPrefs(context!!)

        var tProfit = pref.getBoolean(getString(R.string.show_profit), false)
        var tTax = pref.getBoolean(getString(R.string.show_tax), false)
        var tExtras = pref.getBoolean(getString(R.string.show_extra_costs), false)
        var tNoExtraCosts = pref.getInt(getString(R.string.no_of_extras), 0)
        if (tProfit != profitChecked) return true
        if (tTax != taxChecked) return true
        if(tExtras != extraCostChecked){
            extraCostNumber = tNoExtraCosts
            return true
        }

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