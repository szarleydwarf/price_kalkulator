package eu.rjch.kalkulatory.price_calculator

import android.content.Context
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
import kotlinx.android.synthetic.main.eula_frag_layout.view.*
import kotlinx.android.synthetic.main.eula_frag_layout.view.btn_go_back
import kotlinx.android.synthetic.main.price_calc_setup_frag.view.*

class EulaFragment : Fragment() {
    companion object {
        fun newInstance() = EulaFragment()
    }
    var actCallback : btnListener? = null
    interface btnListener{
        fun switchFragment(frag: Fragment)
    }
    private val TAG = "EULA_FRAG"

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
        val v = inflater.inflate(R.layout.eula_frag_layout, container, false)

//        AdsHandler().getAds(v.findViewById(R.id.adViewB)!!)

        v.btn_go_back.setOnClickListener { btnClicked(v) }

        return v
    }

    private fun btnClicked(v:View) {
        Log.d(TAG, "EULA BTN CLICKED")
        AnimationManager().didTapButonInterpolate(
                v.btn_go_back, context, R.anim.bounce, MainActivity.amp, MainActivity.freq)
        actCallback?.switchFragment(PriceCalculatorSetupFragment.newInstance())
    }
}