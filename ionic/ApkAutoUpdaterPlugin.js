/**
 * Ionic service wrapper for the TimerPlugin
 * url: https://github.com/schchr/cordova-plugin-timers.git
 */
app.service("Timers", function($q){

    var self = this;

    var canBeUsed = false;
    var engine = null;

    if(typeof cordova.plugins.TimerPlugin !== "undefined"){

        engine = cordova.plugins.TimerPlugin;
        canBeUsed = true;

        console.log("TimerPlugin loaded");

    }

    var notUsable = function(){
        console.log("TimerPlugin not loaded");
        return $q.reject();
    };

    this.timeout = function(callback, duration){

        if(canBeUsed){

            var defer = $q.defer();

            cordova.plugins.TimerPlugin.addTimeout(callback, duration, function(id){
                defer.resolve(id);
            });

            return defer.promise;

        } else
            notUsable();

    };

    this.interval = function(callback, duration){

        if(canBeUsed){

            var defer = $q.defer();

            cordova.plugins.TimerPlugin.addInterval(callback, duration, function(id){
                defer.resolve(id);
            });

            return defer.promise;

        } else
            notUsable();

    };

    this.cancel = function(id){

        if(canBeUsed)
            return cordova.plugins.TimerPlugin.deleteTimer(id);
        else
            notUsable();

    };

    return this;

});