var exec = require('cordova/exec');

var success = function (msg) {
    //console.log("success:" + msg);
};

var error = function (msg) {
    //console.log("error:" + msg);
};

var callbacks = {};

var ApkAutoUpdater = {
    updateFromUrl: function (strUrl, success, error) {
        exec(function (id) {
            if (typeof success !== "undefined")
                success(id);
        }, function (err) {
            if (typeof err !== "undefined")
                error(err)
        }, "ApkAutoUpdaterPlugin", "updateFromUrl", [strUrl]);
    }
};

module.exports = ApkAutoUpdater;