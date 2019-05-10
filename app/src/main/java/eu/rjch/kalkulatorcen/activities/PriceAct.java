package eu.rjch.kalkulatorcen.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import eu.rjch.kalkulatorcen.R;

public class PriceAct extends Activity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.price_act_layout);

        init();
    }

    private void init() {
        TextView price = findViewById(R.id.suggested_price);
        Intent i = getIntent();
        String s = i.getExtras().getString(getResources().getString(R.string.price));

        price.setText(s);

        Button ok = findViewById(R.id.ok_btn);
        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent ib = new Intent(v.getContext(), TheApp.class);
                startActivity(ib);
                finish();
            }
        });
    }
}
