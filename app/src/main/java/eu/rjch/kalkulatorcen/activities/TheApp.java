package eu.rjch.kalkulatorcen.activities;
import android.app.Activity;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.*;
import eu.rjch.kalkulatorcen.R;
import eu.rjch.kalkulatorcen.utilities.ItemSelectedListener;

public class TheApp  extends Activity {
	private Spinner profits;
	private ItemSelectedListener isl;
	private TextView costTV, priceTV;
	private EditText netPriceEV, transportEV;
	private Button calculate;
	private CheckBox vatChk, vemcChk, transChk;
	
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
		}else {
			setContentView(R.layout.app_layout_old);
			
		}
		
		
		init();
	}
	
	private void init() {
		setVariable();
		update();
	}
	
	private void update() {
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
		
		this.vatChk = findViewById(R.id.chkvat);
		this.vemcChk = findViewById(R.id.chkvemc);
		this.transChk = findViewById(R.id.chktransport);
		
		this.netPriceEV = findViewById(R.id.netto);
		this.transportEV = findViewById(R.id.transport_edit);
		
		this.calculate = findViewById(R.id.calculate_btn);
	}
	
}
