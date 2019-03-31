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
import eu.rjch.kalkulatorcen.R;
import eu.rjch.kalkulatorcen.utilities.ItemSelectedListener;
import eu.rjch.kalkulatorcen.utilities.MathsUt;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Locale;

public class TheApp  extends Activity {
	private Spinner profits;
	private ItemSelectedListener isl;
	private TextView costTV, priceTV, transportTV;
	private EditText netPriceEV, transportEV;
	private Button calculate, settings;
	private CheckBox vatChk, vemcChk, transChk;
	
	private char euro = '€', hash = '#';
	private String priceS, transportStringTV;
	private boolean vat, vemc, transB;
	private double transD = 0.5;
	private double costD, vatD = 23, vemcD = 3.44;
	private DecimalFormat df;
	private MathsUt mu;
	private SeekBar seekbar;
	
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
		SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPref", MODE_PRIVATE);
//		SharedPreferences.Editor editor = pref.edit();
		String vatS = pref.getString(getResources().getString(R.string.chk_vat), "");
		if(!vatS.equals("")) {
			vatD = Double.parseDouble(vatS);
		}
		
		setVariable();
		setFormating();
		update();
	}
	
	private void setFormating() {
		DecimalFormatSymbols symbols = new DecimalFormatSymbols( new Locale("en", "UK"));
		symbols.setDecimalSeparator('.');
		symbols.setGroupingSeparator(' ');
		
		this.df = new DecimalFormat("00,000.00", symbols);
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
				Toast.makeText(TheApp.this, "Seek bar progress is :" + progressChangedValue,
						Toast.LENGTH_SHORT).show();
			}
		});
	}
	
	private double calculatePrice(double d) {
		double t;
		t = (isl.getSelectedItem() != null) ? Double.parseDouble(isl.getSelectedItem()) : 30;
		
		d = d + mu.calculatePercentage(d, t);
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
				String s = netPriceEV.getText().toString();
				costTV.setText(euro + s);
				costD = (!s.equals("") && s != null) ? Double.parseDouble(s):  0.0;
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
				
				String st = transportTV.getText().toString();
				st = st.substring(0, st.indexOf(" ")+1) + euro + s;
				transportTV.setText(st);
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
				vat = vatChk.isChecked() ? true : false;
			}
		});
		vemcChk.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				vemc = vemcChk.isChecked() ? true : false;
			}
		});
		transChk.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				transB = transChk.isChecked() ? true : false;
			}
		});
	}
	
	private void setVariable() {
		mu = new MathsUt();
		this.profits = findViewById(R.id.spinner);
		ArrayAdapter a = ArrayAdapter.createFromResource(this, R.array.profit_list, R.layout.spinner_t_size);
		this.profits.setAdapter(a);
		this.isl = new ItemSelectedListener(this.profits);
		
		this.profits.setOnItemSelectedListener(this.isl);
		
		this.seekbar = findViewById(R.id.vat_seek_bar);
		this.seekbar.setMax(50);
		
		this.costTV = findViewById(R.id.costTF);
		this.priceTV = findViewById(R.id.priceTF);
		this.transportTV = findViewById(R.id.transport);
		this.transportStringTV = transportTV.getText().toString();
		
		this.vatChk = findViewById(R.id.chkvat);
		this.vemcChk = findViewById(R.id.chkvemc);
		this.transChk = findViewById(R.id.chktransport);
		
		this.netPriceEV = findViewById(R.id.netto);
		this.transportEV = findViewById(R.id.transport_edit);
		
		this.settings = findViewById(R.id.settings_btn);
		this.settings.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				Intent i = new Intent(view.getContext(), AppSetup.class);
				startActivity(i);
				finish();
			}
		});
		
		this.calculate = findViewById(R.id.calculate_btn);
		this.calculate.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				double d = checkCost(), cd, pd;
				cd = calculateCost(d);
				pd = calculatePrice(d);
				
				costTV.setText(euro + " " + df.format(cd));
				priceTV.setText(euro + " " + df.format(pd));
			}
		});
	}
	
	private double checkCost() {
		return (costD > 0) ? costD : 0;
	}
	
}
