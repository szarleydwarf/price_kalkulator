package eu.rjch.kalkulatory.price_calculator

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
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

    private val TAG = "PRICE_C_SETUP_FR"

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        var v = inflater.inflate(R.layout.pc_appearance_fragment, container, false)

        AdsHandler().getAds(v?.findViewById(R.id.adViewB)!!)

        runApp(v)
        return v
    }

    private fun runApp(v: View) {
        Toast.makeText(context, "Ole !!!", Toast.LENGTH_LONG).show()

        v.btn_go_back.setOnClickListener { AnimationManager().didTapButonInterpolate(
                v.btn_go_back, context, R.anim.bounce, MainActivity.amp, MainActivity.freq)
            switchFragment(PriceCalculatorSetupFragment())
        }

        v.btn_home.setOnClickListener { AnimationManager().didTapButonInterpolate(
                v.btn_home, context, R.anim.bounce, MainActivity.amp, MainActivity.freq)
            switchFragment(MainFragment())
        }
    }

    private fun switchFragment(frag: Fragment) {
        var fragment = frag
        var fragTransaction = activity?.supportFragmentManager?.beginTransaction()
        fragTransaction?.replace(R.id.container_price_calculator, fragment)
        fragTransaction?.addToBackStack(null)
        fragTransaction?.commit()
    }
}