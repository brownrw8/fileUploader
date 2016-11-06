(function() {
    "use strict";

    var module = angular.module("fileUploader");

    module.service("FileService", ["$http", "envService", function($http, envService){
        var service = this;
        var environment = envService.get();
        console.info("Environment: " + environment);
        var apiUrl = envService.read('apiUrl');

        service.getFileMetadata = function() {
            return $http.get(apiUrl + "file/list")
                .then(function(response) {
                    return response.data;
                });
        };

        service.downloadFile = function(filename) {
            window.open(apiUrl + "file/download/" + filename);
        };

        service.saveFileMetadata = function(metadata) {
            return $http({
                method: 'POST',
                url: apiUrl + "file/save",
                data: metadata
                })
                .then(function(response) {
                    return response.data;
                })
                .catch(function(error) {
                    throw error;
                });
        };

        service.uploadFile = function(fileId) {
            var file = document.getElementById(fileId).files[0];
            var formData = new FormData();
            formData.append('file', file);
            return $http.post(apiUrl + "file/upload", formData, {
                headers: {
                    'Content-Type': undefined,
                },
                transformRequest: angular.identity,
              })
              .then(function(response) {
                  return response.data;
             }).catch(function(error) {
                  throw error;
             });
        };

        service.removeFile = function(fileId) {
            document.getElementById(fileId).value = "";
        };

    }]);


}());
