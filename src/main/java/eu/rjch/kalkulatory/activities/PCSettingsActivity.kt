package eu.rjch.kalkulatory.activities

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import eu.rjch.kalkulatory.R
import eu.rjch.kalkulatory.price_calculator.EulaFragment
import eu.rjch.kalkulatory.price_calculator.PriceCalculatorSetupFragment
import eu.rjch.kalkulatory.rjutil.EmailHandler

class PCSettingsActivity : AppCompatActivity(),
        PriceCalculatorSetupFragment.btnListener,
        EulaFragment.btnListener {

    private val TAG = "PCSETACT"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.pc_settings_activity_layout)

        if(savedInstanceState == null){
            supportFragmentManager.beginTransaction()
                    .replace(R.id.pc_settings_container,
                            PriceCalculatorSetupFragment.newInstance()
                    )
                    .commit()
        }
    }

    override fun switchFragment(frag: Fragment) {
        Log.d(TAG, "SWITCH FRAGMENT")
        var fragment = frag
        var fragTransaction = supportFragmentManager?.beginTransaction()
        fragTransaction?.replace(R.id.pc_settings_container, fragment)
        fragTransaction?.addToBackStack(null)
        fragTransaction?.commit()
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

    override fun switchFragment() {
        Log.d(TAG, "SWITCH EULA FRAGMENT")

        switchFragment(PriceCalculatorSetupFragment())
    }
}