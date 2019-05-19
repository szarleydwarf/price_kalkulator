package eu.rjch.kalkulatorcen.activities;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageButton;

import com.crashlytics.android.Crashlytics;
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
//        Crashlytics.getInstance().crash(); // Force a crash

		setContentView(R.layout.app_setup_layout);
		//todo catch crash of the app

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
		ImageButton vBtn = findViewById(R.id.img_btn_V);
		ImageButton hBtn = findViewById(R.id.img_btn_H);

		Button saveVAT = findViewById(R.id.save_btn);
		Button saveRec = findViewById(R.id.save_r_btn);
        Button saveMaxProf = findViewById(R.id.save_max_profit_btn);
        Button eula = findViewById(R.id.eula_btn);
        Button back = findViewById(R.id.go_back);


		sendEmail(email);
		savingVAT(pref, saveVAT);
		savingRec(pref, saveRec);
		saveMaxProfit(pref, saveMaxProf, R.string.max_profit);
		setLayout(pref, vBtn, hBtn);
        returnToMain(back);
		showEULA(eula);
	}


	private void setLayout(SharedPreferences pref, ImageButton vBtn, ImageButton hBtn) {
		final SharedPreferences.Editor e = pref.edit();
		vBtn.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				e.putBoolean(getResources().getString(R.string.orien), true);
				e.commit();
				u.showToast(v, "Screen layout saved");
			}
		});
		hBtn.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				e.putBoolean(getResources().getString(R.string.orien), false);
				e.commit();
                u.showToast(v, "Screen layout saved");

            }
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

    private void saveMaxProfit(SharedPreferences pref, Button btn, final int ri) {
        final SharedPreferences.Editor e = pref.edit();
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!maxProfitET.getText().toString().isEmpty()) {
                    String s = maxProfitET.getText().toString();
                    e.putString(getResources().getString(ri), s);
                    e.commit();
                    u.showToast(v, "Max profit saved at " + s + "%");
//                    u.showDialog(v.getContext(), "Max profit saved at " + s);
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

					u.showToast(v,"Saving Recycling charge €"+recStr);
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
				s = isThereStringTosave(view);
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
	
	private String isThereStringTosave(View view) {
		String s  = vatET.getText().toString();
		if(s.isEmpty() && vatSETSaved.isEmpty()) {
			//if both empty display messgage prompt
			u.showToast(view, "No vat value saved and typed in. By default VAT will be set to "
					+getResources().getString(R.string.vat_default)
					+getResources().getString(R.string.percent));
//			u.showDialog(this,"No vat value saved and typed in. By default VAT will be set to "
//					+getResources().getString(R.string.vat_default)
//					+getResources().getString(R.string.percent));
			return null;
		} else if(s.isEmpty()) {
			//if et empty and saved has some value save it
            u.showToast(view,"Vat value set at "+vatSETSaved+"%");
			return vatSETSaved;
		} else if(!s.isEmpty()) {
			//if et not empty save it
            u.showToast(view,"Vat saved at "+s + "%");
			return s;
		}
		return null;
	}
}
