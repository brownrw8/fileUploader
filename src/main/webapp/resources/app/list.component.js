(function(){
    "use strict";

    var module = angular.module("fileUploader");

    var listController = function($rootScope, FileService){
        var vm = this;

        vm.notify = {
            message: "Use this page to view uploaded files.  Click Refresh to check for new files.",
            status: "info"
        };

        vm.files = {};

        vm.load = function(){
            _clearNotify();
            FileService.getFileMetadata().then(function(response) {
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

        var _clearNotify = function(){
            vm.notify = {};
        };

        $rootScope.$on("Reload", function(e){
            vm.load();
        });

        vm.load();

    };

    module.component("list", {
            templateUrl: "app/list.component.html",
            controllerAs: "list",
            controller: ["$rootScope", "FileService", listController]
    });
}());