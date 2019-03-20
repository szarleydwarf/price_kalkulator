package eu.rjch.kalkulatorcen.activities;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
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
		SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPref", MODE_PRIVATE);
		SharedPreferences.Editor editor = pref.edit();

		EditText vatET = findViewById(R.id.vat_settings_et);
		String vatSET = vatET.getText().toString();
		editor.putString(getResources().getString(R.string.chk_vat), vatSET);
		
		editor.commit();
		//todo
		Button save = findViewById(R.id.save_btn);
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
