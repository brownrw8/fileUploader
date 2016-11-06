(function(){
    "use strict";

    var module = angular.module("fileUploader");

    var appController = function(){
        var vm = this;

        vm.display = {
            panelOne: true,
            panelTwo: false
        };

        vm.swapPanels = function(){
            vm.display.panelOne = vm.display.panelTwo;
            vm.display.panelTwo = !vm.display.panelTwo;
        };

    };

    module.component("app", {
            templateUrl: "app/app.component.html",
            controllerAs: "app",
            controller: [appController]
    });
}());