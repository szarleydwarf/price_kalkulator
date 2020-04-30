package eu.rjch.kalkulatory.price_calculator

import android.app.AlertDialog
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
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
import eu.rjch.kalkulatory.rjutil.AnimationManager
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
//    lateinit var pref : SharedPreferences
    var obj : PC_ObjectToSave? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        var v = inflater.inflate(R.layout.pc_appearance_fragment, container, false)

//        AdsHandler().getAds(v?.findViewById(R.id.adViewB)!!)

        runApp(v)

        return v
    }

    private fun runApp(v: View) {
//        pref = context?.getSharedPreferences(getString(R.string.price_calc_pref), Context.MODE_PRIVATE) as SharedPreferences
        obj = PC_ObjectToSave()
        obj?.loadSPAppearance(context!!)

        initFields(v)
        initButtons(v)
        setListeners(v)
    }

    private fun initFields(v: View) {
        v.chkb_profit_field.isChecked = obj!!.profit_field
        profitChecked = obj!!.profit_field
        v.chkb_tax_field.isChecked = obj!!.tax_field
        taxChecked = obj!!.tax_field
        v.chkb_extra_costs_field.isChecked = obj!!.extra_cost_field
        extraCostChecked = obj!!.extra_cost_field
        extraCostNumber = obj!!.number_of_extras
        when(obj!!.number_of_extras) {
            1 -> v.rd_gr_no_of_extra_costs.check(R.id.rbtn_one_extra_cost)
            2 -> v.rd_gr_no_of_extra_costs.check(R.id.rbtn_two_extra_costs)
            3 -> v.rd_gr_no_of_extra_costs.check(R.id.rbtn_three_extra_costs)
            4 -> v.rd_gr_no_of_extra_costs.check(R.id.rbtn_four_extra_costs)
            5 -> v.rd_gr_no_of_extra_costs.check(R.id.rbtn_five_extra_costs)
            0 -> Toast.makeText(context, "Nothing selected", Toast.LENGTH_SHORT).show()
        }

    }

    private fun saveVars() {
//        obj?.profit_field = profitChecked
//        obj?.tax_field = taxChecked
//        obj?.extra_cost_field = extraCostChecked
//        obj?.number_of_extras = extraCostNumber
        obj?.printElementinLog("3. Log.d ")

        obj?.saveSPAppearance(context!!)
    }

    private fun compareSavedVars() : Boolean {
        var changeFound = false
        if(obj!!.profit_field != profitChecked) {
            obj?.profit_field = profitChecked
            changeFound = true
        }
        if(obj!!.tax_field != taxChecked) {
            obj?.tax_field = taxChecked
            changeFound = true
        }
        if(obj!!.extra_cost_field != extraCostChecked) {
            obj?.extra_cost_field = extraCostChecked
            changeFound = true
        }
        if(extraCostChecked && obj?.number_of_extras != extraCostNumber) {
            obj?.number_of_extras = extraCostNumber
            changeFound = true
        }
        return changeFound
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
            if(compareSavedVars()) {
                doSave(v, true)
            } else {
                switchFragment()
            }
        }

        v.btn_home.setOnClickListener { AnimationManager().didTapButonInterpolate(
                v.btn_home, context, R.anim.bounce, MainActivity.amp, MainActivity.freq)
            if(compareSavedVars()) {
                doSave(v, false)
            } else {
                switchActivity()
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
                            if(backBtnPressed) switchFragment()
                            else switchActivity()
                        })
                .setNegativeButton(getString(R.string.dont_save),
                        DialogInterface.OnClickListener { dialog, which ->
                            dialog.dismiss()
                            if(backBtnPressed) switchFragment()
                            else switchActivity()
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

    private fun switchActivity(){
        startActivity(Intent(context, MainActivity::class.java))
    }

    private fun switchFragment() {
        var fragment = PriceCalculatorSetupFragment()
        var fragTransaction = activity?.supportFragmentManager?.beginTransaction()
        fragTransaction?.replace(R.id.pc_settings_container, fragment)
        fragTransaction?.addToBackStack(null)
        fragTransaction?.commit()
    }

}