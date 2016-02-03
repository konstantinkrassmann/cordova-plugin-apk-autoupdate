package coredinate.kkr.cordova.plugin.apk-updater;

import org.apache.cordova.CallbackContext;
import org.apache.cordova.CordovaInterface;
import org.apache.cordova.CordovaPlugin;
import org.apache.cordova.CordovaWebView;
import org.json.JSONArray;
import org.json.JSONException;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.util.Log;

public class ApkAutoUpdatePlugin extends CordovaPlugin {

	protected static CordovaInterface cordovaInstance = null;
	private static CordovaWebView webView = null;

    BroadcastReceiver mReceiver;

	// Logging: adb logcat -s alarmButton

	@Override
	public void initialize(CordovaInterface cordova, CordovaWebView webView) {

		AlarmButtonPlugin.webView = super.webView;
		AlarmButtonPlugin.cordovaInstance = super.cordova;

		Log.v(TAG, "init");

		RegisterAlarmBroadcast();

	}

	@Override
	public boolean execute(String action, JSONArray data, CallbackContext callbackContext) throws JSONException {

		Log.v(TAG, "execute(" + action + ")");

		if (action.equals("updateFromUrl")){
			updateFromUrl(data);
			callbackContext.success(1);
		}
		else
			return false;

		return true;

	}

 	private void updateFromUrl(JSONArray data) {

        private UpdateApp atualizaApp = new UpdateApp();
        atualizaApp.setContext(getApplicationContext());
        atualizaApp.execute(data.optString(0);
	}
}