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
import eu.rjch.kalkulatorcen.utilities.Utilities;

public class AppSetup extends Activity {
	
	private EditText vatET, recET, maxProfitET;
	private String vatSET, vatSETSaved, recStr;
	private Utilities u;
	
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
		this.u = new Utilities();

		SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPref", MODE_PRIVATE);
		vatSETSaved = pref.getString(getResources().getString(R.string.chk_vat), "");

		vatSET = "";

		recET = findViewById(R.id.recycling_et);
		vatET = findViewById(R.id.vat_settings_et);
		maxProfitET = findViewById(R.id.max_profit_et);


		ImageButton email = findViewById(R.id.email_me);

		Button saveVAT = findViewById(R.id.save_btn);
		Button saveRec = findViewById(R.id.save_r_btn);
        Button saveMaxProf = findViewById(R.id.save_max_profit_btn);
        Button eula = findViewById(R.id.eula_btn);
        Button back = findViewById(R.id.go_back);

		checkForTextChanges();
		sendEmail(email);
		savingVAT(pref, saveVAT);
		savingRec(pref, saveRec);
		saveMaxProfit(pref, saveMaxProf, R.string.max_profit);
		returnToMain(back);
		showEULA(eula);
	}

    private void checkForTextChanges() {
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

    private void saveMaxProfit(SharedPreferences pref, Button saveMaxProf, final int recycling) {
        final SharedPreferences.Editor e = pref.edit();
        saveMaxProf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!maxProfitET.getText().toString().isEmpty()) {
                    //todo check if the ontextchanged is necessary
                    String s = maxProfitET.getText().toString();
                    e.putString(getResources().getString(recycling), s);
                    e.commit();

                    u.showDialog(v.getContext(), "Max profit saved at " + s);
                }
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

					u.showDialog(v.getContext(),"Saving Recycling charge "+recStr);
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
			u.showDialog(this,"No vat value saved and typed in. By default VAT will be set to "
					+getResources().getString(R.string.vat_default)
					+getResources().getString(R.string.percent));
			return null;
		} else if(vatSET.isEmpty()) {
			//if et empty and saved has some value save it
            u.showDialog(this,"Vat value set at "+vatSETSaved+"%");
			return vatSETSaved;
		} else if(!vatSET.isEmpty()) {
			//if et not empty save it
            u.showDialog(this,"Vat saved at "+vatSET + "%");
			return vatSET;
		}
		return null;
	}
}
