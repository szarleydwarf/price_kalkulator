package eu.rjch.kalkulatory.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import eu.rjch.kalkulatory.R
import eu.rjch.kalkulatory.price_calculator.PriceCalculatorSetupFragment

class PCSettingsActivity : AppCompatActivity() {

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
}