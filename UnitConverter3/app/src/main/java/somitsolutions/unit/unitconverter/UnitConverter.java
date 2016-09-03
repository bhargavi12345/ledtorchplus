/*
  	Copyright (c) 2012 Somenath Mukhopadhyay.
 	All rights reserved.
 */

package somitsolutions.unit.unitconverter;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

import static com.google.android.gms.ads.AdRequest.DEVICE_ID_EMULATOR;


//this is for git testing

public class UnitConverter extends Activity implements OnClickListener,
		AdapterView.OnItemSelectedListener {
	/** Called when the activity is first created. */

	private Spinner SpinnerUnit;
	private EditText inputValue;
	private Spinner SpinnerFrom;
	private Spinner SpinnerTo;
	private Button ButtonConvert;
	private EditText ResultView;
	ArrayAdapter<String> unitarray;
	ArrayAdapter<String> unitarrayadapter;
	 Strategy currentStrategy;
	private Strategy lastStrategy;
	 String unitfrom;
	 String unitto;
    TextView Type;
	private  static UnitConverter instance=null;
	int position;
	private AdView mAdView;

	// this is to test the Git repository
	@Override
	public void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);

		setContentView(R.layout.main);
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
		Bundle b=getIntent().getExtras();
		if(b!=null){
			 position=b.getInt("position");
		}
		SpinnerFrom = (Spinner) findViewById(R.id.SpinnerFrom);
		SpinnerFrom.setOnItemSelectedListener(this);
		SpinnerTo = (Spinner) findViewById(R.id.SpinnerTo);
		SpinnerTo.setOnItemSelectedListener(this);

		Type=(TextView)findViewById(R.id.TextViewType);

		unitarrayadapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_spinner_item);
		unitarrayadapter
				.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		SpinnerFrom.setAdapter(unitarrayadapter);
		SpinnerTo.setAdapter(unitarrayadapter);

		unitarrayadapter.setNotifyOnChange(true);
		inputValue = (EditText) findViewById(R.id.EditTextValue);
		ResultView = (EditText) findViewById(R.id.TextViewResult);
		ResultView.setClickable(false);

		ButtonConvert = (Button)findViewById(R.id.Button01);
		ButtonConvert.setOnClickListener(this);
		instance=this;


		switch (position) {
			case 0:
				Type.setText("Area");
				fillSpinnerWithAreaUnit();
				currentStrategy = new AreaStrategy();
				break;
			case 1:
				Type.setText("Energy");
				fillSpinnerWithenErgyUnit();
				currentStrategy = new EnergyStrategy();
				break;
			case 2:
				Type.setText("Length");
				fillSpinnerWithLengthUnit();
				currentStrategy = new LengthStrategy();
				break;
			case 3:
				Type.setText("Power");
				fillSpinnerWithPowerUnit();
				currentStrategy = new PowerStrategy();
				break;
			case 4:
				Type.setText("Pressure");
				fillSpinnerWithPressureUnit();
				currentStrategy = new PressureStrategy();
				break;
			case 5:
				Type.setText("Temperature");
				fillSpinnerWithTempUnit();
				currentStrategy = new TemperatureStrategy();
				break;
			case 6:
				Type.setText("Velocity");
				fillSpinnerWithVelocityUnit();
				currentStrategy = new VelocityStrategy();
				break;
			case 7:
				Type.setText("Volume");
				fillSpinnerWithVolumeUnit();
				currentStrategy = new VolumeStrategy();
				break;
			case 8:
				Type.setText("Weight");
				fillSpinnerWithWeightUnit();
				currentStrategy = new WeightStrategy();
				break;
		}


	}

	public static UnitConverter getInstance() {
 if(instance==null){
	instance=new UnitConverter();
 }
		return instance;
	}

	public void onItemSelected(AdapterView<?> parent) {
	}

	public void onNothingSelected(AdapterView<?> parent) {

	}

	public void onItemSelected(AdapterView<?> parent, View v, int position,
			long id) {


		  if (v.getParent() == SpinnerFrom) {
			unitfrom = (String) (SpinnerFrom.getSelectedItem().toString());
			  Log.d("units", "" + unitfrom );
		}

		else if (v.getParent() == SpinnerTo) {
			unitto = (String) (SpinnerTo.getSelectedItem().toString());
			  Log.d("units", "" + unitto);
		}
	}

	private void fillSpinnerWithTempUnit() {
		unitarrayadapter.clear();
		unitarrayadapter.add(getResources()
				.getString(R.string.temperatureunitc));
		unitarrayadapter.add(getResources()
				.getString(R.string.temperatureunitf));
		unitarrayadapter.notifyDataSetChanged();
	}

	private void fillSpinnerWithWeightUnit() {
		unitarrayadapter.clear();
		unitarrayadapter.add(getResources().getString(R.string.weightunitkg));
		unitarrayadapter.add(getResources().getString(R.string.weightunitgm));
		unitarrayadapter.add(getResources().getString(R.string.weightunitlb));
		unitarrayadapter
				.add(getResources().getString(R.string.weightunitounce));
		unitarrayadapter.add(getResources().getString(R.string.weightunitmg));
		unitarrayadapter.notifyDataSetChanged();
	}

	private void fillSpinnerWithLengthUnit() {
		unitarrayadapter.clear();
		unitarrayadapter.add(getResources().getString(R.string.lengthunitmile));
		unitarrayadapter.add(getResources().getString(R.string.lengthunitkm));
		unitarrayadapter.add(getResources().getString(R.string.lengthunitm));
		unitarrayadapter.add(getResources().getString(R.string.lengthunitcm));
		unitarrayadapter.add(getResources().getString(R.string.lengthunitmm));
		unitarrayadapter.add(getResources().getString(R.string.lengthunitinch));
		unitarrayadapter.add(getResources().getString(R.string.lengthunitfeet));
	}

	private void fillSpinnerWithPowerUnit() {
		unitarrayadapter.clear();
		unitarrayadapter.add(getResources().getString(R.string.powerunitwatts));
		unitarrayadapter.add(getResources().getString(
				R.string.powerunithorseposer));
		unitarrayadapter.add(getResources().getString(
				R.string.powerunitkilowatts));
	}

	private void fillSpinnerWithenErgyUnit() {
		unitarrayadapter.clear();
		unitarrayadapter.add(getResources().getString(
				R.string.energyunitcalories));
		unitarrayadapter.add(getResources()
				.getString(R.string.energyunitjoules));
		unitarrayadapter.add(getResources().getString(
				R.string.energyunitkilocalories));

	}

	private void fillSpinnerWithVelocityUnit() {
		unitarrayadapter.clear();
		unitarrayadapter.add(getResources()
				.getString(R.string.velocityunitkmph));
		unitarrayadapter.add(getResources().getString(
				R.string.velocityunitmilesperh));
		unitarrayadapter.add(getResources().getString(
				R.string.velocityunitmeterpers));
		unitarrayadapter.add(getResources().getString(
				R.string.velocityunitfeetpers));
	}

	private void fillSpinnerWithAreaUnit() {
		unitarrayadapter.clear();
		unitarrayadapter.add(getResources().getString(R.string.areaunitsqkm));
		unitarrayadapter
				.add(getResources().getString(R.string.areaunitsqmiles));
		unitarrayadapter.add(getResources().getString(R.string.areaunitsqm));
		unitarrayadapter.add(getResources().getString(R.string.areaunitsqcm));
		unitarrayadapter.add(getResources().getString(R.string.areaunitsqmm));
		unitarrayadapter.add(getResources().getString(R.string.areaunitsqyard));
	}

	private void fillSpinnerWithVolumeUnit() {
		unitarrayadapter.clear();
		unitarrayadapter.add(getResources()
				.getString(R.string.volumeunitlitres));
		unitarrayadapter.add(getResources().getString(
				R.string.volumeunitmillilitres));
		unitarrayadapter.add(getResources()
				.getString(R.string.volumeunitcubicm));
		unitarrayadapter.add(getResources().getString(
				R.string.volumeunitcubiccm));
		unitarrayadapter.add(getResources().getString(
				R.string.volumeunitcubicmm));
		unitarrayadapter.add(getResources().getString(
				R.string.volumeunitcubicfeet));
	}
	private void fillSpinnerWithPressureUnit() {
		unitarrayadapter.clear();
		unitarrayadapter.add(getResources().getString(
				R.string.pressureunitpascal));
		unitarrayadapter
				.add(getResources().getString(R.string.pressureunitbar));
		unitarrayadapter
				.add(getResources().getString(R.string.pressureunitatm));
		unitarrayadapter.add(getResources()
				.getString(R.string.pressureunittorr));
		unitarrayadapter.notifyDataSetChanged();

	}
	public void onClick(View v) {

		if (v == ButtonConvert) {
			if (!inputValue.getText().toString().equals("")) {
				double in = Double.parseDouble(inputValue.getText().toString());
				Log.d("units", "" + unitfrom + "," + unitto);
				Log.d("current", "" + currentStrategy);
				Log.d("editvalue", "" + in);
				double result = currentStrategy.Convert(unitfrom, unitto, in);
				Log.d("result", "" + currentStrategy.Convert(unitfrom, unitto, in));
				ResultView.setText(Double.toString(result));
			} else {
				ResultView.setText("");
			}
		}
	}
}