package somitsolutions.unit.unitconverter;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

import static com.google.android.gms.ads.AdRequest.DEVICE_ID_EMULATOR;


public class StopWatchActivity extends Activity {
	private Button startButton;
	private Button pauseButton;
	private Button resetButton;
	private Button backButton;

	private TextView timerValue;

	private long startTime = 0L;

	private Handler customHandler = new Handler();

	long timeInMilliseconds = 0L;

	long timeSwapBuff = 0L;

	long updatedTime = 0L;
	private AdView mAdView;

	@Override
	public void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.stopwatch);
		mAdView = (AdView) findViewById(R.id.adView);

		AdRequest adRequest = new AdRequest.Builder()
				.addTestDevice(DEVICE_ID_EMULATOR)
						// Check the LogCat to get your test device ID
				.addTestDevice("C04B1BFFB0774708339BC273F8A43708")
				.build();
		mAdView.setAdListener(new AdListener() {
			@Override
			public void onAdLoaded() {
//				Toast.makeText(getApplicationContext(), "Ad is loaded!", Toast.LENGTH_SHORT).show();
			}

			@Override
			public void onAdClosed() {
//				Toast.makeText(getApplicationContext(), "Ad is closed!", Toast.LENGTH_SHORT).show();
			}

			@Override
			public void onAdFailedToLoad(int errorCode) {
//				Toast.makeText(getApplicationContext(), "Ad failed to load! error code: " + errorCode, Toast.LENGTH_SHORT).show();
			}

			@Override
			public void onAdLeftApplication() {
//				Toast.makeText(getApplicationContext(), "Ad left application!", Toast.LENGTH_SHORT).show();
			}

			@Override
			public void onAdOpened() {
//				Toast.makeText(getApplicationContext(), "Ad is opened!", Toast.LENGTH_SHORT).show();
			}
		});
		mAdView.loadAd(adRequest);
		timerValue = (TextView) findViewById(R.id.timerValue);
		startButton = (Button) findViewById(R.id.startButton);
		startButton.setOnClickListener(new View.OnClickListener() {
			public void onClick(View view) {
				startTime = SystemClock.uptimeMillis();
				Log.d("startTime", "" + startTime);
				customHandler.postDelayed(updateTimerThread, 0);
			}
		});

		pauseButton = (Button) findViewById(R.id.pauseButton);
		pauseButton.setOnClickListener(new View.OnClickListener() {
			public void onClick(View view) {
				timeSwapBuff += timeInMilliseconds;
				customHandler.removeCallbacks(updateTimerThread);
			}
		});
		resetButton = (Button) findViewById(R.id.resetButton);
		resetButton.setOnClickListener(new View.OnClickListener() {

			public void onClick(View v) {
				// TODO Auto-generated method stub
				updatedTime=0;
				timeInMilliseconds=0L;
				timeSwapBuff=0L;
				timerValue.setText("" + String.format("%02d", 00) + ":"
						+ String.format("%02d", 00) + ":"
						+ String.format("%02d", 00)+":"+ String.format("%03d", 000));
				startTime = SystemClock.uptimeMillis();
				customHandler.removeCallbacks(updateTimerThread);
			}
		});
		backButton = (Button) findViewById(R.id.backbutton);
		backButton.setOnClickListener(new View.OnClickListener() {

			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent startMain = new Intent(StopWatchActivity.this,
						Flashmain.class);
				startMain.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				startActivity(startMain);
				finish();
			}
		});
	}

	private Runnable updateTimerThread = new Runnable() {
		public void run() {

			timeInMilliseconds = SystemClock.uptimeMillis() - startTime;
			updatedTime = timeSwapBuff + timeInMilliseconds;

			int secs = (int) (updatedTime / 1000);
			int mins = secs / 60;
			secs = secs % 60;
			int hours = mins / 60;
//			Log.d("hours", "" + hours);
			if(mins==60){
				mins=0;
			}
			int milliseconds = (int) (updatedTime % 1000);
			timerValue.setText("" +  String.format("%02d", hours) + ":" + String.format("%02d", mins)
					+ ":" + String.format("%02d", secs)+":"+String.format("%03d", milliseconds));

			customHandler.postDelayed(this, 0);

		}

	};

}
