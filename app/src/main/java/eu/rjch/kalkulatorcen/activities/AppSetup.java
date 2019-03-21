package eu.rjch.kalkulatorcen.activities;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import eu.rjch.kalkulatorcen.R;

public class AppSetup extends Activity {
	
	private EditText vatET;
	private String vatSET;
	
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
		final SharedPreferences.Editor editor = pref.edit();
		//todo check for existance of the vat value and if vatet empty save it as it is
		
		vatET = findViewById(R.id.vat_settings_et);
		vatET.addTextChangedListener(new TextWatcher() {
			
			@Override
			public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
			
			}
			
			@Override
			public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
				vatSET = vatET.getText().toString();
				Log.v("WWW", "ap setup "+vatSET);
			}
			
			@Override
			public void afterTextChanged(Editable editable) {
			
			}
		});

		Button save = findViewById(R.id.save_btn);
		save.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				Log.v("WWW", "setup "+vatSET);
				
				editor.putString(getResources().getString(R.string.chk_vat), vatSET);
				editor.commit();
				Intent i = new Intent(view.getContext(), TheApp.class);
				startActivity(i);
				finish();
			}
		});
	}
}
