package eu.rjch.kalkulatory.ui.main

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.media.MediaPlayer
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import eu.rjch.kalkulatory.R
import eu.rjch.kalkulatory.PriceCalculatorActivity
import eu.rjch.kalkulatory.rjutil.DateHandler
import kotlinx.android.synthetic.main.main_fragment.view.*
import java.lang.ClassCastException

class MainFragment : Fragment() {

    companion object {
        fun newInstance() = MainFragment()
    }

    private val TAG = "MAINFRAGMENT"

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val rv = inflater.inflate(R.layout.main_fragment, container, false)

        Log.d(TAG, ("introplayed " + introPlayedToday()))
        if(introPlayedToday())
            playIntro(rv)
        else
            switchFragment()

        return rv
    }

    private fun introPlayedToday(): Boolean {
        val ot = getSavedDate()//"06_5_2020"
        var today = DateHandler().getToday()
        Log.d(TAG, "Today - $ot - $today => ")
        return (DateHandler().compareTodays(today, ot))
    }

    private fun getSavedDate(): String {
        var pref = context?.getSharedPreferences(getString(R.string.price_calc_pref), Context.MODE_PRIVATE)
        var editor = pref?.edit() as SharedPreferences.Editor
        var todayTag = resources.getString(R.string.todaysDate)
        var ot : String = pref.getString(todayTag, "06_4_2020").toString()

        return ot
    }

    private fun playIntro(rv : View) {
        Log.d(TAG, "playIntro 1")
        val path = "android.resource://"+context?.packageName+"/"+R.raw.intro
        Log.d(TAG, "path $path")
        rv.vv_intro.setVideoURI(Uri.parse(path))
        if(!rv.vv_intro.isPlaying) {
            Log.d(TAG, "isPlaying")

            rv.vv_intro.start()
        }
        Log.d(TAG, "after playing")
        rv.vv_intro.setOnCompletionListener { switchFragment()        }

        rv.skip_btn.setOnClickListener { switchFragment() }
    }

    private fun switchFragment() {
        var menuFragment = MenuFragment()
        var fragTransaction = activity?.supportFragmentManager?.beginTransaction()
        fragTransaction?.replace(R.id.main_act_container, menuFragment)
        fragTransaction?.addToBackStack(null)
        fragTransaction?.commit()
    }

}
