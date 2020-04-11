package eu.rjch.kalkulatory.rjutil


import android.app.Dialog
import android.content.Context
import android.content.DialogInterface
import android.util.Log
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import eu.rjch.kalkulatory.R
import java.lang.IllegalStateException

class UserInteractionHandler : DialogFragment(){
    private val TAG = "UIH"

    fun showDialogScreen(ctx: Context, s: String): Dialog {
//todo try pass activity instead of context
//        alert.show()
        return (ctx?.let{
            val builder = AlertDialog.Builder(ctx, R.style.MyDialogTheme)
            val inflater = requireActivity().layoutInflater
            builder.setView(inflater.inflate(R.layout.alert_dialog_layout, null))
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
        }?:throw IllegalStateException("Activity cannot be null")) as Dialog
    }
}