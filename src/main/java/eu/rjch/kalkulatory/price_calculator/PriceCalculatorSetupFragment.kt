package eu.rjch.kalkulatory.price_calculator

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import eu.rjch.kalkulatory.MainActivity
import eu.rjch.kalkulatory.R
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

    private val TAG = "PRICE_C_SETUP_FR"

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        var v = inflater.inflate(R.layout.price_calc_setup_frag, container, false)

        AdsHandler().getAds(v?.findViewById(R.id.adViewB)!!)

        runApp(v)
        return v
    }


    private fun runApp(v: View) {

        v.btn_price_calculator_values.setOnClickListener { AnimationManager().didTapButonInterpolate(
                v.btn_price_calculator_values, context, R.anim.bounce, MainActivity.amp, MainActivity.freq)
            switchFragment(PCValuesFragment())
        }

        v.btn_price_calculator_appearance.setOnClickListener {
            AnimationManager().didTapButonInterpolate(
                v.btn_price_calculator_appearance, context, R.anim.bounce, MainActivity.amp, MainActivity.freq)
            switchFragment(PCAppearanceFragment())
        }

        v.btn_eula.setOnClickListener {
            AnimationManager().didTapButonInterpolate(
                v.btn_eula, context, R.anim.bounce, MainActivity.amp, MainActivity.freq )
            switchFragment(EulaFragment())
        }

        v.btn_contact.setOnClickListener { AnimationManager().didTapButonInterpolate(
                v.btn_contact, context, R.anim.bounce, MainActivity.amp, MainActivity.freq)
            sendEmail()
        }

        v.btn_go_back.setOnClickListener { AnimationManager().didTapButonInterpolate(
                v.btn_go_back, context, R.anim.bounce, MainActivity.amp, MainActivity.freq)
            switchFragment(PriceCalculatorFragment())
        }

        v.btn_home.setOnClickListener { AnimationManager().didTapButonInterpolate(
                v.btn_home, context, R.anim.bounce, MainActivity.amp, MainActivity.freq)
            switchFragment(MainFragment())
        }

    }

    private fun sendEmail() {
        val subject = resources.getString(R.string.email_subject)
        val eh = EmailHandler()
        startActivity(Intent.createChooser(eh.sendEmail(subject, resources),
                R.string.choose_email_option.toString()))
    }

    private fun switchFragment(frag : Fragment) {
        var fragment = frag
        var fragTransaction = activity?.supportFragmentManager?.beginTransaction()
        fragTransaction?.replace(R.id.container_price_calculator, fragment)
        fragTransaction?.addToBackStack(null)
        fragTransaction?.commit()
    }
}