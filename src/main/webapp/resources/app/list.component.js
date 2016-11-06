(function(){
    "use strict";

    var module = angular.module("fileUploader");

    var listController = function(FileService){
        var vm = this;

        vm.notify = null;
        vm.files = {};

        var _clearNotify = function(){
            vm.notify = null;
        };

        vm.load = function(){
            _clearNotify();
            FileService.getFileMetadata().then(function(response) {
                vm.files = response;
            })
            .catch(function(error){
                vm.notify = "Something went wrong.";
            });
        };

        vm.download = function(name){
            _clearNotify();
            FileService.downloadFile(name);
        };

        vm.load();

    };

    module.component("list", {
            templateUrl: "app/list.component.html",
            controllerAs: "list",
            controller: ["FileService", listController]
    });
}());