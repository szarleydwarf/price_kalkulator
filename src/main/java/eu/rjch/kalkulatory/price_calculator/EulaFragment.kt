package eu.rjch.kalkulatory.price_calculator

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import eu.rjch.kalkulatory.MainActivity
import eu.rjch.kalkulatory.R
import eu.rjch.kalkulatory.rjutil.AdsHandler
import eu.rjch.kalkulatory.rjutil.AnimationManager
import kotlinx.android.synthetic.main.eula_frag_layout.view.*
import kotlinx.android.synthetic.main.eula_frag_layout.view.btn_go_back
import kotlinx.android.synthetic.main.price_calc_setup_frag.view.*

class EulaFragment : Fragment() {
    companion object {
        fun newInstance() = EulaFragment()
    }
    private val TAG = "EULA_FRAG"

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val v = inflater.inflate(R.layout.eula_frag_layout, container, false)

        AdsHandler().getAds(v.findViewById(R.id.adViewB)!!)

        v.btn_go_back.setOnClickListener { AnimationManager().didTapButonInterpolate(
                v.btn_go_back, context, R.anim.bounce, MainActivity.amp, MainActivity.freq)
            switchFragment()

        }
        return v
    }

    private fun switchFragment() {
        var fragment = PriceCalculatorSetupFragment()
        var fragTransaction = activity?.supportFragmentManager?.beginTransaction()
        fragTransaction?.replace(R.id.pc_settings_container, fragment)
        fragTransaction?.addToBackStack(null)
        fragTransaction?.commit()
    }
}