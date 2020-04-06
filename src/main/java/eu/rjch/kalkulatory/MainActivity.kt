package eu.rjch.kalkulatory

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.google.android.gms.ads.MobileAds
import eu.rjch.kalkulatory.rjutil.DateHandler
import eu.rjch.kalkulatory.ui.main.MainFragment
import eu.rjch.kalkulatory.ui.main.MenuFragment
import java.text.SimpleDateFormat
import java.util.*

class MainActivity : AppCompatActivity(), MenuFragment.BtnListener {
    private val TAG = "MAIN"

    companion object{
        val amp = 0.2
        val freq = 20.0
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)

//        initialize google ads
        MobileAds.initialize(this, R.string.app_id_google_adds.toString())
//        MobileAds.initialize(this){} // other way


        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.main_act_container, MainFragment.newInstance())
                .commitNow()
        }
    }


    override fun onBtnClicked(act : Class<*>) {
        var i = Intent(this, act)
        startActivity(i)
    }

}
