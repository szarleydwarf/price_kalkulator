package eu.rjch.kalkulatorcen.activities;
import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import eu.rjch.kalkulatorcen.R;

public class AppSetup extends Activity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
			setContentView(R.layout.app_setup_layout);
		}else {
			setContentView(R.layout.app_setup_layout_old);
			
		}
		
		
		init();
	}
	
	private void init() {
		Button save = findViewById(R.id.save_btn);
		//todo
		/* design settings Window
		*  think what options to allow to modify
		*  save to file to be used from TheApp class
		*  or
		*  pass them ?
		* */
		save.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				Intent i = new Intent(view.getContext(), TheApp.class);
				startActivity(i);
				finish();
			}
		});
	}
}
