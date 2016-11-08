(function(){
    "use strict";

    var module = angular.module("fileUploader");

    var uploadFormController = function(FileService){
        var vm = this;

        vm.fileId = "file-upload-identifier";

        vm.file = {};
        vm.notify = {};

        vm.reset = function(){
            _reset();
        };

        vm.save = function(form){
            _clearNotify();
            if(form.$valid){
                FileService.uploadFile(vm.fileId).then(function(response){
                    vm.file.name = response.safeFileName;
                    vm.file.originalName = response.originalFileName;
                    vm.file.mimeType = response.mimeType;
                    FileService.saveFileMetadata(vm.file).then(function(response) {
                        _reset();
                        vm.notify.message = "Success: File uploaded!";
                        vm.notify.status = "success";
                    })
                    .catch(function(error){
                        vm.notify.message = "Something went wrong: File metadata could not be saved!";
                        vm.notify.status = "danger";
                    });
                })
                .catch(function(error){
                    vm.notify.message = "Something went wrong: File could not be uploaded!";
                    vm.notify.status = "danger";
                });
            }else{
                vm.notify.message = "Please fill out all required fields.";
                vm.notify.status = "info";
            }
        }

        var _clearNotify = function(){
            vm.notify = {};
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