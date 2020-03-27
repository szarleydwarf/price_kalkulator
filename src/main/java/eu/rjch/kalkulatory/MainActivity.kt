package eu.rjch.kalkulatory

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import eu.rjch.kalkulatory.ui.main.MainFragment
import eu.rjch.kalkulatory.ui.main.MenuFragment

class MainActivity : AppCompatActivity(), MenuFragment.btnListener {
    private val TAG = "MAIN"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, MainFragment.newInstance())
                .commitNow()
        }
    }

    override fun onBtnClicked(act : Class<*>) {
        var i = Intent(this, act)
        Log.d(TAG, "Switching activity Fragment")
        startActivity(i)
    }

}
