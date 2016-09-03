package somitsolutions.unit.unitconverter;

import android.content.Context;



public class PressureStrategy implements Strategy {

	public Context mcontext;
	public double Convert(String from, String to, double input) {
		// TODO Auto-generated method stub
		if ((from.equals(UnitConverter.getInstance().getApplicationContext().getResources()
				.getString(R.string.pressureunitpascal)) && to
				.equals(UnitConverter.getInstance()
						.getApplicationContext().getResources()
						.getString(R.string.pressureunitbar)))) {
			// if((from.equals("pascal")) && (to.equals("bar"))){
			double ret = input / 100000;
			return ret;
		}
		if ((from.equals(UnitConverter.getInstance()
				.getApplicationContext().getResources()
				.getString(R.string.pressureunitbar)) && to
				.equals(UnitConverter.getInstance()
						.getApplicationContext().getResources()
						.getString(R.string.pressureunitpascal)))) {
			// if((from.equals("bar")) && (to.equals("pascal"))){
			double ret = input * 100000;
			return ret;
		}
		if ((from.equals(UnitConverter.getInstance()
				.getApplicationContext().getResources()
				.getString(R.string.pressureunitatm)) && to
				.equals(UnitConverter.getInstance()
						.getApplicationContext().getResources()
						.getString(R.string.pressureunitpascal)))) {
			// if((from.equals("atm")) && (to.equals("pascal"))){
			double ret = input * 101325;
			return ret;
		}
		if ((from.equals(UnitConverter.getInstance()
				.getApplicationContext().getResources()
				.getString(R.string.pressureunitpascal)) && to
				.equals(UnitConverter.getInstance()
						.getApplicationContext().getResources()
						.getString(R.string.pressureunitatm)))) {
			// if((from.equals("pascal")) && (to.equals("atm"))){
			double ret = input * 0.0000098692;
			return ret;
		}
		if ((from.equals(UnitConverter.getInstance()
				.getApplicationContext().getResources()
				.getString(R.string.pressureunitpascal)) && to
				.equals(UnitConverter.getInstance()
						.getApplicationContext().getResources()
						.getString(R.string.pressureunittorr)))) {
			// if((from.equals("pascal")) && (to.equals("torr"))){
			double ret = input * 0.0075006;
			return ret;
		}
		if ((from.equals(UnitConverter.getInstance()
				.getApplicationContext().getResources()
				.getString(R.string.pressureunittorr)) && to
				.equals(UnitConverter.getInstance()
						.getApplicationContext().getResources()
						.getString(R.string.pressureunitbar)))) {
			// if((from.equals("torr")) && (to.equals("bar"))){
			double ret = input * 0.0013332;
			return ret;
		}
		if ((from.equals(UnitConverter.getInstance()
				.getApplicationContext().getResources()
				.getString(R.string.pressureunittorr)) && to
				.equals(UnitConverter.getInstance()
						.getApplicationContext().getResources()
						.getString(R.string.pressureunitpascal)))) {
			// if((from.equals("torr")) && (to.equals("pascal"))){
			double ret = input * 133.322;
			return ret;
		}
		if ((from.equals(UnitConverter.getInstance()
				.getApplicationContext().getResources()
				.getString(R.string.pressureunitbar)) && to
				.equals(UnitConverter.getInstance()
						.getApplicationContext().getResources()
						.getString(R.string.pressureunittorr)))) {
			// if((from.equals("bar")) && (to.equals("torr"))){
			double ret = input * 750.06;
			return ret;
		}
		if ((from.equals(UnitConverter.getInstance()
				.getApplicationContext().getResources()
				.getString(R.string.pressureunitatm)) && to
				.equals(UnitConverter.getInstance()
						.getApplicationContext().getResources()
						.getString(R.string.pressureunittorr)))) {
			// if((from.equals("atm")) && (to.equals("torr"))){
			double ret = input * 760;
			return ret;
		}
		if ((from.equals(UnitConverter.getInstance()
				.getApplicationContext().getResources()
				.getString(R.string.pressureunittorr)) && to
				.equals(UnitConverter.getInstance()
						.getApplicationContext().getResources()
						.getString(R.string.pressureunitatm)))) {
			// if((from.equals("torr")) && (to.equals("atm"))){
			double ret = input * 0.0013158;
			return ret;
		}
		if ((from.equals(UnitConverter.getInstance()
				.getApplicationContext().getResources()
				.getString(R.string.pressureunitbar)) && to
				.equals(UnitConverter.getInstance()
						.getApplicationContext().getResources()
						.getString(R.string.pressureunitatm)))) {
			// if((from.equals("bar")) && (to.equals("atm"))){
			double ret = input * 0.98692;
			return ret;
		}
		if ((from.equals(UnitConverter.getInstance()
				.getApplicationContext().getResources()
				.getString(R.string.pressureunitatm)) && to
				.equals(UnitConverter.getInstance()
						.getApplicationContext().getResources()
						.getString(R.string.pressureunitbar)))) {
			// if((from.equals("atm")) && (to.equals("bar"))){
			double ret = input * 1.01325;
			return ret;
		}

		if (from.equals(to)) {
			return input;
		}
		return 0.0;
	}
}
