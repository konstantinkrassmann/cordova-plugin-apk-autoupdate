This plugin allows you to create timers in cordova that work in idle mode!

#API

##cordova.plugins.TimerPlugin

###addInterval(callback, msInterval, success, error)
works like setInterval(callback, msInterval)
returns the id of the created timer

###addTimeout(callback, msTimeout, success, error)
works like setTimeout(callback, msTimeout)
returns the id of the created timer

###deleteTimer(timerId, success, error)
removes a specific timer

###setCallback(timerId, callback)
sets a callback for a speicific timer

##logging
Use logcat to log the plugin actions:
'adb logcat -s timers'
