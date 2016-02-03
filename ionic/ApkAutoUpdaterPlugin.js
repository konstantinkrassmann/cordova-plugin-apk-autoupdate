/**
 * Ionic service wrapper for the TimerPlugin
 * url: https://github.com/schchr/cordova-plugin-timers.git
 */
app.service("ApkAutoUpdater", function($q){

    var self = this;

    var plugin;

    if(typeof cordova != "undefined" &&
    typeof cordova.plugins.ApkAutoUpdater !== "undefined"){
        var plugin = cordova.plugins.ApkAutoUpdater;
       console.info("-- ApkAutoUpdater loaded");
    }

    /**
    *  Install an apk from the passed url
     * @param String strUrl Url to download the apk from http://exmple.de/test.apk
    */
    this.updateFromUrl = function(strUrl, success, error) {
        var defer = $q.defer();

        if(strUrl){
            return plugin.updateFromUrl(strUrl, defer.resolve,  defer.reject);
        }
        return defer.promise;
    }


    return this;

});