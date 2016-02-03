package coredinate.kkr.cordova.plugin.apkupdater;

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

	public static final String TAG = "ApkAutoUpdatePlugin";
	protected static CordovaInterface cordovaInstance = null;
	private static CordovaWebView webView = null;

	@Override
	public void initialize(CordovaInterface cordova, CordovaWebView webView) {
		ApkAutoUpdatePlugin.webView = super.webView;
		ApkAutoUpdatePlugin.cordovaInstance = super.cordova;

		Log.v(TAG, "--- Init");
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
        UpdateApp atualizaApp = new UpdateApp();
		atualizaApp.setContext(this.cordova.getActivity().getApplicationContext());
        atualizaApp.execute(data.optString(0));
	}
}