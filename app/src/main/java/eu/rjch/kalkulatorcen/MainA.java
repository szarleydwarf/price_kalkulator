package eu.rjch.kalkulatorcen;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.widget.VideoView;
import eu.rjch.kalkulatorcen.activities.AppSetup;
import eu.rjch.kalkulatorcen.activities.TheApp;

import com.crashlytics.android.Crashlytics;
import com.google.android.gms.ads.MobileAds;
import io.fabric.sdk.android.Fabric;

public class MainA extends Activity {
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Fabric.with(this, new Crashlytics());
		setContentView(R.layout.act_main);
		Crashlytics.log(1, "MainApp", "Something went wrong on start MainApp");

		VideoView vv = findViewById(R.id.VideoView);

		try{
			String pkgName = getPackageName();
			int id = getResources().getIdentifier("intro", "raw", pkgName);
			vv.setVideoURI(Uri.parse("android.resource://"+getPackageName()+"/"+id));
			
			vv.start();
			vv.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
				@Override
				public void onCompletion(MediaPlayer mediaPlayer) {
					runApp();
				}
			});
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private void runApp() {
		SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPref", MODE_PRIVATE);
		SharedPreferences.Editor editor = pref.edit();

		String firstUse = getResources().getString( R.string.first_use );
		boolean userFirstLogin = pref.getBoolean(firstUse, true);
		
		if(userFirstLogin){
			editor.putBoolean(firstUse, false);
			editor.commit();
			
			Intent i = new Intent(this, AppSetup.class);
			startActivity(i);
			finish();
		} else {
			Intent i = new Intent(this, TheApp.class);
			startActivity(i);
			finish();
		}
		
		
	}
	
}
