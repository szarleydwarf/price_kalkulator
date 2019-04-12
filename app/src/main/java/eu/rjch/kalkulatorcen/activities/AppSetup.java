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
import android.widget.Toast;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

import eu.rjch.kalkulatorcen.R;

public class AppSetup extends Activity {
	
	private EditText vatET;
	private String vatSET, vatSETSaved;
	private boolean isOk = false;
	private AdView adBanner;
	
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
		getAds();
		SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPref", MODE_PRIVATE);
		
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
		ImageButton save = findViewById(R.id.save_btn);
		Button email = findViewById(R.id.email_me);
		Button back = findViewById(R.id.go_back);
		
		sendEmail(email);
		saving(pref, save);
		returnToMain(back);
	}
	
	private void getAds() {
		adBanner = findViewById(R.id.adView);
		AdRequest ar = new AdRequest.Builder().build();
		adBanner.loadAd(ar);
	}
	
	private void returnToMain(Button back) {
		back.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				goBack();
			}
		});
	}
	
	private void sendEmail(Button email) {
		email.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				//todo add system config to the message, maybe some error log file as well?
				String[] reciepent = {"r.j.chomik@gmail.com"};
				String subject = "Feedback from Price Calc";
				
				Intent i = new Intent(Intent.ACTION_SEND);
				i.putExtra(Intent.EXTRA_EMAIL, reciepent);
				i.putExtra(Intent.EXTRA_SUBJECT, subject);
				i.setType("message/rfc822");
				startActivity(Intent.createChooser(i, "Choose your email"));
			}
		});
	}
	
	private void saving(SharedPreferences pref, final ImageButton save) {
		final SharedPreferences.Editor editor = pref.edit();
		
		save.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				String s;
				s = isThereStringTosave(save);
				
				editor.putString(getResources().getString(R.string.chk_vat), s);
				editor.commit();
				
				if(isOk)
					goBack();
				
			}
		});
	}
	
	private void goBack(){
		Intent i = new Intent(/*view.getContext()*/this, TheApp.class);
		startActivity(i);
		finish();
	}
	
	private String isThereStringTosave(ImageButton save) {
		if(vatSET.isEmpty() && vatSETSaved.isEmpty()) {
			//if both empty display messgage prompt
			showDialog("No vat value saved and typed in. Please enter vat value");
//			Toast.makeText(AppSetup.this,"No vat value saved and typed in. Please enter vat value ",
//					Toast.LENGTH_LONG).show();
			return null;
		} else if(vatSET.isEmpty()) {
			//if et empty and saved has some value save it
			showDialog("Vat value set at "+vatSETSaved+"%");
			Toast.makeText(this,"Vat value set at "+vatSETSaved+"%",Toast.LENGTH_LONG).show();
			return vatSETSaved;
		} else if(!vatSET.isEmpty()) {
			//if et not empty save it
			showDialog("Vat saved at "+vatSET + "%");
			Toast.makeText(this,"Vat saved at "+vatSET + "%",Toast.LENGTH_LONG).show();
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
						isOk = true;
					}
				});
		AlertDialog alertDialog = builder.create();
		alertDialog.show();
	}
}
