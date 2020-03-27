package eu.rjch.kalkulatory.ui.main

import android.content.Context
import android.content.Intent
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
import kotlinx.android.synthetic.main.main_fragment.view.*
import java.lang.ClassCastException

class MainFragment : Fragment() {

    companion object {
        fun newInstance() = MainFragment()
    }
//    var actCallback : MainFragment.videoListener? = null
//    interface videoListener {
//        fun onVideoEnd()
//    }

    private val TAG = "MAINFRAGMENT"

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val rv = inflater.inflate(R.layout.main_fragment, container, false)

        val path = "android.resource://"+context?.packageName+"/"+R.raw.intro
        rv.vv_intro.setVideoURI(Uri.parse(path))
        Log.d(TAG, "1 Running Main Fragment")
        if(!rv.vv_intro.isPlaying)
            rv.vv_intro.start()


        rv.vv_intro.setOnCompletionListener { switchFragment()        }

        rv.skip_btn.setOnClickListener { switchFragment() }

        return rv
    }

    private fun switchFragment() {
        var menuFragment = MenuFragment()
        var fragTransaction = activity?.supportFragmentManager?.beginTransaction()
        fragTransaction?.replace(R.id.container, menuFragment)
        fragTransaction?.addToBackStack(null)
        fragTransaction?.commit()
    }

}
