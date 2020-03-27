package eu.rjch.kalkulatory

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import eu.rjch.kalkulatory.price_calculator.PriceCalculatorFragment

class PriceCalculatorActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.price_calculator_activity)

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.container_price_calculator,
                    PriceCalculatorFragment.newInstance()
                )
                .commitNow()
        }
    }
}