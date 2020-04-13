package eu.rjch.kalkulatory.rjutil


import android.app.Activity
import android.app.Dialog
import android.content.Context
import android.content.DialogInterface
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import eu.rjch.kalkulatory.MainActivity
import eu.rjch.kalkulatory.R
import kotlinx.android.synthetic.main.alert_dialog_layout.*
import kotlinx.android.synthetic.main.alert_dialog_layout.view.*
import kotlinx.android.synthetic.main.pc_appearance_fragment.view.*

class CustomAlertDialogFragment : DialogFragment() {
    private val TAG = "UIH"


    fun showAlertDialog(act : Activity, ctx: Context) {
        ctx?.let {
            val ad = AlertDialog.Builder(it)
            val v = LayoutInflater.from(ctx).inflate(R.layout.alert_dialog_layout,null)
            ad.setView(v)
            ad.setPositiveButton(R.string.save) { dialog, id ->
                AnimationManager().didTapButonInterpolate(
                        v.btn_confirm, context, R.anim.bounce, MainActivity.amp, MainActivity.freq)
                Log.d(TAG, "SAVE ")

            }
            val ald = ad.create()
            ald.show()
        }
    }
}