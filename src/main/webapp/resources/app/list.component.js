(function(){
    "use strict";

    var module = angular.module("fileUploader");

    var listController = function(FileService){
        var vm = this;

        vm.notify = {};
        vm.files = {};

        var _clearNotify = function(){
            vm.notify = {};
        };

        vm.load = function(){
            _clearNotify();
            FileService.getFileMetadata().then(function(response) {
                vm.notify.message = "Retrieved file list!";
                vm.notify.status = "info";
                vm.files = response;
            })
            .catch(function(error){
                vm.notify.message = "Something went wrong: Unable to retrieve file list!";
                vm.notify.status = "danger";
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