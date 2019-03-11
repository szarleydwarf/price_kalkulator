package eu.rjch.kalkulatorcen;

import android.app.Activity;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.VideoView;
import eu.rjch.kalkulatorcen.activities.TheApp;

public class MainA extends Activity {
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.act_main);
		runApp();
		//todo - uncomment bellow
//		VideoView vv = findViewById(R.id.VideoView);
//
//		try{
//			String pkgName = getPackageName();
//			int id = getResources().getIdentifier("intro", "raw", pkgName);
//			vv.setVideoURI(Uri.parse("android.resource://"+getPackageName()+"/"+id));
//
//			vv.start();
//			vv.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
//				@Override
//				public void onCompletion(MediaPlayer mediaPlayer) {
//					runApp();
//				}
//			});
//		} catch (Exception e) {
//			Log.e("ERR", e.getMessage());
//			e.printStackTrace();
//		}
		
	}
	
	private void runApp() {
		Intent i = new Intent(this, TheApp.class);
		startActivity(i);
		finish();
	}
	
}
