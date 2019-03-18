package eu.rjch.kalkulatorcen.activities;
import android.app.Activity;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.*;
import eu.rjch.kalkulatorcen.R;
import eu.rjch.kalkulatorcen.utilities.ItemSelectedListener;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Locale;

public class TheApp  extends Activity {
	private Spinner profits;
	private ItemSelectedListener isl;
	private TextView costTV, priceTV, transportTV;
	private EditText netPriceEV, transportEV;
	private Button calculate;
	private CheckBox vatChk, vemcChk, transChk;
	
	private char euro = '€', hash = '#';
	private String profitS, costS, priceS, transportStringTV;
	private boolean vat, vemc, transB;
	private double transF = 0.5f;
	private double costD, priceD, vatD = 1.23, vemcD = 2.8;
	private int profit;
	private DecimalFormat df;
	
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
		
	}
	
	private double calculatePrice(double d) {
		double t;
		t = (isl.getSelectedItem() != null) ? Double.parseDouble(isl.getSelectedItem()) : 30;
		
		d = d + ((d * t) / 100);
		 return calculateCost(d);
	}
	
	private double calculateCost(double d) {
		d = (vemc) ? d + vemcD : d;
		d = (vat) ? d * vatD : d;
		
		d = (transB) ? (d + transF) : d;
		return d;
	}
	
	
	private void updateEditText() {
		this.netPriceEV.addTextChangedListener(new TextWatcher() {
			@Override
			public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
				priceS = euro+" 0.00";
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
				String s = transportTV.getText().toString();
//				String end = s.substring(s.indexOf(hash));
				String start = s.substring(0, s.indexOf(" ")+1);
				String middle = euro+transportEV.getText().toString();
				s = start + middle;
				s = (s.equals("") || s == null) ? transportStringTV : s;
				//todo work in progress
				
				String st = s.substring(s.indexOf(euro)+1);
				Log.v("WWW", "st"+st+"r");
				transF = (st.equals("")) ? transF : Double.parseDouble(st);
				transportTV.setText(s);
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
		this.profits = findViewById(R.id.spinner);
		this.isl = new ItemSelectedListener(this.profits);
		
		ArrayAdapter a = ArrayAdapter.createFromResource(this, R.array.profit_list, R.layout.spinner_t_size);
		this.profits.setAdapter(a);
		this.profits.setOnItemSelectedListener(this.isl);
		
		
		this.costTV = findViewById(R.id.costTF);
		this.priceTV = findViewById(R.id.priceTF);
		this.transportTV = findViewById(R.id.transport);
//		this.transportStringTV = transportTV.getText().toString();
		
		this.vatChk = findViewById(R.id.chkvat);
		this.vemcChk = findViewById(R.id.chkvemc);
		this.transChk = findViewById(R.id.chktransport);
		
		this.netPriceEV = findViewById(R.id.netto);
		this.transportEV = findViewById(R.id.transport_edit);
		
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
