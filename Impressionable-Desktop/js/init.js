"use strict";

//All links open in the default browser instead of electron's window
const shell = require('electron').shell;
var openExternalLink = (link) => {
    event.preventDefault();
    shell.openExternal(link);
};

angular.module('impressionableApp', []);

angular
  .module('impressionableApp')
  .controller('userController', ($scope) => {
    $scope.screenList = [
        $scope.loginScreen = {
            state: true
        },
        $scope.signInScreen = {
            state: false
        },
        $scope.signUpScreen = {
            state: false
        },
        $scope.mainScreen = {
            state: false
        }
    ];
    $scope.backgroundImg = "images/backgroundImage.jpg"
  });


    //changeToTrue takes a list and changes all elements to the falseValue except the one at index elem, which becomes trueValue
/*    $rootScope.changeToTrue = (elem, list, property) => {
        list.forEach((item, key) => {
            if (elem !== key) {
                item[property] = false;
            } else {
                item[property] = true;
            }
        })
        return list;
    };
    $rootScope.changeToFalse = (list, property, falseValue) => {
        list.forEach((item, key) => {
            item[property] = falseValue;
        })
        return list;
    };
    $rootScope.checkForInstance = (elem, list, property) => {
        var checker = false;
        list.forEach((item, key) => {
            if (elem == item[property]) {
                checker = true;
            }
        });
        if (checker == true) {
            return true;
        } else {
            return false;
        }
    };
    $rootScope.retrieveIndex = (index, property) => {
        $rootScope[property] = index;
    };
}); */
