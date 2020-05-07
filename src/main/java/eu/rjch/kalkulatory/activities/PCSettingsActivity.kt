package eu.rjch.kalkulatory.activities

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import eu.rjch.kalkulatory.R
import eu.rjch.kalkulatory.price_calculator.EulaFragment
import eu.rjch.kalkulatory.price_calculator.PCAppearanceFragment
import eu.rjch.kalkulatory.price_calculator.PCValuesFragment
import eu.rjch.kalkulatory.price_calculator.PriceCalculatorSetupFragment
import eu.rjch.kalkulatory.rjutil.AdsHandler
import eu.rjch.kalkulatory.rjutil.EmailHandler

class PCSettingsActivity : AppCompatActivity(),
        PriceCalculatorSetupFragment.btnListener,
        EulaFragment.btnListener,
        PCValuesFragment.btnListener,
        PCAppearanceFragment.btnListener{

    private val TAG = "PCSETACT"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val v = R.layout.pc_settings_activity_layout
        setContentView(v)

        if(savedInstanceState == null){
            switchFragment(R.id.g_ads_container, AdsHandler.newInstance(),
                    getString(R.string.googl_ads_fragment))
            switchFragment(R.id.pc_settings_container, PriceCalculatorSetupFragment.newInstance(),
                    getString(R.string.pc_settings_container_tag))
        }
    }

    override fun switchFragment(containerID: Int, frag: Fragment, tag: String) {
        supportFragmentManager?.beginTransaction()
                .replace(containerID, frag, tag)
                .addToBackStack(tag)
                .commit()
    }

    override fun switchFragment(frag: Fragment) {
        supportFragmentManager?.beginTransaction()
                .replace(R.id.pc_settings_container, frag)
                .addToBackStack(null)
                .commit()
    }

    override fun switchActivity(act: Class<*>) {
        var i = Intent(this, act)
        startActivity(i)
    }

    override fun sendEmail() {
        val subject = resources.getString(R.string.email_subject)
        val eh = EmailHandler()
        startActivity(Intent.createChooser(eh.sendEmail(subject, resources),
                getString(R.string.choose_email_option)))
    }
}