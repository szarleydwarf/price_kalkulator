package eu.rjch.kalkulatorcen.activities;
import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import eu.rjch.kalkulatorcen.R;

public class AppSetup extends Activity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
			setContentView(R.layout.app_layout);
		}else {
			setContentView(R.layout.app_layout_old);
			
		}
		
		
		init();
	}
	
	private void init() {
		Intent i = new Intent(this, TheApp.class);
		startActivity(i);
		finish();
		//todo
		/* design settings Window
		*  think what options to allow to modify
		*  save to file to be used from TheApp class
		*  or
		*  pass them ?
		* */
	}
}
