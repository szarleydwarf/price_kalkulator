package eu.rjch.kalkulatory.rjutil


import android.app.Dialog
import android.content.DialogInterface
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import eu.rjch.kalkulatory.R

class CustomAlertDialogFragment : DialogFragment() {
    private val TAG = "UIH"

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val v = layoutInflater?.inflate(R.layout.alert_dialog_layout, null)
        return AlertDialog.Builder(activity!!)
                .setView(v)
                .setTitle("SAVE")
                .setMessage(getString(R.string.save_request_msg))
                .setPositiveButton("SAVE",
                        DialogInterface.OnClickListener{
                            dialog, id ->
                            Log.d(TAG, "SAVE $id / ${dialog.toString()}")
                        }).setNegativeButton("NO",
                        DialogInterface.OnClickListener{
                            dialog, id ->
                            Log.d(TAG, "NO $id")
                        }).create()
    }
}