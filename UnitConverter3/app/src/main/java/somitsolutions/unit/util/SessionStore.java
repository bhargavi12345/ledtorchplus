package somitsolutions.unit.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

public class SessionStore {
	private static String STOREPREFF_NAME = "storepreff";
	public static String playMode = "PlayMode";
	public static String DELAY = "delay";
	public static String MILLISEC = "milliseconds";


	public static void savePlayMode(Context context, String key, String value) {
		SharedPreferences sharedPreferences = context.getSharedPreferences(
				STOREPREFF_NAME, Context.MODE_PRIVATE);
		Editor editor = sharedPreferences.edit();
		editor.putString(key, value);
		editor.commit();
	}

	public static String getPlayMode(Context context, String key,
			String defaultValue) {
		SharedPreferences sharedPreferences = context.getSharedPreferences(
				STOREPREFF_NAME, Context.MODE_PRIVATE);
		return sharedPreferences.getString(key, defaultValue);
	}

}
