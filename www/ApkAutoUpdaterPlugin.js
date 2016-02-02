var exec = require('cordova/exec');

var success = function (msg) {
    //console.log("success:" + msg);
};

var error = function (msg) {
    //console.log("error:" + msg);
};

var callbacks = {};

var TimerPlugin = {
    addInterval: function (callback, msInterval, success, error) {
        exec(function (id) {
            callbacks[id] = callback;
            if (typeof success !== "undefined")
                success(id);
        }, function (err) {
            if (typeof err !== "undefined")
                error(err)
        }, "TimerPlugin", "addInterval", [msInterval]);
    },
    addTimeout: function (callback, msTimeout, success, error) {
        exec(function (id) {
            callbacks[id] = callback;
            if (typeof success !== "undefined")
                success(id);
        }, function (err) {
            if (typeof err !== "undefined")
                error(err)
        }, "TimerPlugin", "addTimeout", [msTimeout]);
    },
    deleteTimer: function (timerId, success, error) {
        exec(function (res) {
            delete callbacks[timerId];
            if (typeof success !== "undefined")
                success(res);
        }, function (err) {
            if (typeof err !== "undefined")
                error(err)
        }, "TimerPlugin", "deleteTimer", [timerId]);
    },
    setCallback: function (timerId, callback) {
        callbacks[timerId] = callback;
    },
    triggerTimer: function (timerId) {
        if (typeof callbacks[timerId] !== "undefined")
            callbacks[timerId]();
    }
};

module.exports = TimerPlugin;