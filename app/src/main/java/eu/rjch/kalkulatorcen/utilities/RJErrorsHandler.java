package eu.rjch.kalkulatorcen.utilities;
import android.content.Intent;

public class RJErrorsHandler {
	public RJErrorsHandler() {
	
	}
	
	public String getSystemInfo() {
		String s="Debug-infos:";
		s += "\n OS Version: " + System.getProperty("os.version") + "(" + android.os.Build.VERSION.INCREMENTAL + ")";
		s += "\n OS API Level: " + android.os.Build.VERSION.SDK_INT;
		s += "\n Device: " + android.os.Build.DEVICE;
		s += "\n Model (and Product): " + android.os.Build.MODEL + " ("+ android.os.Build.PRODUCT + ")";
		return s;
	}
	
	public Intent sendEmail(String subject, String message) {
		String msg = this.getSystemInfo();
		String[] reciepent = {"r.j.chomik@gmail.com"};
		
		msg += "\n\n"+ message;
		
		Intent i = new Intent(Intent.ACTION_SEND);
		i.putExtra(Intent.EXTRA_EMAIL, reciepent);
		i.putExtra(Intent.EXTRA_SUBJECT, subject);
		i.putExtra(Intent.EXTRA_TEXT, msg);
		
		i.setType("message/rfc822");
		return i;
	}
}
