package de.schchr.cordova.plugin.timers;

import java.util.Hashtable;

import org.apache.cordova.CallbackContext;
import org.apache.cordova.CordovaInterface;
import org.apache.cordova.CordovaPlugin;
import org.apache.cordova.CordovaWebView;
import org.json.JSONArray;
import org.json.JSONException;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.util.Log;

public class TimerPlugin extends CordovaPlugin {

	protected static final String TAG = "timers";

	protected static CordovaInterface cordovaInstance = null;
	private static CordovaWebView webView = null;
	
	private int timerCount = 0;
	
	private Hashtable<Integer, PendingIntent> timerIntents;
	
	AlarmManager alarmManager = null;

	// adb logcat -s timers

	@Override
	public void initialize(CordovaInterface cordova, CordovaWebView webView) {
		
		TimerPlugin.webView = super.webView;
		TimerPlugin.cordovaInstance = super.cordova;
		
		timerIntents = new Hashtable<Integer, PendingIntent>();
		Log.v(TAG, "init");
		
	}

	@Override
	public boolean execute(String action, JSONArray data, CallbackContext callbackContext) throws JSONException {

		Log.v(TAG, "execute(" + action + ")");

		if (action.equals("addTimeout"))
			callbackContext.success(addTimeout(data.optInt(0)));
		else if (action.equals("addInterval"))
			callbackContext.success(addInterval(data.optInt(0)));
		else if (action.equals("deleteTimer"))
			callbackContext.success(deleteTimer(data.optInt(0)));
		else 
			return false;

		return true;

	}
	
 	private int addTimer(int time, boolean isInterval) {
 		
 		final int timerId = timerCount;
 		
		BroadcastReceiver mReceiver = new BroadcastReceiver() {
			    
			@Override
			public void onReceive(Context context, Intent intent) {
				triggerTimer(timerId);
		    }
	
		};
		
		Activity a = cordovaInstance.getActivity();
		
		if(alarmManager == null)
			alarmManager = (AlarmManager)(a.getSystemService(Context.ALARM_SERVICE));
		
		a.registerReceiver(mReceiver, new IntentFilter("CORDOVA_PLUGIN_TIMER_" + timerId));
		
		PendingIntent pendingIntent = PendingIntent.getBroadcast(a, 0, new Intent("CORDOVA_PLUGIN_TIMER_" + timerId), 0);
    	
		if(isInterval)
			alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, System.currentTimeMillis(), time , pendingIntent);
		else
			alarmManager.set(AlarmManager.RTC_WAKEUP, System.currentTimeMillis() + time, pendingIntent);
		
		timerIntents.put(timerId, pendingIntent);
		
		timerCount++;
		
		Log.v(TAG, "createdTimer with id " + timerId);
		
		return timerId;
		    
	}

	private int addInterval(int msInterval) {
		Log.v(TAG, "addInterval(" + msInterval + ")");
		return addTimer(msInterval, true);
	}

	private int addTimeout(int msTimeout) {
		Log.v(TAG, "addTimeout(" + msTimeout + ")");
		return addTimer(msTimeout, false);
	}

	private String deleteTimer(int timerId) {

		Log.v(TAG, "deleteTimer(" + timerId + ")");

		PendingIntent pendingIntent = timerIntents.get(timerId);
		
		if(pendingIntent != null){
			alarmManager.cancel(pendingIntent);
			return "true";
		} else
			return "false";

	}

	public static void triggerTimer(int timerId) {

		final int finalTimerId = timerId;
	
		Log.v(TAG, "trigger timer with id " + finalTimerId);

		Activity a = cordovaInstance.getActivity();

		a.runOnUiThread(new Runnable() {

			public void run() {
				String js = "cordova.plugins.TimerPlugin.triggerTimer(" + finalTimerId + ")";
				webView.loadUrl("javascript:" + js);
			}

		});

	}

}