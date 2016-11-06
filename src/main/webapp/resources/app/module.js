(function() {
    "use strict";

    var module = angular.module("fileUploader", ["environment"]);

    module.config(["envServiceProvider", function(envServiceProvider) {
            // set the domains and variables for each environment
            if (!window.location.origin) {
                window.location.origin = window.location.protocol + "//" + window.location.hostname + (window.location.port ? ':' + window.location.port: '');
            }
            envServiceProvider.config({
                domains: {
                    dev: ['localhost'],
                    live: [window.location.hostname]
                },
                vars: {
                    dev: {
                        apiUrl: '//localhost:8080/'
                    },
                    live: {
                        apiUrl: window.location.origin + '/'
                    }
                }
            });
            envServiceProvider.check();
        }]);

}());