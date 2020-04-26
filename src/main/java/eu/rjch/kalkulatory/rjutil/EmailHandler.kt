package eu.rjch.kalkulatory.rjutil

import android.content.Intent
import android.content.res.Resources
import android.net.Uri
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
        var recepient = resources.getString(R.string.reciepent_email)
        var body:String = ""
        if(error_email)
            body += getSystemInfo()
        body += "\n\n" + resources.getString(R.string.email_msg)
        var uri = Uri.parse("mailto:"+recepient)
                .buildUpon()
                .appendQueryParameter("subject", subject)
                .appendQueryParameter("body", body)
                .build()
        var i = Intent(Intent.ACTION_SENDTO, uri)

        return i
    }
}