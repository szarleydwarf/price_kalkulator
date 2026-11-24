package eu.rjch.kalkulatorcen.activities;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import eu.rjch.kalkulatorcen.R;

public class EulaAct extends Activity {
	@Override
	protected void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.eula_view);
		
		Button back = findViewById(R.id.go_back);
		back.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				goBack();
			}
		});
	}
	
	private void goBack(){
		Intent i = new Intent(this, TheApp.class);
		startActivity(i);
		finish();
	}
	
}
