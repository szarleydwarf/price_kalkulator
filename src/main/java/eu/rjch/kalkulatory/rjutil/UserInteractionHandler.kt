package eu.rjch.kalkulatory.rjutil


import android.content.Context
import android.content.DialogInterface
import android.util.Log
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import eu.rjch.kalkulatory.R

class UserInteractionHandler : DialogFragment(){
    private val TAG = "UIH"

    fun showDialogScreen(ctx: Context, s: String) {
        val builder = AlertDialog.Builder(ctx, R.style.MyDialogTheme)
        builder.setTitle("SAVE")
        builder.setMessage(s)
        builder.setPositiveButton("SAVE",
                DialogInterface.OnClickListener{
                    dialog, id ->
                    Log.d(TAG, "SAVE $id / ${dialog.toString()}")
                })

        builder.setNegativeButton("NO",
                DialogInterface.OnClickListener{
                    dialog, id ->
                    Log.d(TAG, "NO $id")
                })


        val alert = builder.create()
        alert.show()
    }
}