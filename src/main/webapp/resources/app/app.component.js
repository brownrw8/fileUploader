(function(){
    "use strict";

    var module = angular.module("fileUploader");

    var appController = function(){
        var vm = this;

        vm.display = {
            uploadForm: {active: true, title: "Upload"},
            list: {active: false, title: "List"}
        };

        vm.show = function(panel){
            for(var i in vm.display){
                vm.display[i].active = false;
            }
            vm.display[panel].active = true;
        };

    };

    module.component("app", {
            templateUrl: "app/app.component.html",
            controllerAs: "app",
            controller: [appController]
    });
}());