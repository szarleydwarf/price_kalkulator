package eu.rjch.kalkulatory.ui.main

import android.content.Context
import android.content.SharedPreferences
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import eu.rjch.kalkulatory.R
import eu.rjch.kalkulatory.rjutil.DateHandler
import kotlinx.android.synthetic.main.main_fragment.view.*

class MainFragment : Fragment() {

    companion object {
        fun newInstance() = MainFragment()
    }

    lateinit var pref : SharedPreferences

    private val TAG = "MAINFRAGMENT"

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val rv = inflater.inflate(R.layout.main_fragment, container, false)
        pref = context?.getSharedPreferences(getString(R.string.price_calc_pref), Context.MODE_PRIVATE) as SharedPreferences
        var editor = pref?.edit() as SharedPreferences.Editor
        Log.d(TAG, ("introplayed " + introPlayedToday()) + " / " + skippIntro(editor))
        if(!skippIntro(editor) || !introPlayedToday()) {
            playIntro(rv)
            editor.putString(resources.getString(R.string.todaysDate), DateHandler().getToday())
            editor.commit()
        } else {
            switchFragment()
        }
        return rv
    }

    private fun skippIntro(editor: SharedPreferences.Editor): Boolean {
        var skipped = pref.getInt(R.string.skiped_num.toString(), 0)
        if(skipped <= resources.getString(R.string.max_intro_skips).toInt()) {
            editor.putInt(R.string.skiped_num.toString(), ++skipped)
            editor.commit()
            return true
        }
        editor.putInt(R.string.skiped_num.toString(), 0)
        editor.commit()
        return false
    }

    private fun introPlayedToday(): Boolean {
        val ot = getSavedDate()//"06_5_2020"
        var today = DateHandler().getToday()
        return (DateHandler().compareTodays(today, ot))
    }

    private fun getSavedDate(): String {
        var todayTag = resources.getString(R.string.todaysDate)
        var ot : String = pref?.getString(todayTag, "06_4_2020").toString()

        return ot
    }

    private fun playIntro(rv : View) {
        val path = "android.resource://"+context?.packageName+"/"+R.raw.intro
        rv.vv_intro.setVideoURI(Uri.parse(path))
        if(!rv.vv_intro.isPlaying) {
            Log.d(TAG, "isPlaying")
            rv.vv_intro.start()
        }
        rv.vv_intro.setOnCompletionListener { switchFragment()        }

        rv.skip_btn.setOnClickListener { switchFragment() }
    }

    private fun switchFragment() {
        var menuFragment = MenuFragment()
        var fragTransaction = activity?.supportFragmentManager?.beginTransaction()
        fragTransaction?.replace(R.id.main_fragment, menuFragment)
        fragTransaction?.addToBackStack(null)
        fragTransaction?.commit()
    }

}
