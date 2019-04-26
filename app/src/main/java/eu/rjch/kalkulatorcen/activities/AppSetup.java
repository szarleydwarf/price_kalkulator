package eu.rjch.kalkulatorcen.activities;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import com.google.android.gms.ads.AdView;

import eu.rjch.kalkulatorcen.R;
import eu.rjch.kalkulatorcen.utilities.AdsHandler;
import eu.rjch.kalkulatorcen.utilities.RJErrorsHandler;

public class AppSetup extends Activity {
	
	private EditText vatET, recET;
	private String vatSET, vatSETSaved, recStr;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
			setContentView(R.layout.app_setup_layout);
		}else {
			setContentView(R.layout.app_setup_layout_old);
			//todo catch crash of the app
		}
		
		init();
	}
	
	private void init() {
		getAds();
		SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPref", MODE_PRIVATE);
		
		recET = findViewById(R.id.recycling_et);
		recET.addTextChangedListener(new TextWatcher() {
			
			@Override
			public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}
			
			@Override
			public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
				recStr = recET.getText().toString();
			}
			
			@Override
			public void afterTextChanged(Editable editable) {}
		});
		vatSETSaved = pref.getString(getResources().getString(R.string.chk_vat), "");
		vatSET = "";
		
		vatET = findViewById(R.id.vat_settings_et);
		vatET.addTextChangedListener(new TextWatcher() {
			
			@Override
			public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}
			
			@Override
			public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
				vatSET = vatET.getText().toString();
			}
			
			@Override
			public void afterTextChanged(Editable editable) {}
		});
		Button saveVAT = findViewById(R.id.save_btn);
		Button saveRec = findViewById(R.id.save_r_btn);
		ImageButton email = findViewById(R.id.email_me);
		
		Button eula = findViewById(R.id.eula_btn);
		Button back = findViewById(R.id.go_back);
		
		sendEmail(email);
		savingVAT(pref, saveVAT);
		savingRec(pref, saveRec);
		returnToMain(back);
		showEULA(eula);
	}
	
	private void showEULA(Button eula) {
		eula.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent i = new Intent(v.getContext(), EulaAct.class);
				startActivity(i);
				finish();
			}
		});
	}
	
	private void savingRec(SharedPreferences pref, Button saveRec) {
		final SharedPreferences.Editor editor = pref.edit();
		saveRec.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				if(!recET.getText().toString().isEmpty()) {
					recStr = recET.getText().toString();
					
					editor.putString(getResources().getString(R.string.recycling), recStr);
					editor.commit();
					
					showDialog("Saving Recycling charge "+recStr);
				}
				
			}
		});
	}
	
	private void savingVAT(SharedPreferences pref, final Button save) {
		final SharedPreferences.Editor editor = pref.edit();
		
		save.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				String s;
				s = isThereStringTosave();
				if(s != null) {
					editor.putString(getResources().getString(R.string.chk_vat), s);
					editor.commit();
				}
			}
		});
	}
	
	private void getAds() {
		AdsHandler ah = new AdsHandler();
		ah.getAds((AdView) findViewById(R.id.adView));
	}
	
	private void returnToMain(Button back) {
		back.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				goBack();
			}
		});
	}
	
	private void sendEmail(ImageButton email) {
		email.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				String subject = "Feedback from Price Calc";
				RJErrorsHandler eh = new RJErrorsHandler();
				
				startActivity(Intent.createChooser(eh.sendEmail(subject, getResources().getString(R.string.email_msg_1)), "Choose your email"));
			}
		});
	}
	
	private void goBack(){
		Intent i = new Intent(this, TheApp.class);
		startActivity(i);
		finish();
	}
	
	private String isThereStringTosave() {
		if(vatSET.isEmpty() && vatSETSaved.isEmpty()) {
			//if both empty display messgage prompt
			showDialog("No vat value saved and typed in. By default VAT will be set to "
					+getResources().getString(R.string.vat_default)
					+getResources().getString(R.string.percent));
			return null;
		} else if(vatSET.isEmpty()) {
			//if et empty and saved has some value save it
			showDialog("Vat value set at "+vatSETSaved+"%");
			return vatSETSaved;
		} else if(!vatSET.isEmpty()) {
			//if et not empty save it
			showDialog("Vat saved at "+vatSET + "%");
			return vatSET;
		}
		return null;
	}
	
	private void showDialog(final String s) {
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setMessage(s);
		
		builder.setPositiveButton("OK",
				new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface arg0, int arg1) {
//						boolean isOk = true;
//						if(isOk)
//							return;
					}
				});
		AlertDialog alertDialog = builder.create();
		alertDialog.show();
	}
}
