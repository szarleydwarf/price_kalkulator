package eu.rjch.kalkulatory.rjutil

import android.content.Intent
import android.content.res.Resources
import eu.rjch.kalkulatory.R

class EmailHandler {

    fun getSystemInfo():String {
        var s = "Debug-infos:"
        s += "\nOS Version: " + System.getProperty("os.version")
        s += "(" + android.os.Build.VERSION.INCREMENTAL + ")"
        s += "\n OS API Level: " + android.os.Build.VERSION.SDK_INT;
        s += "\n Device: " + android.os.Build.DEVICE;
        s += "\n Model (and Product): " + android.os.Build.MODEL
        s += " ("+ android.os.Build.PRODUCT + ")";

        return s
    }

    fun sendEmail(subject: String, resources: Resources, error_email: Boolean=true) : Intent {
        var msg : String = ""
        var reciepent = arrayOf(resources.getString(R.string.reciepent_email))
        if(error_email)
            msg += getSystemInfo()

        msg += "\n\n" +  resources.getString(R.string.email_msg)

        val i = Intent(Intent.ACTION_SEND)
        i.putExtra(Intent.EXTRA_EMAIL, reciepent)
        i.putExtra(Intent.EXTRA_SUBJECT, subject)
        i.putExtra(Intent.EXTRA_TEXT, msg)

        i.setType("message/rfc822")
        return i
    }
}