package eu.rjch.kalkulatorcen.activities;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.*;
import com.google.android.gms.ads.AdView;
import eu.rjch.kalkulatorcen.R;
import eu.rjch.kalkulatorcen.utilities.AdsHandler;
import eu.rjch.kalkulatorcen.utilities.MathsUt;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Locale;

public class TheApp  extends Activity {
	private TextView costTV;
    private TextView priceTV;
    private TextView transportTV;
    private TextView profitPercentTV;
	private EditText netPriceEV, transportEV;
	private CheckBox vatChk, vemcChk, transChk, profitChk;

    private DecimalFormat df;
    private MathsUt mu;
    private SeekBar seekbar;

	private char euro = '€';
	private boolean vat, vemc, transB;
	private int profitPercent;
	private double costD, vatD, vemcD = 3.44, transD = 0.5;
	private int maxProfit;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
			setContentView(R.layout.app_layout);
		} else {
			setContentView(R.layout.app_layout_old);
		}
		
		init();
	}
	
	private void init() {
		loadAds();
		
		findByIDs();
		setVariable();
		
		setFormating();
		update();
	}
	
	private void update() {
		updateCheckBoxes();
		updateEditText();
		updateSeekBar();
	}
	
	private void updateSeekBar() {
		this.seekbar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
			int progressChangedValue = 0;
			@Override
			public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
				progressChangedValue = i;
			}
			
			@Override
			public void onStartTrackingTouch(SeekBar seekBar) {
			
			}
			
			@Override
			public void onStopTrackingTouch(SeekBar seekBar) {
				profitPercent = progressChangedValue;
				String s =  profitPercent + "%";
				profitPercentTV.setText(s);
				Toast.makeText(getBaseContext(), "Profit @ "+s,Toast.LENGTH_LONG).show();
				setTVs();
			}
		});
	}
	
	private double calculatePrice(double d) {
		d = d + mu.calculatePercentage(d, profitPercent);
		return calculateCost(d);
	}
	
	private double calculateCost(double d) {
		d = (vat) ? d + mu.calculatePercentage(d, vatD) : d;
		d = (vemc) ? d + vemcD : d;
		
		d = (transB) ? (d + transD) : d;
		return d;
	}

	private void updateEditText() {
		this.netPriceEV.addTextChangedListener(new TextWatcher() {
			@Override
			public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
			}
			
			@Override
			public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
				String s = netPriceEV.getText().toString(), se;
				se = euro + s;
				costTV.setText(se);
				costD = (!s.equals("")) ? Double.parseDouble(s):  0.0;
				setTVs();
			}
			
			@Override
			public void afterTextChanged(Editable editable) {
			}
		});
		
		this.transportEV.addTextChangedListener(new TextWatcher() {
			@Override
			public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
			}

			@Override
			public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
				String s = transportEV.getText().toString();
				transD = (!s.equals("")) ? Double.parseDouble(s) : transD;
				
				setText(transportTV, s);
				setTVs();
			}
			
			@Override
			public void afterTextChanged(Editable editable) {
			}
		});
	}
	
	private void updateCheckBoxes() {
		vatChk.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				vat = vatChk.isChecked();
				setTVs();
			}
		});
		vemcChk.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				vemc = vemcChk.isChecked();
				setTVs();
			}
		});
		transChk.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				transB = transChk.isChecked();
				setTVs();
			}
		});

		profitChk.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				toggleViewVisibility(profitPercentTV);
			}
		});

		String s = vatChk.getText().toString();
		s += " - " + vatD + "%";
		vatChk.setText(s);
	}

    private void setVariable(){
		SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPref", MODE_PRIVATE);
		String vatS = pref.getString(getResources().getString(R.string.chk_vat), "");
		String s = pref.getString(getResources().getString(R.string.max_profit), "200");

		if(!s.equals("")) {
			maxProfit = Integer.parseInt(s);
		} else {
			maxProfit = 200;
		}
		addItemsToSpinnerSeekBar();

		if(!vatS.equals("")) {
			vatD = Double.parseDouble(vatS);
		} else {
			vatD = Double.parseDouble(getResources().getString(R.string.vat_default));
		}
		
		String recS = pref.getString(getResources().getString(R.string.recycling),"");
		if(!recS.equals("")){
			vemcD = Double.parseDouble(recS);
		} else {
			vemcD = Double.parseDouble(getResources().getString(R.string.rec_defaoult));
		}
		
		setTextC(vemcChk, ""+vemcD);
	}

	private void addItemsToSpinnerSeekBar() {
		seekbar.setMax(maxProfit);
	}

	private void setTextC(CheckBox chk, String s) {
		String st = chk.getText().toString();
		st = st.substring(0, st.indexOf(" ")+1) + euro + s;
        chk.setText(st);
	}
	
	private void setText(TextView tv, String s) {
		String st = tv.getText().toString();
		st = st.substring(0, st.indexOf(" ")+1) + euro + s;
		tv.setText(st);
	}
	
	private void findByIDs() {
		mu = new MathsUt();

        this.seekbar = findViewById(R.id.vat_seek_bar);
        profitPercent = seekbar.getProgress();

		this.costTV = findViewById(R.id.costTF);
		this.priceTV = findViewById(R.id.priceTF);
		this.transportTV = findViewById(R.id.transport);
		
		this.profitPercentTV = findViewById(R.id.profit_percent);
		setText(profitPercentTV, profitPercent+"%");

		this.vatChk = findViewById(R.id.chkvat);
		this.vemcChk = findViewById(R.id.chkvemc);
		this.transChk = findViewById(R.id.chktransport);
		this.profitChk = findViewById(R.id.profit_percent_chkbox);
		
		this.netPriceEV = findViewById(R.id.netto);
		this.transportEV = findViewById(R.id.transport_edit);

		Button showCost = findViewById(R.id.costLabel);
		showCost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toggleViewVisibility(costTV);
            }
        });

		
		Button settings = findViewById(R.id.settings_btn);
		settings.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				Intent i = new Intent(view.getContext(), AppSetup.class);
				startActivity(i);
				finish();
			}
		});
	}

    private <T extends View> void toggleViewVisibility(T v) {
        if(v.getVisibility() ==  View.VISIBLE)
            v.setVisibility(View.INVISIBLE);
        else
            v.setVisibility(View.VISIBLE);
    }

    protected void setTVs(){
		double d = checkCost(), cd, pd;
		cd = calculateCost(d);
		pd = calculatePrice(d);

		String c = euro + " " + df.format(cd);
		costTV.setText(c);
		String p = euro + " " + df.format(pd);
		priceTV.setText(p);
	}

	private double checkCost() { return (costD > 0) ? costD : 0;	}
	
	private void loadAds() {
		AdsHandler ah = new AdsHandler();
		ah.getAds((AdView) findViewById(R.id.adView));
	}

	private void setFormating() {
		DecimalFormatSymbols symbols = new DecimalFormatSymbols( new Locale("en", "UK"));
		symbols.setDecimalSeparator('.');
		symbols.setGroupingSeparator(' ');

		this.df = new DecimalFormat("00,000.00", symbols);
	}

	@Override
	public void onPause() {
		super.onPause();
		finish();
	}
	
	@Override
	public void onResume(){
		super.onResume();
	}
}
