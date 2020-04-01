package eu.rjch.kalkulatory.price_calculator

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import eu.rjch.kalkulatory.R
import kotlinx.android.synthetic.main.eula_frag_layout.view.*

class EulaFragment : Fragment() {
    companion object {
        fun newInstance() = EulaFragment()
    }
    private val TAG = "EULA_FRAG"

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val v = inflater.inflate(R.layout.eula_frag_layout, container, false)


        return v
    }
}