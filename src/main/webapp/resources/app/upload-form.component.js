(function(){
    "use strict";

    var module = angular.module("fileUploader");

    var uploadFormController = function(FileService){
        var vm = this;

        vm.fileId = "file-upload-identifier";

        vm.file = {};
        vm.notify = null;

        vm.reset = function(){
            _reset();
        };

        vm.save = function(){
            _clearNotify();
            FileService.uploadFile(vm.fileId).then(function(response){
                vm.file.name = response.safeFileName;
                vm.file.originalName = response.originalFileName;
                vm.file.mimeType = response.mimeType;
                FileService.saveFileMetadata(vm.file).then(function(response) {
                    _reset();
                    vm.notify = "Saved successfully.";
                })
                .catch(function(error){
                    vm.notify = "Something went wrong.";
                });
            })
            .catch(function(error){
                vm.notify = "Something went wrong.";
            });
        };

        var _clearNotify = function(){
            vm.notify = null;
        };

        var _reset = function(){
            FileService.removeFile(vm.fileId);
            vm.file = {};
        };

    };

    module.component("uploadForm", {
            templateUrl: "app/upload-form.component.html",
            controllerAs: "form",
            controller: ["FileService", uploadFormController]
    });

    module.directive("fileReader", [function () {
              return {
                  scope: {
                      fileReader: "="
                  },
                  link: function (scope, element, attributes) {
                      element.bind("change", function (changeEvent) {
                          var reader = new FileReader();
                          reader.onload = function (loadEvent) {
                              scope.$apply(function () {
                                  scope.fileReader = loadEvent.target.result;
                              });
                          }
                          reader.readAsDataURL(changeEvent.target.files[0]);
                      });
                  }
              }
    }]);
}());