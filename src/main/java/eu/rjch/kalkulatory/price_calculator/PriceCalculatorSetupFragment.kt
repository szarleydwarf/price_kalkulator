package eu.rjch.kalkulatory.price_calculator

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import eu.rjch.kalkulatory.MainActivity
import eu.rjch.kalkulatory.R
import eu.rjch.kalkulatory.activities.PriceCalculatorActivity
import eu.rjch.kalkulatory.rjutil.AdsHandler
import eu.rjch.kalkulatory.rjutil.AnimationManager
import eu.rjch.kalkulatory.rjutil.EmailHandler
import eu.rjch.kalkulatory.ui.main.MainFragment
import eu.rjch.kalkulatory.ui.main.MenuFragment
import kotlinx.android.synthetic.main.price_calc_setup_frag.view.*

class PriceCalculatorSetupFragment : Fragment(){

    companion object {
        fun newInstance() = PriceCalculatorSetupFragment()
    }
    var actCallback : btnListener? = null
    interface  btnListener {
        fun switchFragment(frag: Fragment)
        fun switchActivity(act : Class<*>)
        fun sendEmail()
        fun switchFragment(containerID: Int, frag: Fragment, tag: String)
    }

    private val TAG = "PRICE_C_SETUP_FR"

    override fun onAttach(context: Context) {
        super.onAttach(context)
        try {
            actCallback = context as btnListener
        } catch (e:ClassCastException) {
            Log.d(TAG, "Error with class cast: ${e.message}")
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        var v = inflater.inflate(R.layout.price_calc_setup_frag, container, false)

        setBtnListeners(v)
        return v
    }

    private fun setBtnListeners(v: View) {
        v.btn_price_calculator_values.setOnClickListener { btnClicked(v, R.id.btn_price_calculator_values) }
        v.btn_price_calculator_appearance.setOnClickListener {btnClicked(v, R.id.btn_price_calculator_appearance) }
        v.btn_eula.setOnClickListener { btnClicked(v, R.id.btn_eula) }
        v.btn_contact.setOnClickListener { btnClicked(v, R.id.btn_contact)}
        v.btn_go_back.setOnClickListener { btnClicked(v, R.id.btn_go_back) }
        v.btn_home.setOnClickListener { btnClicked(v, R.id.btn_home) }
    }

    private fun btnClicked(v: View, id: Int) {
        when(id){
            R.id.btn_price_calculator_values -> {
                AnimationManager().didTapButonInterpolate(
                        v.btn_price_calculator_values, context, R.anim.bounce, MainActivity.amp, MainActivity.freq)
                actCallback?.switchFragment(PCValuesFragment.newInstance())
            }
            R.id.btn_price_calculator_appearance -> {
                AnimationManager().didTapButonInterpolate(
                        v.btn_price_calculator_appearance, context, R.anim.bounce, MainActivity.amp, MainActivity.freq)
                actCallback?.switchFragment(PCAppearanceFragment.newInstance())
            }
            R.id.btn_eula -> {
                AnimationManager().didTapButonInterpolate(
                        v.btn_eula, context, R.anim.bounce, MainActivity.amp, MainActivity.freq )
                actCallback?.switchFragment(EulaFragment.newInstance())
            }
            R.id.btn_contact -> {
                AnimationManager().didTapButonInterpolate(
                        v.btn_contact, context, R.anim.bounce, MainActivity.amp, MainActivity.freq)
                actCallback?.sendEmail()
            }
            R.id.btn_go_back -> {
                AnimationManager().didTapButonInterpolate(
                        v.btn_go_back, context, R.anim.bounce, MainActivity.amp, MainActivity.freq)
                actCallback?.switchActivity(PriceCalculatorActivity::class.java)
            }
            R.id.btn_home -> {
                AnimationManager().didTapButonInterpolate(
                        v.btn_home, context, R.anim.bounce, MainActivity.amp, MainActivity.freq)
                actCallback?.switchActivity(MainActivity::class.java)
            }
        }
    }
}