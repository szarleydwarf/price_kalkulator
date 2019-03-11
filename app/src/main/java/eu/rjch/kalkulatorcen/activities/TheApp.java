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

public class TheApp  extends Activity {
	private Spinner profits;
	private ItemSelectedListener isl;
	private TextView costTV, priceTV, transportTV;
	private EditText netPriceEV, transportEV;
	private Button calculate;
	private CheckBox vatChk, vemcChk, transChk;
	
	private char euro = '€', hash = '#';
	private String profitS, costS, priceS;
	private boolean vat, vemc, transB;
	private float transF;
	private double costD, priceD;
	private int profit;
	
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
		update();
	}
	
	private void update() {
		updateCheckBoxes();
		updateEditText();
	}
	
	private void updateEditText() {
		this.netPriceEV.addTextChangedListener(new TextWatcher() {
			@Override
			public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
				priceS = euro+" 0.00";
			}
			
			@Override
			public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
				costTV.setText(euro+netPriceEV.getText().toString());
			}
			
			@Override
			public void afterTextChanged(Editable editable) {
			}
		});
		
		this.transportEV.addTextChangedListener(new TextWatcher() {
			@Override
			public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
			}
			//todo work in progress
			@Override
			public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
				String s = transportTV.getText().toString();
				Log.v("WWW","S "+s);
				String pattern = euro+"\\?.*?"+hash;
				String trans = transportEV.getText().toString();
				Log.v("WWW","trans "+trans);
				s = s.replaceAll(pattern, "Transport charge "+euro+" "+trans+" "+hash);
				Log.v("WWW","s2 "+s);
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
		
		this.vatChk = findViewById(R.id.chkvat);
		this.vemcChk = findViewById(R.id.chkvemc);
		this.transChk = findViewById(R.id.chktransport);
		
		this.netPriceEV = findViewById(R.id.netto);
		this.transportEV = findViewById(R.id.transport_edit);
		
		this.calculate = findViewById(R.id.calculate_btn);
	}
	
}
